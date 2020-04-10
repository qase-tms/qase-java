package io.qase.api.services.v1;

import io.qase.api.QaseApiClient;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.testcases.TestCase;
import io.qase.api.models.v1.testcases.TestCases;
import io.qase.api.services.TestCaseService;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;

public final class TestCaseServiceImpl implements TestCaseService {
    private final QaseApiClient qaseApiClient;

    public TestCaseServiceImpl(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    @Override
    public TestCases getAll(String projectCode, int limit, int offset, RouteFilter filter) {
        return qaseApiClient.get(TestCases.class, "/case/{code}",
                singletonMap("code", projectCode), filter, limit, offset);
    }

    @Override
    public TestCases getAll(String projectCode, RouteFilter filter) {
        return getAll(projectCode, 100, 0, filter);
    }

    @Override
    public TestCases getAll(String projectCode) {
        return getAll(projectCode, 100, 0, filter());
    }

    @Override
    public TestCase get(String projectCode, int caseId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", caseId);
        return qaseApiClient.get(TestCase.class, "/case/{code}/{id}", routeParams);
    }

    @Override
    public boolean delete(String projectCode, int caseId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", caseId);
        return (boolean) qaseApiClient.delete("/case/{code}/{id}", routeParams).get("status");
    }
}
