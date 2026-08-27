package io.qase.commons.reporters;

import io.qase.commons.QaseException;
import io.qase.commons.client.ApiClient;
import io.qase.commons.config.ConfigFactory;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.domain.TestResultExecution;
import io.qase.commons.models.domain.TestResultStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * A run whose result batches were dropped must NOT be marked complete.
 *
 * Regression guard for qase-python#504: a batch that failed to upload was lost
 * silently and the run was still completed, so CI stayed green over missing
 * data. An open run is a visible signal; a completed run over partial data is
 * not.
 */
class TestopsReporterFailedBatchTest {

    private ApiClient clientMock;
    private QaseConfig config;

    @BeforeEach
    void setUp() throws QaseException {
        clientMock = mock(ApiClient.class);

        config = ConfigFactory.loadConfig();
        config.testops.run.id = 0;
        config.testops.run.complete = true;
        // Large batch size so addResult() does not auto-trigger an upload
        config.testops.batch.size = 200;
        config.testops.showPublicReportLink = false;

        when(clientMock.createTestRun()).thenReturn(1L);
        doNothing().when(clientMock).completeTestRun(anyLong());
    }

    private TestResult passedResult(String title) {
        TestResult result = new TestResult();
        result.title = title;
        result.execution = new TestResultExecution();
        result.execution.status = TestResultStatus.PASSED;
        return result;
    }

    @Test
    void runIsNotCompletedWhenABatchFailedToUpload() throws Exception {
        doThrow(new QaseException("upload permanently failed"))
                .when(clientMock).uploadResults(anyLong(), anyList());

        TestopsReporter reporter = new TestopsReporter(config.testops, clientMock);
        reporter.startTestRun();
        reporter.addResult(passedResult("test one"));
        reporter.completeTestRun();

        verify(clientMock, times(1)).uploadResults(anyLong(), anyList());
        verify(clientMock, never()).completeTestRun(anyLong());
    }

    @Test
    void publicReportIsNotEnabledWhenABatchFailedToUpload() throws Exception {
        config.testops.showPublicReportLink = true;
        doThrow(new QaseException("upload permanently failed"))
                .when(clientMock).uploadResults(anyLong(), anyList());

        TestopsReporter reporter = new TestopsReporter(config.testops, clientMock);
        reporter.startTestRun();
        reporter.addResult(passedResult("test one"));
        reporter.completeTestRun();

        verify(clientMock, never()).completeTestRun(anyLong());
        verify(clientMock, never()).enablePublicReport(anyLong());
    }

    @Test
    void runIsCompletedWhenEveryBatchUploadedSuccessfully() throws Exception {
        doNothing().when(clientMock).uploadResults(anyLong(), anyList());

        TestopsReporter reporter = new TestopsReporter(config.testops, clientMock);
        reporter.startTestRun();
        reporter.addResult(passedResult("test one"));
        reporter.addResult(passedResult("test two"));
        reporter.completeTestRun();

        verify(clientMock, times(1)).uploadResults(anyLong(), anyList());
        verify(clientMock, times(1)).completeTestRun(1L);
    }

    @Test
    void runWithNoResultsAtAllIsStillCompleted() throws Exception {
        TestopsReporter reporter = new TestopsReporter(config.testops, clientMock);
        reporter.startTestRun();
        reporter.completeTestRun();

        verify(clientMock, never()).uploadResults(anyLong(), anyList());
        verify(clientMock, times(1)).completeTestRun(1L);
    }

    /**
     * The dropped-batch message means lost data, so it must be logged at ERROR,
     * not WARN.
     */
    @Test
    void droppedBatchIsLoggedAtErrorLevel() throws Exception {
        doThrow(new QaseException("upload permanently failed"))
                .when(clientMock).uploadResults(anyLong(), anyList());

        io.qase.commons.logger.Logger logger = io.qase.commons.logger.Logger.getInstance();

        TestopsReporter reporter = new TestopsReporter(config.testops, clientMock);
        reporter.startTestRun();
        reporter.addResult(passedResult("test one"));

        long errorsBefore = logger.getStatistics().get("ERROR");
        reporter.completeTestRun();
        long errorsAfter = logger.getStatistics().get("ERROR");

        org.junit.jupiter.api.Assertions.assertTrue(errorsAfter - errorsBefore >= 2,
                "Expected at least 2 ERROR logs (dropped batch + skipped completion), got "
                        + (errorsAfter - errorsBefore));
    }
}
