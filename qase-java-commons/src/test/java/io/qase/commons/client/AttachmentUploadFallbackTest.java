package io.qase.commons.client;

import io.qase.client.v1.ApiException;
import io.qase.client.v1.api.AttachmentsApi;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.config.TestopsConfig;
import io.qase.commons.logger.Logger;
import io.qase.commons.models.domain.Attachment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Tests for UPLD-04: Graceful attachment degradation.
 *
 * When attachment upload fails, result is still submitted to Qase with empty
 * attachments list, and a warning log names the dropped files.
 */
class AttachmentUploadFallbackTest {

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

    @Test
    void uploadAttachmentsReturnsEmptyListWhenApiThrows(@TempDir Path tempDir) throws Exception {
        // Arrange: mock API that always throws
        AttachmentsApi mockApi = mock(AttachmentsApi.class);
        when(mockApi.uploadAttachment(anyString(), any()))
                .thenThrow(new ApiException("Network failure"));

        TestableApiClientV1 client = new TestableApiClientV1(createConfig(), mockApi);

        File testFile = tempDir.resolve("screenshot.png").toFile();
        testFile.createNewFile();

        Attachment attachment = new Attachment();
        attachment.filePath = testFile.getAbsolutePath();
        attachment.fileName = "screenshot.png";

        // Act
        List<String> result = client.uploadAttachments(Collections.singletonList(attachment));

        // Assert: returns empty list (result will be submitted without attachments)
        assertNotNull(result, "Return value must not be null");
        assertTrue(result.isEmpty(),
                "When upload fails, empty list must be returned so result is still sent without attachments");
    }

    @Test
    void uploadAttachmentsLogsWarningWithDroppedFileNamesOnFailure(@TempDir Path tempDir) throws Exception {
        // Arrange: capture initial WARN count
        Logger logger = Logger.getInstance();
        // Flush any pending async messages from other tests
        Thread.sleep(100);
        long warnBefore = logger.getStatistics().get("WARN");

        AttachmentsApi mockApi = mock(AttachmentsApi.class);
        when(mockApi.uploadAttachment(anyString(), any()))
                .thenThrow(new ApiException("Upload refused"));

        TestableApiClientV1 client = new TestableApiClientV1(createConfig(), mockApi);

        File testFile = tempDir.resolve("report.xml").toFile();
        testFile.createNewFile();

        Attachment attachment = new Attachment();
        attachment.filePath = testFile.getAbsolutePath();
        attachment.fileName = "report.xml";

        // Act
        client.uploadAttachments(Collections.singletonList(attachment));

        // Allow async logger to process the message
        Thread.sleep(200);

        // Assert: a WARN was emitted (file name mention in the warning log)
        long warnAfter = logger.getStatistics().get("WARN");
        assertTrue(warnAfter > warnBefore,
                "A WARN log must be emitted when attachment upload fails; warnBefore=" + warnBefore + ", warnAfter=" + warnAfter);
    }

    @Test
    void uploadAttachmentsWarnsAboutMultipleDroppedFiles(@TempDir Path tempDir) throws Exception {
        // Arrange
        Logger logger = Logger.getInstance();
        Thread.sleep(100);
        long warnBefore = logger.getStatistics().get("WARN");

        AttachmentsApi mockApi = mock(AttachmentsApi.class);
        when(mockApi.uploadAttachment(anyString(), any()))
                .thenThrow(new ApiException("Batch upload error"));

        TestableApiClientV1 client = new TestableApiClientV1(createConfig(), mockApi);

        // Create two attachment files
        File file1 = tempDir.resolve("trace1.log").toFile();
        File file2 = tempDir.resolve("trace2.log").toFile();
        file1.createNewFile();
        file2.createNewFile();

        Attachment att1 = new Attachment();
        att1.filePath = file1.getAbsolutePath();
        att1.fileName = "trace1.log";

        Attachment att2 = new Attachment();
        att2.filePath = file2.getAbsolutePath();
        att2.fileName = "trace2.log";

        // Act: both in single batch (both files small enough)
        List<String> result = client.uploadAttachments(List.of(att1, att2));

        Thread.sleep(200);

        // Assert: empty result, and warning was logged
        assertTrue(result.isEmpty(), "Both files dropped — result must be empty");
        long warnAfter = logger.getStatistics().get("WARN");
        assertTrue(warnAfter > warnBefore,
                "At least one WARN must be logged for dropped attachments");
    }

    @Test
    void uploadAttachmentsWithNoAttachmentsReturnsEmptyWithoutWarning() {
        // Arrange
        Logger logger = Logger.getInstance();
        long warnBefore = logger.getStatistics().get("WARN");

        AttachmentsApi mockApi = mock(AttachmentsApi.class);
        TestableApiClientV1 client = new TestableApiClientV1(createConfig(), mockApi);

        // Act: null and empty list should not produce warnings
        List<String> result1 = client.uploadAttachments(null);
        List<String> result2 = client.uploadAttachments(Collections.emptyList());

        long warnAfter = logger.getStatistics().get("WARN");

        // Assert: no warnings, empty results
        assertTrue(result1.isEmpty(), "Null attachments yields empty list");
        assertTrue(result2.isEmpty(), "Empty attachments list yields empty list");
        assertEquals(warnBefore, warnAfter, "No warnings should be logged for empty input");
    }
}
