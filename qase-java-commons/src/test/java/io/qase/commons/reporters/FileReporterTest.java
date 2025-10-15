package io.qase.commons.reporters;

import io.qase.commons.QaseException;
import io.qase.commons.config.ConfigFactory;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.models.domain.Attachment;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.domain.TestResultStatus;
import io.qase.commons.models.report.ReportAttachment;
import io.qase.commons.models.report.ReportResult;
import io.qase.commons.models.report.Run;
import io.qase.commons.writers.Writer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
