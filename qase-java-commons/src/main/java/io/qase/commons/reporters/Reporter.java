package io.qase.commons.reporters;

import io.qase.client.v1.ApiException;
import io.qase.commons.models.TestResult;

public interface Reporter {
    void startTestRun() throws ApiException;

    void completeTestRun() throws ApiException;

    void addResult(TestResult result) throws ApiException;

    void uploadResults() throws ApiException;
}
