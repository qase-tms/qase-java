package io.qase.commons.reporters;

import io.qase.commons.QaseException;
import io.qase.commons.client.ApiClient;
import io.qase.commons.config.ConfigFactory;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.logger.Logger;
import io.qase.commons.models.domain.Attachment;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.domain.TestResultExecution;
import io.qase.commons.models.domain.TestResultStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Tests for LOGS-04: Run-completion aggregate upload statistics at INFO level.
 *
 * Verifies that TestopsReporter emits a summary INFO log after all uploads
 * complete in completeTestRun(), and that AtomicLong counters accumulate correctly.
 *
 * All tests FAIL until TestopsReporter is modified to add AtomicLong counters
 * and the summary log in completeTestRun().
 */
class UploadSummaryLogTest {

    private TestopsReporter reporter;
    private ApiClient clientMock;
    private QaseConfig config;

    @BeforeEach
    void setUp() throws QaseException {
        clientMock = mock(ApiClient.class);

        config = ConfigFactory.loadConfig();
        config.testops.run.id = 0;
        // Large batch size so addResult() does not auto-trigger a batch upload
        config.testops.batch.size = 200;
        // run.complete = false so "Test run X completed" INFO is NOT emitted
        // (it emits "skipping completion" instead, which is still INFO — but
        // we capture the exact baseline right before completeTestRun()
        // and require strictly more than the known non-summary INFOs)
        config.testops.run.complete = true;

        when(clientMock.createTestRun()).thenReturn(1L);
        doNothing().when(clientMock).completeTestRun(anyLong());
        doNothing().when(clientMock).uploadResults(anyLong(), anyList());

        reporter = new TestopsReporter(config.testops, clientMock);
    }

    /**
     * Creates a minimal valid TestResult with a contentBytes attachment.
     */
    private TestResult createTestResult(String title, int attachmentBytes) {
        TestResult result = new TestResult();
        result.title = title;
        result.execution = new TestResultExecution();
        result.execution.status = TestResultStatus.PASSED;

        if (attachmentBytes > 0) {
            Attachment attachment = new Attachment();
            attachment.fileName = title + "-attachment.bin";
            attachment.mimeType = "application/octet-stream";
            attachment.contentBytes = new byte[attachmentBytes];
            result.attachments.add(attachment);
        }

        return result;
    }

    // -------------------------------------------------------------------------
    // LOGS-04-1: Summary log emitted at run completion (with 0 results)
    // -------------------------------------------------------------------------

    /**
     * LOGS-04: When completeTestRun() is called with no buffered results,
     * the summary INFO log must still be emitted (showing all-zeros counters).
     *
     * Known INFO logs from current code during completeTestRun() with 0 results:
     *   1. "Test run 1 completed" (from TestopsReporter.completeTestRun() line 77)
     *
     * With LOGS-04 implemented, we expect:
     *   1. "Upload summary: 0 results, 0 attachments, 0 bytes, 0 ms total, 0 failed batches"
     *   2. "Test run 1 completed"
     *
     * This FAILS because the summary log does not exist yet — only 1 INFO is emitted,
     * not 2. We assert infoIncrement >= 2.
     */
    @Test
    void summaryLogEmittedAtRunCompletion() throws Exception {
        // Start run (emits "Test run 1 started" INFO)
        reporter.startTestRun();

        // Capture baseline right before completeTestRun()
        // (no results in buffer, no batch uploads will happen)
        Logger logger = Logger.getInstance();
        long infoBefore = logger.getStatistics().get("INFO");

        // Complete the run — NO results to upload
        // Current code emits exactly 1 INFO: "Test run 1 completed"
        // After LOGS-04: emits 2 INFOs: "Upload summary: ..." + "Test run 1 completed"
        reporter.completeTestRun();

        long infoAfter = logger.getStatistics().get("INFO");
        long infoIncrement = infoAfter - infoBefore;

        assertTrue(infoIncrement >= 2,
                "completeTestRun() must emit at least 2 INFO messages: " +
                "the Upload summary log (LOGS-04) + the 'Test run completed' log. " +
                "Got infoIncrement=" + infoIncrement +
                " (infoBefore=" + infoBefore + ", infoAfter=" + infoAfter + "). " +
                "This FAILS because the summary log does not exist yet in completeTestRun().");
    }

    // -------------------------------------------------------------------------
    // LOGS-04-2: Failed batches are counted; summary still emitted
    // -------------------------------------------------------------------------

    /**
     * LOGS-04: When uploadResults throws QaseException, statFailedBatches increments
     * and the summary log is still emitted in completeTestRun().
     *
     * Known INFO logs from current code during completeTestRun() with 1 failed batch:
     *   1. "Uploading batch: 1 results, 1 attachments, 0.0 MB"
     *   2. "Test run 1 completed"
     * (Note: the WARN for the failed batch does not count as INFO)
     *
     * With LOGS-04 implemented:
     *   1. "Uploading batch: ..."
     *   2. "Upload summary: ..."  <- NEW
     *   3. "Test run 1 completed"
     *
     * Capture infoBefore right before completeTestRun(). We expect infoIncrement >= 3.
     * This FAILS because infoIncrement == 2 (no summary log in current code).
     */
    @Test
    void summaryCountsFailedBatches() throws Exception {
        // Mock uploadResults to throw on every call
        doThrow(new QaseException("Upload failed for test"))
                .when(clientMock).uploadResults(anyLong(), anyList());

        reporter.startTestRun();
        // Add 1 result — stays in buffer since batch.size=200
        reporter.addResult(createTestResult("failing-batch-test", 512));

        // Capture INFO count right before completeTestRun()
        Logger logger = Logger.getInstance();
        long infoBefore = logger.getStatistics().get("INFO");

        // completeTestRun() flushes buffer, batch fails, summary should still fire
        // Current code INFOs during this: "Uploading batch: ..." + "Test run 1 completed" = 2
        // LOGS-04 target: "Uploading batch: ..." + "Upload summary: ..." + "Test run 1 completed" = 3
        reporter.completeTestRun();

        long infoAfter = logger.getStatistics().get("INFO");
        long infoIncrement = infoAfter - infoBefore;

        assertTrue(infoIncrement >= 3,
                "completeTestRun() with a failed batch must emit at least 3 INFO messages: " +
                "'Uploading batch', 'Upload summary' (LOGS-04), and 'Test run completed'. " +
                "Got infoIncrement=" + infoIncrement +
                " (infoBefore=" + infoBefore + ", infoAfter=" + infoAfter + "). " +
                "This FAILS because the summary log does not exist yet.");
    }

    // -------------------------------------------------------------------------
    // LOGS-04-3: Total results and attachments counted across multiple batches
    // -------------------------------------------------------------------------

    /**
     * LOGS-04: With batch.size=1, 2 addResult() calls trigger 2 immediate uploads.
     * After completeTestRun(), the summary INFO log must be emitted.
     *
     * Known INFO logs from current code (batch.size=1, 2 results):
     *   After each addResult(): "Uploading batch: ..." (async, may fire before or during completeTestRun)
     *   In completeTestRun(): "Test run 1 completed"
     *
     * Total INFO during completeTestRun() (after awaitTermination drains in-flight batches):
     *   The 2 "Uploading batch" logs may already be counted in infoBefore (if async completed).
     *   The summary log is strictly emitted AFTER awaitTermination() — always AFTER infoBefore.
     *
     * We capture infoBefore RIGHT BEFORE completeTestRun(), wait for async batches first.
     * Then infoIncrement should include the summary log + "Test run completed" = at least 2.
     * Current code: only "Test run completed" fires after infoBefore = infoIncrement of 1.
     * This FAILS because infoIncrement == 1, not >= 2.
     */
    @Test
    void summaryCountsTotalResultsAndAttachments() throws Exception {
        // Set batch.size=1 so each addResult triggers immediate async upload
        config.testops.batch.size = 1;
        reporter = new TestopsReporter(config.testops, clientMock);

        reporter.startTestRun();

        // Add 2 results — each triggers immediate async batch upload
        reporter.addResult(createTestResult("result-one", 2048));
        reporter.addResult(createTestResult("result-two", 1024));

        // Wait for the async "Uploading batch" logs to complete before capturing baseline
        Thread.sleep(300);

        // Capture INFO count RIGHT before completeTestRun()
        // The async "Uploading batch" INFOs should already be counted by now
        Logger logger = Logger.getInstance();
        long infoBefore = logger.getStatistics().get("INFO");

        // Invoke completeTestRun() — no buffered results remain (batch.size=1 already uploaded)
        // Current code: emits "Test run 1 completed" only = infoIncrement of 1
        // LOGS-04 target: emits "Upload summary: ..." + "Test run 1 completed" = infoIncrement of 2
        reporter.completeTestRun();

        long infoAfter = logger.getStatistics().get("INFO");
        long infoIncrement = infoAfter - infoBefore;

        assertTrue(infoIncrement >= 2,
                "completeTestRun() must emit at least 2 INFO messages: " +
                "'Upload summary' (LOGS-04) + 'Test run completed'. " +
                "Got infoIncrement=" + infoIncrement +
                " (infoBefore=" + infoBefore + ", infoAfter=" + infoAfter + "). " +
                "This FAILS because the summary log does not exist yet.");

        // Verify uploadResults was called for both batches
        verify(clientMock, timeout(5000).atLeast(2)).uploadResults(anyLong(), anyList());
    }
}
