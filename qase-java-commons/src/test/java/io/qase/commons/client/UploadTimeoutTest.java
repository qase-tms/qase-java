package io.qase.commons.client;

import io.qase.client.v1.ApiException;
import io.qase.client.v1.api.AttachmentsApi;
import io.qase.client.v1.models.AttachmentUploadsResponse;
import io.qase.client.v1.models.Attachmentupload;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.config.TestopsConfig;
import io.qase.commons.models.domain.Attachment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
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

    private QaseConfig createConfig(int timeoutSeconds) {
        QaseConfig config = new QaseConfig();
        config.testops = new TestopsConfig();
        config.testops.project = "TEST";
        config.testops.api.token = "test_token";
        config.testops.api.host = "qase.io";
        config.testops.api.timeoutSeconds = timeoutSeconds;
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

    // -----------------------------------------------------------------------
    // Tests for computeUploadTimeoutSeconds formula
    // -----------------------------------------------------------------------

    @Test
    void zeroByteAttachmentUsesBaseTimeout() {
        ApiClientV1 client = new ApiClientV1(createConfig(0));

        int result = client.computeUploadTimeoutSeconds(0L);

        assertEquals(BASE_UPLOAD_TIMEOUT_SECONDS, result,
                "Zero bytes should use the base timeout of 30s");
    }

    @Test
    void fiftyMbAttachmentUsesCorrectDynamicTimeout() {
        ApiClientV1 client = new ApiClientV1(createConfig(0));

        long fiftyMb = 50L * 1024 * 1024;
        int result = client.computeUploadTimeoutSeconds(fiftyMb);

        // 30 + (50 * 1024 * 1024) / (100 * 1024) = 30 + 512 = 542
        assertEquals(542, result,
                "50MB at 100KB/s min speed should yield 542s (30 + 512)");
    }

    @Test
    void extremelyLargeAttachmentIsCappedAtMaxTimeout() {
        ApiClientV1 client = new ApiClientV1(createConfig(0));

        long fiveHundredGb = 500L * 1024 * 1024 * 1024;
        int result = client.computeUploadTimeoutSeconds(fiveHundredGb);

        assertEquals(MAX_UPLOAD_TIMEOUT_SECONDS, result,
                "500GB should be capped at 3600s max timeout");
    }

    // -----------------------------------------------------------------------
    // Tests for timeout save/restore behaviour during uploadAttachments()
    // -----------------------------------------------------------------------

    @Test
    void timeoutRestoredAfterUploadCompletes(@TempDir Path tempDir) throws Exception {
        // Arrange: configure with a known 30s timeout
        QaseConfig config = createConfig(30);

        AttachmentUploadsResponse mockResponse = Mockito.mock(AttachmentUploadsResponse.class);
        Attachmentupload uploadResult = new Attachmentupload();
        uploadResult.setHash("test-hash-abc");
        when(mockResponse.getResult()).thenReturn(Collections.singletonList(uploadResult));

        AttachmentsApi mockApi = Mockito.mock(AttachmentsApi.class);
        when(mockApi.uploadAttachment(anyString(), any())).thenReturn(mockResponse);

        TestableApiClientV1 client = new TestableApiClientV1(config, mockApi);

        io.qase.client.v1.ApiClient apiClient = client.getApiClient();
        assertEquals(30000, apiClient.getReadTimeout(), "Initial read timeout should be 30000ms");
        assertEquals(30000, apiClient.getWriteTimeout(), "Initial write timeout should be 30000ms");

        // Create a real (empty) file so prepareFiles() doesn't skip it
        File testFile = tempDir.resolve("test.txt").toFile();
        testFile.createNewFile();

        Attachment attachment = new Attachment();
        attachment.filePath = testFile.getAbsolutePath();
        attachment.fileName = "test.txt";

        // Act
        List<String> hashes = client.uploadAttachments(Collections.singletonList(attachment));

        // Assert: hash returned and timeout restored
        assertTrue(hashes.contains("test-hash-abc"), "Should return uploaded hash");
        assertEquals(30000, apiClient.getReadTimeout(),
                "Read timeout must be restored to 30000ms after upload completes");
        assertEquals(30000, apiClient.getWriteTimeout(),
                "Write timeout must be restored to 30000ms after upload completes");
    }

    @Test
    void timeoutRestoredAfterUploadFails(@TempDir Path tempDir) throws Exception {
        // Arrange: configure with a known 30s timeout
        QaseConfig config = createConfig(30);

        AttachmentsApi mockApi = Mockito.mock(AttachmentsApi.class);
        when(mockApi.uploadAttachment(anyString(), any()))
                .thenThrow(new ApiException("Simulated network error"));

        TestableApiClientV1 client = new TestableApiClientV1(config, mockApi);

        io.qase.client.v1.ApiClient apiClient = client.getApiClient();
        assertEquals(30000, apiClient.getReadTimeout(), "Initial read timeout should be 30000ms");

        File testFile = tempDir.resolve("fail.txt").toFile();
        testFile.createNewFile();

        Attachment attachment = new Attachment();
        attachment.filePath = testFile.getAbsolutePath();
        attachment.fileName = "fail.txt";

        // Act: should not throw — errors are caught and logged internally
        List<String> hashes = client.uploadAttachments(Collections.singletonList(attachment));

        // Assert: no hashes and timeout restored
        assertTrue(hashes.isEmpty(), "On upload failure, no hashes should be returned");
        assertEquals(30000, apiClient.getReadTimeout(),
                "Read timeout must be restored to 30000ms even after upload failure");
        assertEquals(30000, apiClient.getWriteTimeout(),
                "Write timeout must be restored to 30000ms even after upload failure");
    }
}
