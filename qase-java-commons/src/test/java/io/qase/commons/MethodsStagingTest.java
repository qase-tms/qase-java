package io.qase.commons;

import io.qase.commons.models.domain.Attachment;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.domain.TestResultExecution;
import io.qase.commons.models.domain.TestResultStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for UPLD-05: Early disk-backed attachment staging in Methods.
 * Verifies that addAttachment(byte[]) and addAttachment(String) write temp files
 * immediately, set filePath/isStagedTempFile/sizeBytes, and do NOT retain in-memory data.
 */
class MethodsStagingTest {

    @BeforeEach
    void setUp() {
        // Ensure a case is in progress so attachments can be added
        TestResult testResult = new TestResult();
        testResult.title = "staging-test";
        testResult.execution = new TestResultExecution();
        testResult.execution.status = TestResultStatus.PASSED;
        CasesStorage.startCase(testResult);
    }

    @AfterEach
    void tearDown() {
        if (CasesStorage.isCaseInProgress()) {
            TestResult result = CasesStorage.getCurrentCase();
            // Clean up any staged temp files
            for (Attachment a : result.attachments) {
                if (a.isStagedTempFile && a.filePath != null) {
                    new File(a.filePath).delete();
                }
            }
            CasesStorage.stopCase();
        }
    }

    @Test
    void addAttachmentByteArrayCreatesTempFile() {
        byte[] content = "Hello, staging!".getBytes();
        Methods.addAttachment("test.bin", content, "application/octet-stream");

        TestResult result = CasesStorage.getCurrentCase();
        assertEquals(1, result.attachments.size());

        Attachment a = result.attachments.get(0);
        assertNotNull(a.filePath, "filePath should be set to temp file path");
        assertTrue(a.isStagedTempFile, "isStagedTempFile should be true");
        assertEquals(content.length, a.sizeBytes, "sizeBytes should match original content length");
        assertNull(a.contentBytes, "contentBytes should NOT be set (data is on disk)");
        assertEquals("test.bin", a.fileName);
        assertEquals("application/octet-stream", a.mimeType);

        // Verify the temp file exists and contains the correct data
        File tempFile = new File(a.filePath);
        assertTrue(tempFile.exists(), "Temp file should exist on disk");
        assertEquals(content.length, tempFile.length(), "Temp file size should match content");
    }

    @Test
    void addAttachmentStringCreatesTempFile() throws Exception {
        String content = "Hello, string staging!";
        Methods.addAttachment("test.txt", content, "text/plain");

        TestResult result = CasesStorage.getCurrentCase();
        assertEquals(1, result.attachments.size());

        Attachment a = result.attachments.get(0);
        assertNotNull(a.filePath, "filePath should be set to temp file path");
        assertTrue(a.isStagedTempFile, "isStagedTempFile should be true");
        assertEquals(content.length(), a.sizeBytes, "sizeBytes should match original content length");
        assertNull(a.content, "content should NOT be set (data is on disk)");
        assertEquals("test.txt", a.fileName);
        assertEquals("text/plain", a.mimeType);

        // Verify the temp file exists and contains the correct data
        File tempFile = new File(a.filePath);
        assertTrue(tempFile.exists(), "Temp file should exist on disk");
        String fileContent = new String(Files.readAllBytes(tempFile.toPath()));
        assertEquals(content, fileContent, "Temp file should contain the original string content");
    }

    @Test
    void addAttachmentsFromPathSetsIsStagedTempFileFalse() throws Exception {
        // Create a real file to reference
        File realFile = File.createTempFile("test-real-", ".txt");
        realFile.deleteOnExit();
        Files.write(realFile.toPath(), "real file content".getBytes());

        Methods.addAttachments(realFile.getAbsolutePath());

        TestResult result = CasesStorage.getCurrentCase();
        assertEquals(1, result.attachments.size());

        Attachment a = result.attachments.get(0);
        assertEquals(realFile.getAbsolutePath(), a.filePath);
        assertFalse(a.isStagedTempFile, "User-provided file paths should NOT be marked as staged temp files");
        assertEquals(realFile.length(), a.sizeBytes, "sizeBytes should match the file size on disk");
    }
}
