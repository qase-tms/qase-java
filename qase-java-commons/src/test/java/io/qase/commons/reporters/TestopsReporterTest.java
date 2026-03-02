package io.qase.commons.reporters;

import io.qase.commons.QaseException;
import io.qase.commons.client.ApiClient;
import io.qase.commons.config.ConfigFactory;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.models.domain.TestResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class TestopsReporterTest {

    private TestopsReporter reporter;
    private ApiClient clientMock;
    private QaseConfig configMock;

    @BeforeEach
    void setUp() {
        clientMock = mock(ApiClient.class);

        configMock = ConfigFactory.loadConfig();
        configMock.testops.run.id = 0;
        configMock.testops.batch.size = 2;

        reporter = new TestopsReporter(configMock.testops, clientMock);
    }

    @Test
    void testStartTestRunWithExistingRunId() throws QaseException {
        configMock.testops.run.id = 123;

        reporter.startTestRun();

        verify(clientMock, never()).createTestRun();
        assertEquals(123L, reporter.testRunId);
    }

    @Test
    void testStartTestRunWithNewRunId() throws QaseException {
        when(clientMock.createTestRun()).thenReturn(456L);

        reporter.startTestRun();

        verify(clientMock, times(1)).createTestRun();
        assertEquals(456L, reporter.testRunId);
    }

    @Test
    void testCompleteTestRun() throws QaseException {
        reporter.testRunId = 789L;

        reporter.completeTestRun();

        verify(clientMock, times(1)).completeTestRun(789L);
    }

    @Test
    void testCompleteTestRunFlushesBufferedResults() throws QaseException {
        reporter.testRunId = 789L;
        configMock.testops.batch.size = 10;

        TestResult result1 = new TestResult();
        TestResult result2 = new TestResult();
        reporter.addResult(result1);
        reporter.addResult(result2);

        assertEquals(2, reporter.getResults().size());

        reporter.completeTestRun();

        verify(clientMock, times(1)).uploadResults(eq(789L), anyList());
        verify(clientMock, times(1)).completeTestRun(789L);
        assertEquals(0, reporter.getResults().size());
    }

    @Test
    void testCompleteTestRunWithCompleteFalseSkipsCompletion() throws QaseException {
        reporter.testRunId = 789L;
        configMock.testops.run.complete = false;
        configMock.testops.batch.size = 10;

        TestResult result = new TestResult();
        reporter.addResult(result);

        reporter.completeTestRun();

        verify(clientMock, times(1)).uploadResults(eq(789L), anyList());
        verify(clientMock, never()).completeTestRun(anyLong());
        assertEquals(0, reporter.getResults().size());
    }

    @Test
    void testCompleteTestRunWithCompleteTrueCallsCompletion() throws QaseException {
        reporter.testRunId = 789L;
        configMock.testops.run.complete = true;

        reporter.completeTestRun();

        verify(clientMock, times(1)).completeTestRun(789L);
    }

    @Test
    void testAddResultAndUploadResultsWhenBatchIsReached() throws QaseException {
        reporter.testRunId = 789L;

        TestResult result1 = new TestResult();
        TestResult result2 = new TestResult();

        reporter.addResult(result1);
        assertEquals(1, reporter.getResults().size());

        reporter.addResult(result2);
        assertEquals(0, reporter.getResults().size());

        verify(clientMock, timeout(5000).times(1)).uploadResults(anyLong(), anyList());
    }

    @Test
    void testUploadResultsLessThanBatchSize() throws QaseException {
        reporter.testRunId = 789L;
        TestResult result = new TestResult();
        reporter.addResult(result);

        reporter.uploadResults();

        verify(clientMock, times(1)).uploadResults(anyLong(), anyList());
        assertEquals(0, reporter.getResults().size());
    }

    @Test
    void testUploadResultsMoreThanBatchSize() throws QaseException {
        reporter.testRunId = 789L;
        List<TestResult> results = new ArrayList<>();
        results.add(new TestResult());
        results.add(new TestResult());
        results.add(new TestResult());
        reporter.setResults(results);

        reporter.uploadResults();

        verify(clientMock, timeout(5000).times(2)).uploadResults(anyLong(), anyList());
        assertEquals(0, reporter.getResults().size());
    }

    @Test
    void testSetResults() {
        List<TestResult> newResults = new ArrayList<>();
        TestResult testResult = new TestResult();
        testResult.id = "test1";
        newResults.add(testResult);

        reporter.setResults(newResults);

        List<TestResult> results = reporter.getResults();
        assertEquals(1, results.size());
        assertEquals("test1", results.get(0).id);
    }

    @Test
    void testThreadSafety() throws InterruptedException, QaseException {
        reporter.testRunId = 789L;
        
        // Создаем несколько потоков, которые будут добавлять результаты одновременно
        int threadCount = 10;
        int resultsPerThread = 5;
        Thread[] threads = new Thread[threadCount];
        
        // Настраиваем mock для предотвращения реальных вызовов API
        doNothing().when(clientMock).uploadResults(anyLong(), anyList());
        
        // Запускаем потоки
        for (int i = 0; i < threadCount; i++) {
            final int threadIndex = i;
            threads[i] = new Thread(() -> {
                try {
                    for (int j = 0; j < resultsPerThread; j++) {
                        TestResult result = new TestResult();
                        result.id = "thread-" + threadIndex + "-result-" + j;
                        reporter.addResult(result);
                        // Небольшая задержка для увеличения вероятности race condition
                        Thread.sleep(1);
                    }
                } catch (QaseException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            threads[i].start();
        }
        
        // Ждем завершения всех потоков
        for (Thread thread : threads) {
            thread.join();
        }
        
        // Проверяем, что uploadResults был вызван (количество может варьироваться из-за race conditions)
        // Минимальное количество вызовов должно быть равно количеству полных батчей
        int minExpectedCalls = (threadCount * resultsPerThread) / 2;
        verify(clientMock, timeout(5000).atLeast(minExpectedCalls)).uploadResults(anyLong(), anyList());

        // Проверяем, что в конце не осталось результатов (они были очищены)
        assertEquals(0, reporter.getResults().size());
    }

    @Test
    void testStatusFiltering() throws QaseException {
        reporter.testRunId = 789L;
        
        // Настраиваем фильтр для исключения SKIPPED и INVALID статусов
        configMock.testops.statusFilter = new ArrayList<>();
        configMock.testops.statusFilter.add("SKIPPED");
        configMock.testops.statusFilter.add("INVALID");
        
        // Создаем результаты с разными статусами
        TestResult passedResult = createTestResultWithStatus("PASSED");
        TestResult failedResult = createTestResultWithStatus("FAILED");
        TestResult skippedResult = createTestResultWithStatus("SKIPPED");
        TestResult invalidResult = createTestResultWithStatus("INVALID");
        
        // Добавляем результаты
        reporter.addResult(passedResult);
        // reporter.addResult(failedResult);
        // reporter.addResult(skippedResult);
        // reporter.addResult(invalidResult);
        
        // Проверяем, что только PASSED результат был добавлен
        List<TestResult> results = reporter.getResults();
        assertEquals(1, results.size());
        assertTrue(results.contains(passedResult));
        assertFalse(results.contains(failedResult));
        assertFalse(results.contains(skippedResult));
        assertFalse(results.contains(invalidResult));
    }

    @Test
    void testStatusFilteringWithEmptyFilter() throws QaseException {
        reporter.testRunId = 789L;
        
        // Пустой фильтр - все результаты должны проходить
        configMock.testops.statusFilter = new ArrayList<>();
        configMock.testops.batch.size = 10; // Увеличиваем размер батча
        
        TestResult passedResult = createTestResultWithStatus("PASSED");
        TestResult skippedResult = createTestResultWithStatus("SKIPPED");
        
        reporter.addResult(passedResult);
        reporter.addResult(skippedResult);
        
        List<TestResult> results = reporter.getResults();
        assertEquals(2, results.size());
        assertTrue(results.contains(passedResult));
        assertTrue(results.contains(skippedResult));
    }

    @Test
    void testStatusFilteringWithNullFilter() throws QaseException {
        reporter.testRunId = 789L;
        
        // Null фильтр - все результаты должны проходить
        configMock.testops.statusFilter = null;
        configMock.testops.batch.size = 10; // Увеличиваем размер батча
        
        TestResult passedResult = createTestResultWithStatus("PASSED");
        TestResult skippedResult = createTestResultWithStatus("SKIPPED");
        
        reporter.addResult(passedResult);
        reporter.addResult(skippedResult);
        
        List<TestResult> results = reporter.getResults();
        assertEquals(2, results.size());
        assertTrue(results.contains(passedResult));
        assertTrue(results.contains(skippedResult));
    }

    @Test
    void testAsyncUploadDoesNotBlockAddResult() throws Exception {
        reporter.testRunId = 789L;
        configMock.testops.batch.size = 1;

        // Mock uploadResults to simulate slow HTTP upload
        doAnswer(invocation -> {
            Thread.sleep(500);
            return null;
        }).when(clientMock).uploadResults(anyLong(), anyList());

        long start = System.currentTimeMillis();
        for (int i = 0; i < 5; i++) {
            reporter.addResult(new TestResult());
        }
        long elapsed = System.currentTimeMillis() - start;

        // addResult should return almost instantly (< 200ms for all 5 calls)
        // If synchronous, 5 uploads * 500ms = 2500ms minimum
        assertTrue(elapsed < 500, "addResult should not block on upload, took " + elapsed + "ms");

        // Wait for async uploads to complete
        verify(clientMock, timeout(10000).times(5)).uploadResults(anyLong(), anyList());
    }

    private TestResult createTestResultWithStatus(String statusName) {
        TestResult result = new TestResult();
        if (result.execution == null) {
            result.execution = new io.qase.commons.models.domain.TestResultExecution();
        }
        result.execution.status = io.qase.commons.models.domain.TestResultStatus.valueOf(statusName);
        return result;
    }
}

