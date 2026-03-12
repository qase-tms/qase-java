package io.qase.commons.utils;

import io.qase.commons.CasesStorage;
import io.qase.commons.StepStorage;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.domain.TestResultStatus;

import java.time.Instant;

public final class TestResultCompletion {
    private TestResultCompletion() throws IllegalAccessException {
        throw new IllegalAccessException("Utils class");
    }

    /**
     * Fills the current test result's execution fields using wall-clock timing.
     * Does NOT call CasesStorage.stopCase().
     *
     * @param status the final test status
     * @param cause  the throwable if the test failed, or null
     * @return the current test result (from CasesStorage), modified in place
     */
    public static TestResult complete(TestResultStatus status, Throwable cause) {
        TestResult result = CasesStorage.getCurrentCase();
        if (result.ignore) {
            return result;
        }
        long endMillis = Instant.now().toEpochMilli();
        long startMillis = result.execution.startTime != null ? result.execution.startTime : endMillis;
        return fillResult(result, status, cause, startMillis, endMillis);
    }

    /**
     * Fills the current test result's execution fields using supplied timestamps.
     * Used by reporters (e.g. TestNG) that track timing externally.
     * Does NOT call CasesStorage.stopCase().
     *
     * @param status      the final test status
     * @param cause       the throwable if the test failed, or null
     * @param startMillis the test start time in epoch milliseconds
     * @param endMillis   the test end time in epoch milliseconds
     * @return the current test result (from CasesStorage), modified in place
     */
    public static TestResult completeWithTiming(TestResultStatus status, Throwable cause,
                                                long startMillis, long endMillis) {
        TestResult result = CasesStorage.getCurrentCase();
        if (result.ignore) {
            return result;
        }
        return fillResult(result, status, cause, startMillis, endMillis);
    }

    private static TestResult fillResult(TestResult result, TestResultStatus status, Throwable cause,
                                         long startMillis, long endMillis) {
        result.execution.status = status;
        result.execution.endTime = endMillis;
        result.execution.duration = (int) (endMillis - startMillis);
        result.steps = StepStorage.stopSteps();

        if (cause != null) {
            result.execution.throwable = cause;
            result.execution.stacktrace = IntegrationUtils.getStacktrace(cause);
            String causeMessage = cause.toString();
            if (result.message == null) {
                result.message = causeMessage;
            } else {
                result.message = result.message + "\n\n" + causeMessage;
            }
        }

        return result;
    }
}
