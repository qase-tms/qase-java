package io.qase.commons.reporters;

import com.google.gson.Gson;
import io.qase.commons.QaseException;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.models.domain.Attachment;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.report.Run;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class FileReporterTest {
    private FileReporter fileReporter;

    @BeforeEach
    public void setUp() {
        QaseConfig config = mock(QaseConfig.class);
        config.report.connection.path = "test-reports";
        fileReporter = new FileReporter(config);
    }

    @Test
    public void testStartTestRun() {
        assertDoesNotThrow(() -> {
            fileReporter.startTestRun();
        });

        Path rootPath = Paths.get(System.getProperty("user.dir"), "test-reports");
        assert Files.exists(rootPath);

        Path resultsPath = Paths.get(rootPath.toString(), "results");
        assert Files.exists(resultsPath);

        Path attachmentPath = Paths.get(rootPath.toString(), "attachments");
        assert Files.exists(attachmentPath);
    }

    @Test
    public void testCompleteTestRun() throws QaseException, FileNotFoundException {
        fileReporter.startTestRun();

        assertDoesNotThrow(() -> {
            fileReporter.completeTestRun();
        });

        File runFile = new File(Paths.get(System.getProperty("user.dir"), "test-reports", "run.json").toString());
        assert runFile.exists();

        Gson gson = new Gson();
        Run run = gson.fromJson(new FileReader(runFile), Run.class);
        assert run != null;
    }

    @Test
    public void testAddResult() throws QaseException {
        TestResult result = mock(TestResult.class);
        fileReporter.addResult(result);

        assertEquals(1, fileReporter.results.size());
    }

    @Test
    public void testUploadResults() throws QaseException {
        TestResult result = mock(TestResult.class);
        fileReporter.addResult(result);

        assertDoesNotThrow(() -> {
            fileReporter.uploadResults();
        });

        String resultFileName = Paths.get(System.getProperty("user.dir"), "test-reports", "results", result.id + ".json").toString();
        File resultFile = new File(resultFileName);
        assert resultFile.exists();
    }

    @Test
    public void testSaveAttachment() {
        Attachment attachment = mock(Attachment.class);
        attachment.filePath = "path/to/file";

        assertDoesNotThrow(() -> {
            String savedPath = fileReporter.saveAttachment(attachment);
            File savedFile = new File(savedPath);
            assert savedFile.exists();
        });
    }
}
