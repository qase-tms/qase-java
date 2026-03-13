package io.qase.commons.utils;

import io.qase.commons.CasesStorage;
import io.qase.commons.StepStorage;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.domain.TestResultStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class TestResultCompletionTest {

    @BeforeEach
    void setUp() {
        TestResult result = new TestResult();
        result.execution.startTime = Instant.now().toEpochMilli() - 100;
        CasesStorage.startCase(result);
    }

    @AfterEach
    void tearDown() {
        if (CasesStorage.isCaseInProgress()) {
            CasesStorage.stopCase();
        }
    }

    @Test
    void completeShouldFillStatusAndTimingFields() {
        TestResult result = TestResultCompletion.complete(TestResultStatus.PASSED, null);

        assertEquals(TestResultStatus.PASSED, result.execution.status);
        assertNotNull(result.execution.endTime);
        assertTrue(result.execution.endTime > 0);
        assertNotNull(result.execution.duration);
        assertTrue(result.execution.duration >= 0);
        assertNull(result.execution.stacktrace);
        assertNull(result.message);
    }

    @Test
    void completeShouldFillStacktraceAndMessageOnFailure() {
        RuntimeException cause = new RuntimeException("boom");

        TestResult result = TestResultCompletion.complete(TestResultStatus.FAILED, cause);

        assertNotNull(result.execution.stacktrace);
        assertTrue(result.execution.stacktrace.contains("boom"));
        assertNotNull(result.message);
        assertTrue(result.message.contains("boom"));
        assertEquals(cause, result.execution.throwable);
    }

    @Test
    void completeShouldCollectSteps() {
        StepStorage.startStep();
        StepStorage.stopStep();

        TestResult result = TestResultCompletion.complete(TestResultStatus.PASSED, null);

        assertNotNull(result.steps);
        assertFalse(result.steps.isEmpty());
    }

    @Test
    void completeShouldAppendMessageToExistingMessage() {
        TestResult current = CasesStorage.getCurrentCase();
        current.message = "pre-existing";

        RuntimeException cause = new RuntimeException("exception text");
        TestResult result = TestResultCompletion.complete(TestResultStatus.FAILED, cause);

        assertNotNull(result.message);
        assertTrue(result.message.startsWith("pre-existing"));
        assertTrue(result.message.contains("exception text"));
    }

    @Test
    void completeShouldShortCircuitOnIgnoredResult() {
        TestResult current = CasesStorage.getCurrentCase();
        current.ignore = true;

        TestResult result = TestResultCompletion.complete(TestResultStatus.PASSED, null);

        assertNull(result.execution.status);
    }

    @Test
    void completeWithTimingShouldUseSuppliedTimestamps() {
        long startMillis = 1000L;
        long endMillis = 2000L;

        TestResult result = TestResultCompletion.completeWithTiming(TestResultStatus.PASSED, null, startMillis, endMillis);

        assertEquals(endMillis, result.execution.endTime);
        assertEquals(1000, result.execution.duration);
    }

    @Test
    void completeWithTimingShouldShortCircuitOnIgnoredResult() {
        TestResult current = CasesStorage.getCurrentCase();
        current.ignore = true;

        TestResult result = TestResultCompletion.completeWithTiming(TestResultStatus.PASSED, null, 1000L, 2000L);

        assertNull(result.execution.status);
    }

    @Test
    void completeShouldNotCallStopCase() {
        TestResultCompletion.complete(TestResultStatus.PASSED, null);

        assertTrue(CasesStorage.isCaseInProgress());
    }
}
