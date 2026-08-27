package io.qase.commons.utils;

import io.qase.commons.logger.Logger;

import java.util.List;
import java.util.Map;

public class RetryHelper {
    private static final Logger logger = Logger.getInstance();

    static final int MAX_RETRIES = 3;
    static final int BASE_DELAY_MS = 1000;
    static final int BACKOFF_MULTIPLIER = 3;

    /**
     * Header the API sends with HTTP 429 stating how long the rate limit lasts.
     */
    private static final String RETRY_AFTER_HEADER = "Retry-After";

    /**
     * Upper bound on a honoured Retry-After. Qase answers 429 with roughly 60
     * seconds; the cap keeps an implausible value from parking an upload thread
     * for the rest of the build.
     */
    static final long MAX_RETRY_AFTER_MS = 120_000L;

    @FunctionalInterface
    public interface ThrowingSupplier<T> {
        T get() throws Exception;
    }

    @FunctionalInterface
    public interface ThrowingRunnable {
        void run() throws Exception;
    }

    public static <T> T retry(ThrowingSupplier<T> action, String actionName) throws Exception {
        Exception lastException = null;

        for (int attempt = 0; attempt <= MAX_RETRIES; attempt++) {
            try {
                T result = action.get();
                if (attempt > 0) {
                    logger.info("Retry succeeded for '%s' on attempt %d", actionName, attempt + 1);
                }
                return result;
            } catch (Exception e) {
                lastException = e;

                if (!isRetryable(e) || attempt == MAX_RETRIES) {
                    throw e;
                }

                // A rate limit clears when the server says it clears, not when our
                // backoff ladder happens to run out.
                long retryAfterMs = extractRetryAfterMs(e);
                long delay = retryAfterMs > 0
                        ? retryAfterMs
                        : (long) BASE_DELAY_MS * (long) Math.pow(BACKOFF_MULTIPLIER, attempt);
                String delaySource = retryAfterMs > 0 ? " (Retry-After)" : "";

                int httpCode = extractHttpCode(e);
                if (httpCode > 0) {
                    logger.warn("Retrying '%s' (attempt %d/%d) after %dms%s: HTTP %d - %s",
                            actionName, attempt + 1, MAX_RETRIES, delay, delaySource, httpCode, e.getMessage());
                } else {
                    logger.warn("Retrying '%s' (attempt %d/%d) after %dms%s: %s",
                            actionName, attempt + 1, MAX_RETRIES, delay, delaySource, e.getMessage());
                }

                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw lastException;
                }
            }
        }

        throw lastException;
    }

    public static void retry(ThrowingRunnable action, String actionName) throws Exception {
        retry(() -> {
            action.run();
            return null;
        }, actionName);
    }

    /**
     * Worst-case total time a single retried action can spend sleeping, in seconds.
     * Callers that wait on a retried action (the upload executor) need this so their
     * own timeout does not expire while a thread is correctly waiting out a
     * Retry-After.
     *
     * @return the sum of the longest possible delay for every retry attempt
     */
    public static int maxRetryDelaySeconds() {
        long totalMs = 0;
        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {
            long computed = (long) BASE_DELAY_MS * (long) Math.pow(BACKOFF_MULTIPLIER, attempt);
            totalMs += Math.max(MAX_RETRY_AFTER_MS, computed);
        }
        return (int) ((totalMs + 999) / 1000);
    }

    static boolean isRetryable(Exception e) {
        int code = extractHttpCode(e);
        return code == 0 || code == 408 || code == 429 || code >= 500;
    }

    /**
     * Reads the Retry-After header off an API exception.
     *
     * @return the delay in milliseconds, capped at {@link #MAX_RETRY_AFTER_MS},
     *         or -1 when the header is absent or not a plain number of seconds
     */
    static long extractRetryAfterMs(Exception e) {
        Map<String, List<String>> headers = extractResponseHeaders(e);
        if (headers == null || headers.isEmpty()) {
            return -1;
        }

        for (Map.Entry<String, List<String>> header : headers.entrySet()) {
            String name = header.getKey();
            if (name == null || !RETRY_AFTER_HEADER.equalsIgnoreCase(name.trim())) {
                continue;
            }
            List<String> values = header.getValue();
            if (values == null || values.isEmpty() || values.get(0) == null) {
                return -1;
            }
            return parseRetryAfterMs(values.get(0).trim());
        }

        return -1;
    }

    private static long parseRetryAfterMs(String value) {
        try {
            long seconds = Long.parseLong(value);
            if (seconds <= 0) {
                return -1;
            }
            if (seconds > MAX_RETRY_AFTER_MS / 1000) {
                return MAX_RETRY_AFTER_MS;
            }
            return seconds * 1000L;
        } catch (NumberFormatException ex) {
            // Retry-After may be an HTTP-date instead of a delay in seconds.
            // Honouring it would mean guessing at clock skew between us and the
            // server, so fall back to the computed backoff.
            return -1;
        }
    }

    private static Map<String, List<String>> extractResponseHeaders(Exception e) {
        if (e instanceof io.qase.client.v1.ApiException) {
            return ((io.qase.client.v1.ApiException) e).getResponseHeaders();
        }
        if (e instanceof io.qase.client.v2.ApiException) {
            return ((io.qase.client.v2.ApiException) e).getResponseHeaders();
        }
        return null;
    }

    private static int extractHttpCode(Exception e) {
        if (e instanceof io.qase.client.v1.ApiException) {
            return ((io.qase.client.v1.ApiException) e).getCode();
        }
        if (e instanceof io.qase.client.v2.ApiException) {
            return ((io.qase.client.v2.ApiException) e).getCode();
        }
        return 0;
    }
}
