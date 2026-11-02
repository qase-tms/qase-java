package io.qase.commons;

import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.domain.TestResultStatus;
import io.qase.commons.utils.TestResultCompletion;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CasesStorageTest {

    @Test
    void stepsOfIgnoredCaseDoNotLeakIntoNextCase() {
        TestResult ignored = new TestResult();
        ignored.ignore = true;
        CasesStorage.startCase(ignored);
        StepStorage.startStep();
        StepStorage.stopStep();
        CasesStorage.stopCase();

        CasesStorage.startCase(new TestResult());
        TestResult next = TestResultCompletion.complete(TestResultStatus.PASSED, null);
        CasesStorage.stopCase();

        assertTrue(next.steps.isEmpty());
    }
}
