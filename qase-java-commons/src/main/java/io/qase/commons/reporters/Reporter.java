package io.qase.commons.reporters;

import io.qase.commons.QaseException;
import io.qase.commons.models.domain.TestResult;

public interface Reporter {
    void startTestRun() throws QaseException;

    void completeTestRun() throws QaseException;

    void addResult(TestResult result) throws QaseException;

    void uploadResults() throws QaseException;
}
