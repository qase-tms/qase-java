package io.qase.api;

import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.testruns.NewTestRun;
import io.qase.api.models.v1.testruns.TestRun;
import io.qase.api.models.v1.testruns.TestRuns;
import io.qase.api.services.TestRunService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;

public final class TestRunServiceImpl implements TestRunService {
    private final QaseApiClient qaseApiClient;

    public TestRunServiceImpl(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    @Override
    public TestRuns getAll(String projectCode, int limit, int offset, RouteFilter filter, boolean includeCases) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("limit", limit);
        queryParams.put("offset", offset);
        if (includeCases) {
            queryParams.put("include", "cases");
        }
        return qaseApiClient.get(TestRuns.class, "/run/{code}", singletonMap("code", projectCode), queryParams, filter);
    }

    @Override
    public TestRuns getAll(String projectCode, boolean includeCases) {
        return this.getAll(projectCode, 100, 0, filter(), includeCases);
    }

    @Override
    public TestRun get(String projectCode, long id, boolean includeCases) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", id);
        Map<String, Object> queryParams = new HashMap<>();
        if (includeCases) {
            queryParams.put("include", "cases");
        }
        return qaseApiClient.get(TestRun.class, "/run/{code}/{id}", routeParams, queryParams);
    }

    @Override
    public long create(String projectCode, String title, Integer environmentId, String description, Integer... cases) {
        NewTestRun createUpdateTestRunRequest = new NewTestRun(title, Arrays.asList(cases));
        createUpdateTestRunRequest.setDescription(description);
        createUpdateTestRunRequest.setEnvironmentId(environmentId);
        return qaseApiClient.post(TestRun.class, "/run/{code}", singletonMap("code", projectCode), createUpdateTestRunRequest)
                .getId();
    }

    @Override
    public long create(String projectCode, String title, Integer... cases) {
        return this.create(projectCode, title, null, null, cases);
    }

    @Override
    public boolean delete(String projectCode, long testRunId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", testRunId);
        return (boolean) qaseApiClient.delete("/run/{code}/{id}", routeParams).get("status");
    }
}
