package io.qase.commons.client;

import io.qase.commons.QaseException;
import io.qase.commons.models.domain.TestResult;

import java.util.List;

public interface ApiClient {
    Long createTestRun() throws QaseException;

    void completeTestRun(Long runId) throws QaseException;

    void uploadResults(Long runId, List<TestResult> results) throws QaseException;

    List<Long> getTestCaseIdsForExecution() throws QaseException;
}
