package io.qase.commons.utils;

import io.qase.commons.logger.Logger;

public class RetryHelper {
    private static final Logger logger = Logger.getInstance();

    static final int MAX_RETRIES = 3;
    static final int BASE_DELAY_MS = 1000;
    static final int BACKOFF_MULTIPLIER = 3;

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

                int delay = BASE_DELAY_MS * (int) Math.pow(BACKOFF_MULTIPLIER, attempt);
                logger.warn("Retrying '%s' (attempt %d/%d) after %dms: %s",
                        actionName, attempt + 1, MAX_RETRIES, delay, e.getMessage());

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

    static boolean isRetryable(Exception e) {
        int code = extractHttpCode(e);
        return code == 0 || code == 429 || code >= 500;
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
