package io.qase.commons.reporters;

import io.qase.commons.QaseException;
import io.qase.commons.models.domain.TestResult;

import java.util.List;

public interface InternalReporter {
    void startTestRun() throws QaseException;

    void completeTestRun() throws QaseException;

    void addResult(TestResult result) throws QaseException;

    void uploadResults() throws QaseException;

    List<TestResult> getResults();

    void setResults(List<TestResult> results);
}
