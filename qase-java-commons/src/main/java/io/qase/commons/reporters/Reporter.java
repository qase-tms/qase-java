package io.qase.commons.reporters;

import io.qase.commons.QaseException;
import io.qase.commons.models.domain.TestResult;

import java.util.List;

public interface Reporter {
    void startTestRun();

    void completeTestRun();

    void addResult(TestResult result);

    void uploadResults();

    List<Long> getTestCaseIdsForExecution();
}
