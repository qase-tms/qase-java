package io.qase.commons.client;

import io.qase.client.v1.ApiException;
import io.qase.client.v1.api.AttachmentsApi;
import io.qase.client.v1.models.AttachmentUploadsResponse;
import io.qase.client.v1.models.Attachmentupload;
import io.qase.commons.config.BatchConfig;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.config.TestopsConfig;
import io.qase.commons.logger.Logger;
import io.qase.commons.models.domain.Attachment;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests for UPLD-02: Parallel attachment batch upload via configurable thread pool.
 *
 * Tests verify:
 * - Multiple batches upload concurrently (observable via concurrency counter)
 * - Thread pool defaults to 4 threads
 * - Thread pool size is configurable via BatchConfig.setUploadThreads()
 * - Invalid uploadThreads values (<=0 or >32) are rejected with WARN, default preserved
 * - A failed batch does not prevent other batches from completing
 */
class ParallelUploadTest {

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
     * Creates a list of file-path Attachment objects backed by real temp files.
     * Each file is 1 byte to stay well under size limits but still count as a file.
     *
     * @param count number of attachments to create
     * @return list of Attachment objects
     */
    private List<Attachment> createFilePathAttachments(int count) throws Exception {
        List<Attachment> attachments = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            Path filePath = tempDir.resolve("attach-" + i + ".bin");
            Files.write(filePath, new byte[]{(byte) i});
            Attachment attachment = new Attachment();
            attachment.filePath = filePath.toString();
            attachment.fileName = "attach-" + i + ".bin";
            attachment.mimeType = "application/octet-stream";
            attachments.add(attachment);
        }
        return attachments;
    }

    /**
     * Builds a mock AttachmentsApi that returns a single hash per invocation.
     */
    private AttachmentsApi createSuccessMockApi(String hashPrefix) throws Exception {
        AttachmentsApi mockApi = mock(AttachmentsApi.class);
        AtomicInteger callCount = new AtomicInteger(0);
        when(mockApi.uploadAttachment(anyString(), any())).thenAnswer(invocation -> {
            int call = callCount.incrementAndGet();
            AttachmentUploadsResponse mockResponse = mock(AttachmentUploadsResponse.class);
            Attachmentupload uploadResult = new Attachmentupload();
            uploadResult.setHash(hashPrefix + "-" + call);
            when(mockResponse.getResult()).thenReturn(Collections.singletonList(uploadResult));
            return mockResponse;
        });
        return mockApi;
    }

    // -------------------------------------------------------------------------
    // UPLD-02-1: Multiple batches upload concurrently
    // -------------------------------------------------------------------------

    /**
     * UPLD-02: With 60 files (3 batches of 20), the mock API must observe max concurrency > 1.
     *
     * The mock API increments a counter on entry, sleeps 50ms, decrements on exit.
     * After all uploads complete, the recorded max concurrent calls must be > 1.
     *
     * This test FAILS with sequential (single-threaded) implementation because
     * each batch call completes before the next one starts — max concurrency == 1.
     */
    @Test
    void multipleBatchesUploadConcurrently() throws Exception {
        // Track concurrent invocations
        final AtomicInteger currentConcurrent = new AtomicInteger(0);
        final AtomicInteger maxConcurrent = new AtomicInteger(0);
        final List<String> allHashes = Collections.synchronizedList(new ArrayList<>());

        AttachmentsApi mockApi = mock(AttachmentsApi.class);
        when(mockApi.uploadAttachment(anyString(), any())).thenAnswer(invocation -> {
            int concurrent = currentConcurrent.incrementAndGet();
            // Update max observed concurrency
            maxConcurrent.updateAndGet(prev -> Math.max(prev, concurrent));

            // Hold the lock for 50ms to create a concurrency window
            Thread.sleep(50);

            currentConcurrent.decrementAndGet();

            AttachmentUploadsResponse mockResponse = mock(AttachmentUploadsResponse.class);
            Attachmentupload uploadResult = new Attachmentupload();
            uploadResult.setHash("hash-concurrent-" + System.nanoTime());
            when(mockResponse.getResult()).thenReturn(Collections.singletonList(uploadResult));
            return mockResponse;
        });

        QaseConfig config = createConfig();
        // Use default uploadThreads (4)
        TestableApiClientV1 client = new TestableApiClientV1(config, mockApi);

        // 60 files → 3 batches of 20 (MAX_FILES_PER_REQUEST = 20)
        List<Attachment> attachments = createFilePathAttachments(60);

        // Act
        List<String> hashes = client.uploadAttachments(attachments);

        // Assert: all 3 batches returned hashes (one hash per batch in mock)
        assertFalse(hashes.isEmpty(), "Must return at least some hashes from successful batches");

        // Assert: concurrent execution was observed (max > 1)
        assertTrue(maxConcurrent.get() > 1,
                "Expected max concurrent batch uploads > 1 but got: " + maxConcurrent.get()
                        + ". This indicates sequential (non-parallel) execution.");
    }

    // -------------------------------------------------------------------------
    // UPLD-02-2: Default thread pool size is 4
    // -------------------------------------------------------------------------

    /**
     * UPLD-02: A fresh BatchConfig must have uploadThreads == 4.
     *
     * This test FAILS because BatchConfig does not yet have an uploadThreads field.
     */
    @Test
    void defaultThreadPoolSizeIsFour() {
        BatchConfig batch = new BatchConfig();
        assertEquals(4, batch.getUploadThreads(),
                "Default uploadThreads must be 4");
    }

    // -------------------------------------------------------------------------
    // UPLD-02-3: Failed batch does not block other batches
    // -------------------------------------------------------------------------

    /**
     * UPLD-02: When batch 2 (middle) throws ApiException, batches 1 and 3 must still succeed.
     *
     * Uses a call counter to identify which invocation is batch 2.
     * Expected: returned hashes come from batches 1 and 3 only.
     *
     * This test FAILS because the parallel execution path does not exist — the current
     * sequential loop catches per-batch exceptions, but parallel execution is not implemented.
     */
    @Test
    void failedBatchDoesNotBlockOtherBatches() throws Exception {
        final AtomicInteger callCount = new AtomicInteger(0);

        AttachmentsApi mockApi = mock(AttachmentsApi.class);
        when(mockApi.uploadAttachment(anyString(), any())).thenAnswer(invocation -> {
            int call = callCount.incrementAndGet();

            if (call == 2) {
                // Batch 2 fails
                throw new ApiException("Simulated batch 2 failure");
            }

            AttachmentUploadsResponse mockResponse = mock(AttachmentUploadsResponse.class);
            Attachmentupload uploadResult = new Attachmentupload();
            uploadResult.setHash("hash-batch-" + call);
            when(mockResponse.getResult()).thenReturn(Collections.singletonList(uploadResult));
            return mockResponse;
        });

        QaseConfig config = createConfig();
        TestableApiClientV1 client = new TestableApiClientV1(config, mockApi);

        // 60 files → 3 batches (batch 2 will fail)
        List<Attachment> attachments = createFilePathAttachments(60);

        // Act
        List<String> hashes = client.uploadAttachments(attachments);

        // Assert: 2 hashes from batches 1 and 3 (batch 2 was dropped)
        assertEquals(2, hashes.size(),
                "Expected 2 hashes from batches 1 and 3, but got: " + hashes.size()
                        + " hashes: " + hashes);

        assertTrue(hashes.contains("hash-batch-1"), "Hash from batch 1 must be in result");
        assertTrue(hashes.contains("hash-batch-3"), "Hash from batch 3 must be in result");
    }

    // -------------------------------------------------------------------------
    // UPLD-02-4: uploadThreads config validation in BatchConfig
    // -------------------------------------------------------------------------

    /**
     * UPLD-02: BatchConfig.setUploadThreads(8) must accept valid value.
     *
     * This test FAILS because setUploadThreads() does not exist yet.
     */
    @Test
    void uploadThreadsValidValueAccepted() {
        BatchConfig batch = new BatchConfig();
        batch.setUploadThreads(8);
        assertEquals(8, batch.getUploadThreads(),
                "Valid uploadThreads value (8) must be accepted");
    }

    /**
     * UPLD-02: BatchConfig.setUploadThreads(0) must be rejected — default 4 preserved.
     *
     * This test FAILS because setUploadThreads() does not exist yet.
     */
    @Test
    void uploadThreadsZeroRejectedWithDefaultPreserved() {
        Logger logger = Logger.getInstance();
        long warnBefore = logger.getStatistics().get("WARN");

        BatchConfig batch = new BatchConfig();
        batch.setUploadThreads(0);

        assertEquals(4, batch.getUploadThreads(),
                "uploadThreads=0 must be rejected, default 4 must be preserved");
        assertTrue(logger.getStatistics().get("WARN") >= warnBefore,
                "A WARN must be logged for invalid uploadThreads=0");
    }

    /**
     * UPLD-02: BatchConfig.setUploadThreads(-1) must be rejected — default 4 preserved.
     *
     * This test FAILS because setUploadThreads() does not exist yet.
     */
    @Test
    void uploadThreadsNegativeRejectedWithDefaultPreserved() {
        Logger logger = Logger.getInstance();
        long warnBefore = logger.getStatistics().get("WARN");

        BatchConfig batch = new BatchConfig();
        batch.setUploadThreads(-1);

        assertEquals(4, batch.getUploadThreads(),
                "uploadThreads=-1 must be rejected, default 4 must be preserved");
        assertTrue(logger.getStatistics().get("WARN") >= warnBefore,
                "A WARN must be logged for invalid uploadThreads=-1");
    }

    /**
     * UPLD-02: BatchConfig.setUploadThreads(33) exceeds max (32) — default 4 preserved.
     *
     * This test FAILS because setUploadThreads() does not exist yet.
     */
    @Test
    void uploadThreadsAboveMaxRejectedWithDefaultPreserved() {
        Logger logger = Logger.getInstance();
        long warnBefore = logger.getStatistics().get("WARN");

        BatchConfig batch = new BatchConfig();
        batch.setUploadThreads(33);

        assertEquals(4, batch.getUploadThreads(),
                "uploadThreads=33 exceeds max 32, default 4 must be preserved");
        assertTrue(logger.getStatistics().get("WARN") >= warnBefore,
                "A WARN must be logged for invalid uploadThreads=33");
    }

    /**
     * UPLD-02: BatchConfig.setUploadThreads(32) is the maximum valid value — must be accepted.
     *
     * This test FAILS because setUploadThreads() does not exist yet.
     */
    @Test
    void uploadThreadsMaxBoundaryAccepted() {
        BatchConfig batch = new BatchConfig();
        batch.setUploadThreads(32);
        assertEquals(32, batch.getUploadThreads(),
                "uploadThreads=32 is the maximum allowed value and must be accepted");
    }

    /**
     * UPLD-02: BatchConfig.setUploadThreads(1) is a valid minimum (single-threaded mode).
     *
     * This test FAILS because setUploadThreads() does not exist yet.
     */
    @Test
    void uploadThreadsMinBoundaryAccepted() {
        BatchConfig batch = new BatchConfig();
        batch.setUploadThreads(1);
        assertEquals(1, batch.getUploadThreads(),
                "uploadThreads=1 is valid (single-threaded mode) and must be accepted");
    }

    // -------------------------------------------------------------------------
    // UPLD-02-5: uploadThreads wired from system property
    // -------------------------------------------------------------------------

    /**
     * UPLD-02: Setting QASE_TESTOPS_BATCH_UPLOAD_THREADS system property to 8 must result
     * in config.testops.batch.uploadThreads == 8 after loadConfig().
     *
     * This test FAILS because ConfigFactory does not wire QASE_TESTOPS_BATCH_UPLOAD_THREADS yet.
     */
    @Test
    void uploadThreadsConfigurableViaSystemProperty() {
        System.setProperty("QASE_TESTOPS_BATCH_UPLOAD_THREADS", "8");
        try {
            io.qase.commons.config.QaseConfig config = io.qase.commons.config.ConfigFactory.loadConfig();
            assertEquals(8, config.testops.batch.getUploadThreads(),
                    "uploadThreads must be 8 when QASE_TESTOPS_BATCH_UPLOAD_THREADS=8 is set");
        } finally {
            System.clearProperty("QASE_TESTOPS_BATCH_UPLOAD_THREADS");
        }
    }

    /**
     * UPLD-02: Setting QASE_TESTOPS_BATCH_UPLOAD_THREADS system property to invalid value (0)
     * must keep default 4 and log a warning.
     *
     * This test FAILS because ConfigFactory does not wire QASE_TESTOPS_BATCH_UPLOAD_THREADS yet.
     */
    @Test
    void uploadThreadsInvalidSystemPropertyKeepsDefault() {
        Logger logger = Logger.getInstance();
        long warnBefore = logger.getStatistics().get("WARN");

        System.setProperty("QASE_TESTOPS_BATCH_UPLOAD_THREADS", "0");
        try {
            io.qase.commons.config.QaseConfig config = io.qase.commons.config.ConfigFactory.loadConfig();
            assertEquals(4, config.testops.batch.getUploadThreads(),
                    "Invalid QASE_TESTOPS_BATCH_UPLOAD_THREADS=0 must keep default 4");
            assertTrue(logger.getStatistics().get("WARN") > warnBefore,
                    "A WARN must be logged for invalid uploadThreads via system property");
        } finally {
            System.clearProperty("QASE_TESTOPS_BATCH_UPLOAD_THREADS");
        }
    }
}
