package io.qase.commons.client;

import io.qase.client.v1.api.AttachmentsApi;
import io.qase.client.v1.models.AttachmentUploadsResponse;
import io.qase.client.v1.models.Attachmentupload;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.config.TestopsConfig;
import io.qase.commons.models.domain.Attachment;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests for UPLD-05: Disk-backed attachment staging with immediate memory release.
 *
 * Verifies that prepareFiles() writes contentBytes/content to temp files in
 * java.io.tmpdir, nulls out the references for GC, and cleans up temp files
 * after upload (success and failure).
 */
class TempFileAttachmentTest {

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
     * Creates a mock AttachmentsApi that returns a successful upload response with one hash.
     */
    private AttachmentsApi createSuccessMockApi() throws Exception {
        AttachmentsApi mockApi = mock(AttachmentsApi.class);
        AttachmentUploadsResponse mockResponse = mock(AttachmentUploadsResponse.class);
        Attachmentupload uploadResult = new Attachmentupload();
        uploadResult.setHash("test-hash-upld05");
        when(mockResponse.getResult()).thenReturn(Collections.singletonList(uploadResult));
        when(mockApi.uploadAttachment(anyString(), any())).thenReturn(mockResponse);
        return mockApi;
    }

    /**
     * UPLD-05: After uploadAttachments() with contentBytes, the byte[] reference
     * on the Attachment object must be null (released for GC).
     */
    @Test
    void contentBytesNulledAfterTempFileCreation() throws Exception {
        AttachmentsApi mockApi = createSuccessMockApi();
        TestableApiClientV1 client = new TestableApiClientV1(createConfig(), mockApi);

        Attachment attachment = new Attachment();
        attachment.fileName = "binary-data.bin";
        attachment.mimeType = "application/octet-stream";
        attachment.contentBytes = new byte[1024];

        // Act
        client.uploadAttachments(Collections.singletonList(attachment));

        // Assert: contentBytes must be nulled after temp file write
        assertNull(attachment.contentBytes,
                "attachment.contentBytes must be null after prepareFiles() writes it to a temp file");
    }

    /**
     * UPLD-05: After uploadAttachments() with String content, the String reference
     * on the Attachment object must be null (released for GC).
     */
    @Test
    void contentStringNulledAfterTempFileCreation() throws Exception {
        AttachmentsApi mockApi = createSuccessMockApi();
        TestableApiClientV1 client = new TestableApiClientV1(createConfig(), mockApi);

        Attachment attachment = new Attachment();
        attachment.fileName = "log-output.txt";
        attachment.mimeType = "text/plain";
        attachment.content = "test content for UPLD-05";

        // Act
        client.uploadAttachments(Collections.singletonList(attachment));

        // Assert: content must be nulled after temp file write
        assertNull(attachment.content,
                "attachment.content must be null after prepareFiles() writes it to a temp file");
    }

    /**
     * UPLD-05: Temp files created from contentBytes must reside in java.io.tmpdir,
     * not in user.dir (old behavior).
     */
    @Test
    void tempFileCreatedInTempDir() throws Exception {
        AttachmentsApi mockApi = mock(AttachmentsApi.class);
        AttachmentUploadsResponse mockResponse = mock(AttachmentUploadsResponse.class);
        Attachmentupload uploadResult = new Attachmentupload();
        uploadResult.setHash("test-hash-tmpdir");
        when(mockResponse.getResult()).thenReturn(Collections.singletonList(uploadResult));

        // Use ArgumentCaptor to capture the list of File objects passed to the API
        ArgumentCaptor<List<File>> filesCaptor = ArgumentCaptor.forClass(List.class);
        when(mockApi.uploadAttachment(anyString(), filesCaptor.capture())).thenReturn(mockResponse);

        TestableApiClientV1 client = new TestableApiClientV1(createConfig(), mockApi);

        Attachment attachment = new Attachment();
        attachment.fileName = "capture-test.bin";
        attachment.mimeType = "application/octet-stream";
        attachment.contentBytes = new byte[512];

        // Act
        client.uploadAttachments(Collections.singletonList(attachment));

        // Assert: the file passed to the API must be in java.io.tmpdir
        List<List<File>> allCaptures = filesCaptor.getAllValues();
        assertFalse(allCaptures.isEmpty(), "API must have been called with at least one file list");

        String tmpDir = System.getProperty("java.io.tmpdir");
        // Normalize path separators
        String normalizedTmpDir = new File(tmpDir).getCanonicalPath();

        List<File> capturedFiles = allCaptures.get(0);
        assertFalse(capturedFiles.isEmpty(), "Captured file list must not be empty");
        File capturedFile = capturedFiles.get(0);

        String capturedParent = capturedFile.getParentFile().getCanonicalPath();
        assertEquals(normalizedTmpDir, capturedParent,
                "Temp file must be created in java.io.tmpdir, not user.dir. Got: " + capturedParent);
    }

    /**
     * UPLD-05: Temp files created from contentBytes must be deleted after a successful upload
     * (cleaned up by cleanupFileInfos()).
     */
    @Test
    void tempFileDeletedAfterSuccessfulUpload() throws Exception {
        AttachmentsApi mockApi = mock(AttachmentsApi.class);
        AttachmentUploadsResponse mockResponse = mock(AttachmentUploadsResponse.class);
        Attachmentupload uploadResult = new Attachmentupload();
        uploadResult.setHash("test-hash-cleanup-success");
        when(mockResponse.getResult()).thenReturn(Collections.singletonList(uploadResult));

        // Capture the file to check its existence after upload
        ArgumentCaptor<List<File>> filesCaptor = ArgumentCaptor.forClass(List.class);
        when(mockApi.uploadAttachment(anyString(), filesCaptor.capture())).thenReturn(mockResponse);

        TestableApiClientV1 client = new TestableApiClientV1(createConfig(), mockApi);

        Attachment attachment = new Attachment();
        attachment.fileName = "cleanup-success.bin";
        attachment.mimeType = "application/octet-stream";
        attachment.contentBytes = new byte[256];

        // Act
        client.uploadAttachments(Collections.singletonList(attachment));

        // Assert: the temp file must no longer exist after upload
        List<List<File>> allCaptures = filesCaptor.getAllValues();
        assertFalse(allCaptures.isEmpty(), "API must have been called");

        File tempFile = allCaptures.get(0).get(0);
        assertFalse(tempFile.exists(),
                "Temp file must be deleted after successful upload. File: " + tempFile.getAbsolutePath());
    }

    /**
     * UPLD-05: Temp files created from contentBytes must be deleted even when upload fails
     * (cleaned up by cleanupFileInfos() in the finally block).
     */
    @Test
    void tempFileDeletedAfterFailedUpload() throws Exception {
        AttachmentsApi mockApi = mock(AttachmentsApi.class);

        // Capture the file BEFORE the exception is thrown
        // We need to capture the file argument to check deletion
        final File[] capturedFile = {null};

        doAnswer(invocation -> {
            List<File> files = invocation.getArgument(1);
            if (!files.isEmpty()) {
                capturedFile[0] = files.get(0);
            }
            throw new io.qase.client.v1.ApiException("Simulated upload failure");
        }).when(mockApi).uploadAttachment(anyString(), any());

        TestableApiClientV1 client = new TestableApiClientV1(createConfig(), mockApi);

        Attachment attachment = new Attachment();
        attachment.fileName = "cleanup-failure.bin";
        attachment.mimeType = "application/octet-stream";
        attachment.contentBytes = new byte[128];

        // Act: should not throw — errors are caught internally
        List<String> result = client.uploadAttachments(Collections.singletonList(attachment));

        // Assert: upload returned empty (failed), and temp file was deleted
        assertTrue(result.isEmpty(), "Upload failure must return empty list");
        assertNotNull(capturedFile[0], "A temp file must have been created and passed to the API");
        assertFalse(capturedFile[0].exists(),
                "Temp file must be deleted after failed upload. File: " + capturedFile[0].getAbsolutePath());
    }
}
