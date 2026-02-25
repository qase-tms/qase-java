package io.qase.commons.utils;

import io.qase.client.v1.ApiException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class RetryHelperTest {

    @Test
    void successOnFirstAttempt() throws Exception {
        AtomicInteger attempts = new AtomicInteger(0);

        String result = RetryHelper.retry(() -> {
            attempts.incrementAndGet();
            return "ok";
        }, "test action");

        assertEquals("ok", result);
        assertEquals(1, attempts.get());
    }

    @Test
    void transientErrorCode0RetriesThenSucceeds() throws Exception {
        AtomicInteger attempts = new AtomicInteger(0);

        String result = RetryHelper.retry(() -> {
            if (attempts.incrementAndGet() < 3) {
                throw new ApiException(0, "SocketTimeoutException");
            }
            return "recovered";
        }, "timeout action");

        assertEquals("recovered", result);
        assertEquals(3, attempts.get());
    }

    @Test
    void transientErrorCode500ExhaustsRetries() {
        AtomicInteger attempts = new AtomicInteger(0);

        ApiException thrown = assertThrows(ApiException.class, () ->
                RetryHelper.retry(() -> {
                    attempts.incrementAndGet();
                    throw new ApiException(500, "Internal Server Error");
                }, "server error action")
        );

        assertEquals(500, thrown.getCode());
        assertEquals(RetryHelper.MAX_RETRIES + 1, attempts.get());
    }

    @Test
    void transientErrorCode429RetriesThenSucceeds() throws Exception {
        AtomicInteger attempts = new AtomicInteger(0);

        String result = RetryHelper.retry(() -> {
            if (attempts.incrementAndGet() == 1) {
                throw new ApiException(429, "Rate limit");
            }
            return "ok";
        }, "rate limit action");

        assertEquals("ok", result);
        assertEquals(2, attempts.get());
    }

    @Test
    void nonTransientError404NoRetry() {
        AtomicInteger attempts = new AtomicInteger(0);

        ApiException thrown = assertThrows(ApiException.class, () ->
                RetryHelper.retry(() -> {
                    attempts.incrementAndGet();
                    throw new ApiException(404, "Not Found");
                }, "not found action")
        );

        assertEquals(404, thrown.getCode());
        assertEquals(1, attempts.get());
    }

    @Test
    void nonTransientError401NoRetry() {
        AtomicInteger attempts = new AtomicInteger(0);

        ApiException thrown = assertThrows(ApiException.class, () ->
                RetryHelper.retry(() -> {
                    attempts.incrementAndGet();
                    throw new ApiException(401, "Unauthorized");
                }, "unauthorized action")
        );

        assertEquals(401, thrown.getCode());
        assertEquals(1, attempts.get());
    }

    @Test
    void nonTransientError403NoRetry() {
        AtomicInteger attempts = new AtomicInteger(0);

        ApiException thrown = assertThrows(ApiException.class, () ->
                RetryHelper.retry(() -> {
                    attempts.incrementAndGet();
                    throw new ApiException(403, "Forbidden");
                }, "forbidden action")
        );

        assertEquals(403, thrown.getCode());
        assertEquals(1, attempts.get());
    }

    @Test
    void isRetryableForVariousCodes() {
        assertTrue(RetryHelper.isRetryable(new ApiException(0, "timeout")));
        assertTrue(RetryHelper.isRetryable(new ApiException(429, "rate limit")));
        assertTrue(RetryHelper.isRetryable(new ApiException(500, "server error")));
        assertTrue(RetryHelper.isRetryable(new ApiException(502, "bad gateway")));
        assertTrue(RetryHelper.isRetryable(new ApiException(503, "service unavailable")));
        assertTrue(RetryHelper.isRetryable(new ApiException(504, "gateway timeout")));

        assertFalse(RetryHelper.isRetryable(new ApiException(400, "bad request")));
        assertFalse(RetryHelper.isRetryable(new ApiException(401, "unauthorized")));
        assertFalse(RetryHelper.isRetryable(new ApiException(403, "forbidden")));
        assertFalse(RetryHelper.isRetryable(new ApiException(404, "not found")));
        assertFalse(RetryHelper.isRetryable(new ApiException(422, "unprocessable")));
    }

    @Test
    void isRetryableForV2ApiException() {
        assertTrue(RetryHelper.isRetryable(new io.qase.client.v2.ApiException(500, "server error")));
        assertTrue(RetryHelper.isRetryable(new io.qase.client.v2.ApiException(0, "timeout")));
        assertFalse(RetryHelper.isRetryable(new io.qase.client.v2.ApiException(404, "not found")));
    }

    @Test
    void isRetryableForGenericException() {
        assertTrue(RetryHelper.isRetryable(new RuntimeException("network error")));
    }

    @Test
    void runnableRetrySucceeds() throws Exception {
        AtomicInteger attempts = new AtomicInteger(0);

        RetryHelper.retry(() -> {
            if (attempts.incrementAndGet() < 2) {
                throw new ApiException(500, "server error");
            }
        }, "runnable action");

        assertEquals(2, attempts.get());
    }

    @Test
    void runnableRetryFailsOnNonTransient() {
        AtomicInteger attempts = new AtomicInteger(0);

        assertThrows(ApiException.class, () ->
                RetryHelper.retry(() -> {
                    attempts.incrementAndGet();
                    throw new ApiException(404, "Not Found");
                }, "runnable not found")
        );

        assertEquals(1, attempts.get());
    }
}
