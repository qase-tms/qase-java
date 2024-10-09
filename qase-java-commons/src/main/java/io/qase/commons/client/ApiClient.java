package io.qase.commons.client;

import io.qase.client.v1.ApiException;
import io.qase.commons.models.TestResult;

import java.util.List;

public interface ApiClient {
    Long createTestRun() throws ApiException;

    void completeTestRun(Long runId) throws ApiException;

    void uploadResults(Long runId, List<TestResult> results) throws ApiException;
}
