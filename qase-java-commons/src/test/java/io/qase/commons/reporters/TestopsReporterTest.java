package io.qase.commons.reporters;

import io.qase.commons.QaseException;
import io.qase.commons.client.ApiClient;
import io.qase.commons.config.TestopsConfig;
import io.qase.commons.models.domain.TestResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TestopsReporterTest {

    private ApiClient mockClient;
    private TestopsConfig mockConfig;
    private TestopsReporter reporter;

    @BeforeEach
    public void setUp() {
        mockClient = mock(ApiClient.class);
        mockConfig = mock(TestopsConfig.class);

        mockConfig = new TestopsConfig();
        mockConfig.batch.size = 2;
        mockConfig.run.id = 0;

        reporter = new TestopsReporter(mockConfig, mockClient);
    }

    @Test
    public void testStartTestRunWithExistingRunId() throws QaseException {
        mockConfig.run.id = 123;

        reporter.startTestRun();

        verify(mockClient, never()).createTestRun();
    }

    @Test
    public void testStartTestRunWithoutExistingRunId() throws QaseException {
        mockConfig.run.id = 0;
        when(mockClient.createTestRun()).thenReturn(456L);

        reporter.startTestRun();

        verify(mockClient, times(1)).createTestRun();
    }

    @Test
    public void testCompleteTestRun() throws QaseException {
        reporter.testRunId = 123L;

        reporter.completeTestRun();

        verify(mockClient, times(1)).completeTestRun(123L);
    }

    @Test
    public void testAddResultAndUploadWhenBatchSizeReached() throws QaseException {
        TestResult result1 = new TestResult();
        TestResult result2 = new TestResult();
        List<TestResult> results = new ArrayList<>();
        results.add(result1);
        results.add(result2);

        reporter.addResult(result1);
        reporter.addResult(result2);

        verify(mockClient, times(1)).uploadResults(any(Long.class), eq(results));
        assertDoesNotThrow(() -> reporter.addResult(result1));
    }

    @Test
    public void testUploadResultsWithPartialBatch() throws QaseException {
        TestResult result1 = new TestResult();
        TestResult result2 = new TestResult();
        TestResult result3 = new TestResult();

        reporter.addResult(result1);
        reporter.addResult(result2);
        reporter.addResult(result3);

        verify(mockClient, times(2)).uploadResults(any(Long.class), anyList());
    }

    @Test
    public void testUploadResultsWithExactBatch() throws QaseException {
        TestResult result1 = new TestResult();
        TestResult result2 = new TestResult();

        reporter.addResult(result1);
        reporter.addResult(result2);

        verify(mockClient, times(1)).uploadResults(any(Long.class), anyList());
    }
}
