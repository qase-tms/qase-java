package io.qase.commons.reporters;

import io.qase.commons.QaseException;
import io.qase.commons.config.ConfigFactory;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.models.domain.Attachment;
import io.qase.commons.models.domain.SuiteData;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.domain.TestResultStatus;
import io.qase.commons.models.report.ReportAttachment;
import io.qase.commons.models.report.ReportResult;
import io.qase.commons.models.report.Run;
import io.qase.commons.models.report.RunExecution;
import io.qase.commons.models.report.RunStats;
import io.qase.commons.models.report.ShortReportResult;
import io.qase.commons.writers.Writer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FileReporterTest {
    private FileReporter fileReporter;
    private Writer writerMock;

    @BeforeEach
    void setUp() {
        writerMock = mock(Writer.class);

        QaseConfig qaseConfig = ConfigFactory.loadConfig();
        qaseConfig.testops.run.title = "Test Run Title";
        qaseConfig.environment = "Test Environment";

        fileReporter = new FileReporter(qaseConfig, writerMock);
    }

    @Test
    void testStartTestRun() throws QaseException {
        fileReporter.startTestRun();

        verify(writerMock, times(1)).prepare();
    }

    @Test
    void testCompleteTestRun() throws QaseException {
        TestResult testResult = this.prepareResult();
        
        ReportAttachment mockAttachment = new ReportAttachment();
        mockAttachment.id = "test-id";
        mockAttachment.fileName = "test.txt";
        mockAttachment.filePath = "attachment-path";
        when(writerMock.writeAttachment(any())).thenReturn(mockAttachment);

        fileReporter.startTestRun();
        fileReporter.addResult(testResult);
        fileReporter.completeTestRun();

        verify(writerMock, times(1)).writeResult(any(ReportResult.class));
        verify(writerMock, times(1)).writeRun(any(Run.class));
    }

    @Test
    void testAddResult() throws QaseException {
        TestResult testResult = this.prepareResult();

        fileReporter.addResult(testResult);

        List<TestResult> results = fileReporter.getResults();
        assertEquals(1, results.size());
        assertEquals("test1", results.get(0).id);
    }

    @Test
    void testConvertTestResult() throws QaseException {
        TestResult testResult = this.prepareResult();

        ReportAttachment mockAttachment = new ReportAttachment();
        mockAttachment.id = "test-id";
        mockAttachment.fileName = "test.txt";
        mockAttachment.filePath = "attachment-path";
        when(writerMock.writeAttachment(any())).thenReturn(mockAttachment);

        fileReporter.startTestRun();
        fileReporter.addResult(testResult);
        fileReporter.completeTestRun();

        verify(writerMock, times(1)).writeResult(any(ReportResult.class));
        verify(writerMock, times(1)).writeAttachment(any());
    }

    @Test
    void testSetResults() {
        List<TestResult> newResults = new ArrayList<>();
        TestResult testResult = this.prepareResult();
        newResults.add(testResult);

        fileReporter.setResults(newResults);

        List<TestResult> results = fileReporter.getResults();
        assertEquals(1, results.size());
        assertEquals("test1", results.get(0).id);
    }

    @Test
    void testThreadSafety() throws InterruptedException, QaseException {
        // Создаем несколько потоков, которые будут добавлять результаты одновременно
        int threadCount = 10;
        int resultsPerThread = 5;
        Thread[] threads = new Thread[threadCount];
        
        // Запускаем потоки
        for (int i = 0; i < threadCount; i++) {
            final int threadIndex = i;
            threads[i] = new Thread(() -> {
                try {
                    for (int j = 0; j < resultsPerThread; j++) {
                        TestResult result = new TestResult();
                        result.id = "thread-" + threadIndex + "-result-" + j;
                        try {
                            fileReporter.addResult(result);
                        } catch (QaseException e) {
                            throw new RuntimeException(e);
                        }
                        // Небольшая задержка для увеличения вероятности race condition
                        Thread.sleep(1);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            threads[i].start();
        }
        
        // Ждем завершения всех потоков
        for (Thread thread : threads) {
            thread.join();
        }
        
        // Проверяем, что все результаты были добавлены без исключений
        int expectedTotalResults = threadCount * resultsPerThread;
        assertEquals(expectedTotalResults, fileReporter.getResults().size());
    }

    @Test
    void testRunStatsTracking() {
        RunStats stats = new RunStats();

        // Track passed
        ShortReportResult passed = new ShortReportResult();
        passed.status = "passed";
        stats.track(passed, false);

        // Track failed
        ShortReportResult failed = new ShortReportResult();
        failed.status = "failed";
        stats.track(failed, false);

        // Track skipped
        ShortReportResult skipped = new ShortReportResult();
        skipped.status = "skipped";
        stats.track(skipped, false);

        // Track blocked
        ShortReportResult blocked = new ShortReportResult();
        blocked.status = "blocked";
        stats.track(blocked, false);

        // Track invalid
        ShortReportResult invalid = new ShortReportResult();
        invalid.status = "invalid";
        stats.track(invalid, true); // also muted

        assertEquals(1, stats.passed);
        assertEquals(1, stats.failed);
        assertEquals(1, stats.skipped);
        assertEquals(1, stats.blocked);
        assertEquals(1, stats.invalid);
        assertEquals(5, stats.total);
        assertEquals(1, stats.muted);
    }

    @Test
    void testRunExecutionDuration() {
        long startTime = 1000L;
        long endTime = 2000L;
        RunExecution execution = new RunExecution(startTime, endTime);

        // Duration should be endTime - startTime = 1000, NOT (endTime - startTime) * 1000
        assertEquals(1000, execution.duration);
    }

    @Test
    void testRunHostDataStructure() {
        Run run = new Run("Test", 1000L, 2000L, "env");
        Map<String, String> hostData = new java.util.HashMap<>();
        hostData.put("os", "Linux");
        hostData.put("java_version", "17");
        run.addHostData(hostData);

        // hostData should be a Map<String, String> with 2 entries
        assertEquals(2, run.hostData.size());
        assertEquals("Linux", run.hostData.get("os"));
        assertEquals("17", run.hostData.get("java_version"));
    }

    @Test
    void testRunJsonSerialization() {
        Run run = new Run("Test Run", 1000L, 2000L, "staging");
        Map<String, String> hostData = new java.util.HashMap<>();
        hostData.put("os", "Linux");
        run.addHostData(hostData);

        com.google.gson.Gson gson = new com.google.gson.Gson();
        String json = gson.toJson(run);

        // Verify snake_case field names from @SerializedName annotations
        assertTrue(json.contains("\"start_time\""));
        assertTrue(json.contains("\"end_time\""));
        assertTrue(json.contains("\"cumulative_duration\""));
        assertTrue(json.contains("\"host_data\""));

        // Verify host_data is a flat object, not array
        // Should contain: "host_data":{"os":"Linux"}
        assertTrue(json.contains("\"host_data\":{\"os\":\"Linux\"}"));

        // Verify no camelCase leakage for annotated fields
        assertFalse(json.contains("\"startTime\""));
        assertFalse(json.contains("\"endTime\""));
        assertFalse(json.contains("\"cumulativeDuration\""));
        assertFalse(json.contains("\"hostData\""));
    }

    @Test
    void testSuiteDataJsonSerialization() {
        SuiteData suiteData = new SuiteData();
        suiteData.title = "Login Suite";

        com.google.gson.Gson gson = new com.google.gson.Gson();

        // Default: publicId should be null (not 0)
        String jsonNull = gson.toJson(suiteData);
        assertTrue(jsonNull.contains("\"title\":\"Login Suite\""));
        // Gson does not serialize null fields by default, so public_id should be absent
        assertFalse(jsonNull.contains("\"public_id\""));
        assertFalse(jsonNull.contains("\"publicId\""));

        // With value set
        suiteData.publicId = 42;
        String jsonWithId = gson.toJson(suiteData);
        assertTrue(jsonWithId.contains("\"public_id\":42"));
        assertFalse(jsonWithId.contains("\"publicId\""));
    }

    private TestResult prepareResult(){
        TestResult testResult = new TestResult();
        testResult.id = "test1";
        testResult.title = "Test Title";
        testResult.testopsIds = Collections.singletonList(123L);

        testResult.execution.status = TestResultStatus.PASSED;
        testResult.execution.duration = 1000;
        testResult.execution.thread = "main";
        testResult.execution.startTime = 1000L;
        testResult.execution.endTime = 2000L;
        testResult.execution.stacktrace = "";
        testResult.attachments.add(new Attachment());

        return testResult;
    }
}
