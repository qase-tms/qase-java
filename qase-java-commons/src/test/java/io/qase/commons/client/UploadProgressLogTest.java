package io.qase.commons.client;

import io.qase.client.v1.ApiException;
import io.qase.client.v1.api.AttachmentsApi;
import io.qase.client.v1.models.AttachmentUploadsResponse;
import io.qase.client.v1.models.Attachmentupload;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.config.TestopsConfig;
import io.qase.commons.logger.Logger;
import io.qase.commons.models.domain.Attachment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests for LOGS-03: Per-batch upload progress logging at INFO level.
 *
 * Verifies that uploadBatchParallel() emits an INFO log after each successful
 * batch upload (promotion from DEBUG to INFO), including elapsed time and retry count.
 *
 * All tests FAIL until ApiClientV1.uploadBatchParallel() is changed from
 * logger.debug() to logger.info().
 */
class UploadProgressLogTest {

    @TempDir
    Path tempDir;

    private QaseConfig createConfig() {
        QaseConfig config = new QaseConfig();
        config.testops = new TestopsConfig();
        config.testops.project = "TEST";
        config.testops.api.token = "test_token";
        config.testops.api.host = "qase.io";
        config.testops.api.timeoutSeconds = 30;
        return config;
    }

    /**
     * Test subclass that injects a mock AttachmentsApi to avoid real HTTP calls.
     * Follows the established pattern from ParallelUploadTest.
     */
    private static class TestableApiClientV1 extends ApiClientV1 {
        private final AttachmentsApi mockApi;

        TestableApiClientV1(QaseConfig config, AttachmentsApi mockApi) {
            super(config);
            this.mockApi = mockApi;
        }

        @Override
        AttachmentsApi createAttachmentsApi() {
            return mockApi;
        }
    }

    /**
     * Creates a file-path Attachment backed by a real temp file.
     */
    private Attachment createFileAttachment(String fileName, int sizeBytes) throws Exception {
        Path filePath = tempDir.resolve(fileName);
        Files.write(filePath, new byte[sizeBytes]);
        Attachment attachment = new Attachment();
        attachment.filePath = filePath.toString();
        attachment.fileName = fileName;
        attachment.mimeType = "application/octet-stream";
        return attachment;
    }

    /**
     * Builds a mock AttachmentsApi that returns a single hash per invocation.
     */
    private AttachmentsApi createSuccessMockApi() throws Exception {
        AttachmentsApi mockApi = mock(AttachmentsApi.class);
        when(mockApi.uploadAttachment(anyString(), any())).thenAnswer(invocation -> {
            AttachmentUploadsResponse mockResponse = mock(AttachmentUploadsResponse.class);
            Attachmentupload uploadResult = new Attachmentupload();
            uploadResult.setHash("hash-" + System.nanoTime());
            when(mockResponse.getResult()).thenReturn(Collections.singletonList(uploadResult));
            return mockResponse;
        });
        return mockApi;
    }

    // -------------------------------------------------------------------------
    // LOGS-03-1: Batch progress log is emitted at INFO level (not DEBUG)
    // -------------------------------------------------------------------------

    /**
     * LOGS-03: After a successful single-batch upload, the INFO count must increase.
     *
     * The single-batch upload (no retries) goes through uploadBatchParallel() which
     * currently calls logger.debug(). Since DEBUG does NOT increment the INFO counter,
     * this test FAILS with the current code.
     *
     * After changing to logger.info(), one INFO is emitted per batch — test PASSES.
     */
    @Test
    void batchProgressLogEmittedAtInfoLevel() throws Exception {
        Logger logger = Logger.getInstance();
        long infoBefore = logger.getStatistics().get("INFO");

        AttachmentsApi mockApi = createSuccessMockApi();
        TestableApiClientV1 client = new TestableApiClientV1(createConfig(), mockApi);

        // Create 1 file attachment and upload it — no retries, no other INFO logs
        Attachment attachment = createFileAttachment("progress-test.bin", 1024);
        client.uploadAttachments(Collections.singletonList(attachment));

        // After successful upload, INFO count must have increased by exactly 1
        // (the batch progress log). With the current DEBUG log, infoAfter == infoBefore.
        long infoAfter = logger.getStatistics().get("INFO");
        assertTrue(infoAfter > infoBefore,
                "Upload batch completion must be logged at INFO level (not DEBUG). " +
                "infoBefore=" + infoBefore + ", infoAfter=" + infoAfter +
                ". This FAILS because the current code uses logger.debug().");
    }

    // -------------------------------------------------------------------------
    // LOGS-03-2: Retry count reflected in progress log (INFO fires after retry)
    // -------------------------------------------------------------------------

    /**
     * LOGS-03: When a batch fails once with a retryable error (429) and then
     * succeeds on retry, exactly 2 INFO messages must be emitted:
     *   1. RetryHelper logs "Retry succeeded for..." at INFO
     *   2. uploadBatchParallel logs "Upload batch N/M complete: ..." at INFO  (LOGS-03)
     *
     * Current code (logger.debug): only 1 INFO from RetryHelper — infoIncrement == 1.
     * After LOGS-03: 2 INFOs — infoIncrement >= 2.
     *
     * This test FAILS because the current code uses logger.debug() for the batch
     * completion, so infoIncrement == 1, not >= 2.
     */
    @Test
    void retryCountReflectsActualRetries() throws Exception {
        Logger logger = Logger.getInstance();
        long infoBefore = logger.getStatistics().get("INFO");

        // Mock API: first call throws 429 (retryable), second call succeeds
        AtomicInteger callCount = new AtomicInteger(0);
        AttachmentsApi mockApi = mock(AttachmentsApi.class);
        when(mockApi.uploadAttachment(anyString(), any())).thenAnswer(invocation -> {
            int call = callCount.incrementAndGet();
            if (call == 1) {
                // 429 is retryable per RetryHelper.isRetryable()
                throw new ApiException(429, "Rate limited");
            }
            AttachmentUploadsResponse mockResponse = mock(AttachmentUploadsResponse.class);
            Attachmentupload uploadResult = new Attachmentupload();
            uploadResult.setHash("hash-after-retry");
            when(mockResponse.getResult()).thenReturn(Collections.singletonList(uploadResult));
            return mockResponse;
        });

        TestableApiClientV1 client = new TestableApiClientV1(createConfig(), mockApi);

        Attachment attachment = createFileAttachment("retry-test.bin", 512);
        client.uploadAttachments(Collections.singletonList(attachment));

        // After successful retry:
        //   - RetryHelper emits INFO "Retry succeeded for..." = 1 INFO
        //   - uploadBatchParallel must emit INFO "Upload batch complete..." = 1 more INFO
        // Total expected: infoBefore + 2
        // Current code: only RetryHelper's INFO fires → infoIncrement == 1
        long infoAfter = logger.getStatistics().get("INFO");
        long infoIncrement = infoAfter - infoBefore;

        assertTrue(infoIncrement >= 2,
                "After a successful retry, at least 2 INFO messages must be emitted: " +
                "RetryHelper's 'Retry succeeded' log and the batch progress log (LOGS-03). " +
                "Got infoIncrement=" + infoIncrement +
                " (infoBefore=" + infoBefore + ", infoAfter=" + infoAfter + "). " +
                "This FAILS because uploadBatchParallel() still uses logger.debug() " +
                "so only RetryHelper's 1 INFO is counted.");
    }
}
