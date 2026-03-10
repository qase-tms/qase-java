package io.qase.commons.client;

import io.qase.client.v1.api.AttachmentsApi;
import io.qase.client.v1.models.AttachmentUploadsResponse;
import io.qase.client.v1.models.Attachmentupload;
import io.qase.commons.config.ApiConfig;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.config.TestopsConfig;
import io.qase.commons.models.domain.Attachment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Tests for UPLD-01: Dynamic upload timeout based on file size.
 */
class UploadTimeoutTest {

    // Constants mirrored from ApiClientV1 for assertion clarity
    private static final int BASE_UPLOAD_TIMEOUT_SECONDS = 30;
    private static final int MAX_UPLOAD_TIMEOUT_SECONDS = 3600;
    private static final long MINIMUM_UPLOAD_SPEED_BPS = 100L * 1024; // 100 KB/s

    private QaseConfig createConfig(int timeoutSeconds) {
        QaseConfig config = new QaseConfig();
        config.testops = new TestopsConfig();
        config.testops.project = "TEST";
        config.testops.api.token = "test_token";
        config.testops.api.host = "qase.io";
        config.testops.api.timeoutSeconds = timeoutSeconds;
        return config;
    }

    // -----------------------------------------------------------------------
    // Tests for computeUploadTimeoutSeconds formula
    // -----------------------------------------------------------------------

    @Test
    void zeroByteAttachmentUsesBaseTimeout() {
        QaseConfig config = createConfig(0);
        ApiClientV1 client = new ApiClientV1(config);

        int result = client.computeUploadTimeoutSeconds(0L);

        assertEquals(BASE_UPLOAD_TIMEOUT_SECONDS, result,
                "Zero bytes should use the base timeout of 30s");
    }

    @Test
    void fiftyMbAttachmentUsesCorrectDynamicTimeout() {
        QaseConfig config = createConfig(0);
        ApiClientV1 client = new ApiClientV1(config);

        long fiftyMb = 50L * 1024 * 1024;
        int result = client.computeUploadTimeoutSeconds(fiftyMb);

        // 30 + (50 * 1024 * 1024) / (100 * 1024) = 30 + 512 = 542
        assertEquals(542, result,
                "50MB at 100KB/s min speed should yield 542s (30 + 512)");
    }

    @Test
    void extremelyLargeAttachmentIsCapedAtMaxTimeout() {
        QaseConfig config = createConfig(0);
        ApiClientV1 client = new ApiClientV1(config);

        long fiveHundredGb = 500L * 1024 * 1024 * 1024;
        int result = client.computeUploadTimeoutSeconds(fiveHundredGb);

        assertEquals(MAX_UPLOAD_TIMEOUT_SECONDS, result,
                "500GB should be capped at 3600s max timeout");
    }

    @Test
    void timeoutRestoredAfterUploadCompletes(@TempDir Path tempDir) throws Exception {
        // Set up config with a specific timeout so we can verify restoration
        QaseConfig config = createConfig(30); // 30s configured timeout
        ApiClientV1 client = new ApiClientV1(config);

        // Verify initial state: 30s configured => 30000ms
        io.qase.client.v1.ApiClient apiClient = client.getApiClient();
        assertEquals(30000, apiClient.getReadTimeout(), "Initial read timeout should be 30000ms");
        assertEquals(30000, apiClient.getWriteTimeout(), "Initial write timeout should be 30000ms");

        // Create a real file to upload (small, so timeout is just BASE 30s)
        File testFile = tempDir.resolve("test.txt").toFile();
        testFile.createNewFile();

        Attachment attachment = new Attachment();
        attachment.filePath = testFile.getAbsolutePath();
        attachment.fileName = "test.txt";

        // Mock AttachmentsApi so we don't make real HTTP calls
        try (MockedConstruction<AttachmentsApi> mocked = Mockito.mockConstruction(AttachmentsApi.class,
                (mock, context) -> {
                    AttachmentUploadsResponse mockResponse = Mockito.mock(AttachmentUploadsResponse.class);
                    Attachmentupload uploadResult = new Attachmentupload();
                    uploadResult.setHash("test-hash-abc");
                    when(mockResponse.getResult()).thenReturn(Collections.singletonList(uploadResult));
                    when(mock.uploadAttachment(anyString(), any())).thenReturn(mockResponse);
                })) {

            List<String> hashes = client.uploadAttachments(Collections.singletonList(attachment));
            assertTrue(hashes.contains("test-hash-abc"), "Should return uploaded hash");
        }

        // After upload, timeout must be restored to original 30000ms
        assertEquals(30000, apiClient.getReadTimeout(),
                "Read timeout must be restored to 30000ms after upload");
        assertEquals(30000, apiClient.getWriteTimeout(),
                "Write timeout must be restored to 30000ms after upload");
    }

    @Test
    void timeoutRestoredAfterUploadFails(@TempDir Path tempDir) throws Exception {
        // Verify timeout is restored even when upload throws an exception
        QaseConfig config = createConfig(30);
        ApiClientV1 client = new ApiClientV1(config);

        io.qase.client.v1.ApiClient apiClient = client.getApiClient();
        assertEquals(30000, apiClient.getReadTimeout(), "Initial read timeout should be 30000ms");

        File testFile = tempDir.resolve("fail.txt").toFile();
        testFile.createNewFile();

        Attachment attachment = new Attachment();
        attachment.filePath = testFile.getAbsolutePath();
        attachment.fileName = "fail.txt";

        // Mock AttachmentsApi to throw an exception
        try (MockedConstruction<AttachmentsApi> mocked = Mockito.mockConstruction(AttachmentsApi.class,
                (mock, context) -> {
                    when(mock.uploadAttachment(anyString(), any()))
                            .thenThrow(new RuntimeException("Simulated network error"));
                })) {

            // Should not throw — errors are caught and logged
            List<String> hashes = client.uploadAttachments(Collections.singletonList(attachment));
            assertTrue(hashes.isEmpty(), "On upload failure, no hashes returned");
        }

        // Timeout must still be restored despite exception in upload loop
        assertEquals(30000, apiClient.getReadTimeout(),
                "Read timeout must be restored to 30000ms even after upload failure");
        assertEquals(30000, apiClient.getWriteTimeout(),
                "Write timeout must be restored to 30000ms even after upload failure");
    }
}
