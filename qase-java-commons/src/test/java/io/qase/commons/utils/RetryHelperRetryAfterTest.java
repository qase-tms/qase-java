package io.qase.commons.utils;

import io.qase.client.v1.ApiException;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Qase answers HTTP 429 with a Retry-After header of roughly 60 seconds. The
 * computed backoff ladder (1s + 3s + 9s) is exhausted long before the limit
 * clears, so the retry must honour the header instead.
 */
class RetryHelperRetryAfterTest {

    private static Map<String, List<String>> headers(String name, String value) {
        Map<String, List<String>> headers = new HashMap<>();
        headers.put(name, Collections.singletonList(value));
        return headers;
    }

    private static ApiException rateLimited(Map<String, List<String>> headers) {
        return new ApiException(429, "Too Many Requests", headers, "{}");
    }

    // -------------------------------------------------------------------------
    // Header parsing
    // -------------------------------------------------------------------------

    @Test
    void numericRetryAfterIsParsedAsSeconds() {
        assertEquals(60_000L,
                RetryHelper.extractRetryAfterMs(rateLimited(headers("Retry-After", "60"))));
    }

    @Test
    void retryAfterHeaderNameIsCaseInsensitive() {
        assertEquals(5_000L,
                RetryHelper.extractRetryAfterMs(rateLimited(headers("retry-after", "5"))));
        assertEquals(5_000L,
                RetryHelper.extractRetryAfterMs(rateLimited(headers("RETRY-AFTER", "5"))));
    }

    @Test
    void surroundingWhitespaceIsTolerated() {
        assertEquals(7_000L,
                RetryHelper.extractRetryAfterMs(rateLimited(headers("Retry-After", " 7 "))));
    }

    @Test
    void absentHeaderYieldsNoDelay() {
        assertEquals(-1L, RetryHelper.extractRetryAfterMs(rateLimited(headers("X-Other", "1"))));
        assertEquals(-1L, RetryHelper.extractRetryAfterMs(rateLimited(new HashMap<>())));
        assertEquals(-1L, RetryHelper.extractRetryAfterMs(new ApiException(429, "no headers")));
        assertEquals(-1L, RetryHelper.extractRetryAfterMs(new RuntimeException("not an ApiException")));
    }

    /**
     * Retry-After may be an HTTP-date instead of a number. Parsing it would mean
     * guessing at clock skew between us and the server, so we fall back to the
     * computed backoff rather than trusting the difference.
     */
    @Test
    void httpDateRetryAfterFallsBackToComputedDelay() {
        assertEquals(-1L, RetryHelper.extractRetryAfterMs(
                rateLimited(headers("Retry-After", "Wed, 21 Oct 2026 07:28:00 GMT"))));
    }

    @Test
    void garbageAndNonPositiveValuesFallBackToComputedDelay() {
        assertEquals(-1L, RetryHelper.extractRetryAfterMs(rateLimited(headers("Retry-After", "soon"))));
        assertEquals(-1L, RetryHelper.extractRetryAfterMs(rateLimited(headers("Retry-After", ""))));
        assertEquals(-1L, RetryHelper.extractRetryAfterMs(rateLimited(headers("Retry-After", "-5"))));
        assertEquals(-1L, RetryHelper.extractRetryAfterMs(rateLimited(headers("Retry-After", "0"))));
    }

    @Test
    void v2ApiExceptionHeadersAreRead() {
        assertEquals(30_000L, RetryHelper.extractRetryAfterMs(
                new io.qase.client.v2.ApiException(429, "Too Many Requests",
                        headers("Retry-After", "30"), "{}")));
    }

    /**
     * An implausibly large Retry-After must not park an upload thread forever.
     */
    @Test
    void absurdRetryAfterIsCappedAtTheMaximum() {
        assertEquals(RetryHelper.MAX_RETRY_AFTER_MS, RetryHelper.extractRetryAfterMs(
                rateLimited(headers("Retry-After", "86400"))));
    }

    // -------------------------------------------------------------------------
    // Applied delay
    // -------------------------------------------------------------------------

    @Test
    void retryWaitsTheRetryAfterValueWhenPresent() throws Exception {
        AtomicInteger attempts = new AtomicInteger(0);

        long startNanos = System.nanoTime();
        String result = RetryHelper.retry(() -> {
            if (attempts.incrementAndGet() == 1) {
                throw rateLimited(headers("Retry-After", "3"));
            }
            return "ok";
        }, "rate limited action");
        long elapsedMs = (System.nanoTime() - startNanos) / 1_000_000;

        assertEquals("ok", result);
        assertEquals(2, attempts.get());
        assertTrue(elapsedMs >= 3_000,
                "Retry-After: 3 must be honoured over the 1000ms computed backoff, waited " + elapsedMs + "ms");
    }

    @Test
    void retryUsesComputedBackoffWhenHeaderIsAbsent() throws Exception {
        AtomicInteger attempts = new AtomicInteger(0);

        long startNanos = System.nanoTime();
        String result = RetryHelper.retry(() -> {
            if (attempts.incrementAndGet() == 1) {
                throw new ApiException(429, "Too Many Requests");
            }
            return "ok";
        }, "rate limited action without header");
        long elapsedMs = (System.nanoTime() - startNanos) / 1_000_000;

        assertEquals("ok", result);
        assertEquals(2, attempts.get());
        assertTrue(elapsedMs >= RetryHelper.BASE_DELAY_MS,
                "computed backoff of " + RetryHelper.BASE_DELAY_MS + "ms must still apply, waited " + elapsedMs + "ms");
        assertTrue(elapsedMs < 3_000,
                "without a header the wait must stay at the computed backoff, waited " + elapsedMs + "ms");
    }

    @Test
    void worstCaseRetryDelayCoversEveryAttempt() {
        assertTrue(RetryHelper.maxRetryDelaySeconds() >= RetryHelper.MAX_RETRIES * (RetryHelper.MAX_RETRY_AFTER_MS / 1000),
                "the retry budget must cover a Retry-After wait on every attempt");
    }
}
