package io.qase.api.services.v1;

import io.qase.api.QaseApiClient;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.testplans.NewTestPlan;
import io.qase.api.models.v1.testplans.TestPlan;
import io.qase.api.models.v1.testplans.TestPlans;
import io.qase.api.services.TestPlanService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;

public final class TestPlanServiceImpl implements TestPlanService {
    private final QaseApiClient qaseApiClient;

    public TestPlanServiceImpl(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    @Override
    public TestPlans getAll(String projectCode, int limit, int offset, RouteFilter filter) {
        return qaseApiClient.get(TestPlans.class, "/plan/{code}", singletonMap("code", projectCode), filter, limit, offset);
    }

    @Override
    public TestPlans getAll(String projectCode) {
        return this.getAll(projectCode, 100, 0, filter());
    }

    @Override
    public TestPlan get(String projectCode, long id) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", id);
        return qaseApiClient.get(TestPlan.class, "/plan/{code}/{id}", routeParams);
    }

    @Override
    public long create(String projectCode, String title, String description, Integer... cases) {
        NewTestPlan createUpdateTestPlanRequest = new NewTestPlan(title, Arrays.asList(cases));
        createUpdateTestPlanRequest.setDescription(description);
        return qaseApiClient.post(TestPlan.class, "/plan/{code}", singletonMap("code", projectCode), createUpdateTestPlanRequest).getId();
    }

    @Override
    public long create(String projectCode, String title, Integer... cases) {
        return this.create(projectCode, title, null, cases);
    }

    @Override
    public long update(String projectCode, long testPlanId, String title, String description, Integer... cases) {
        NewTestPlan createUpdateTestPlanRequest = new NewTestPlan(title, Arrays.asList(cases));
        createUpdateTestPlanRequest.setDescription(description);
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", testPlanId);
        return qaseApiClient.patch(TestPlan.class, "/plan/{code}/{id}", routeParams, createUpdateTestPlanRequest).getId();
    }

    @Override
    public boolean delete(String projectCode, long testPlanId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", testPlanId);
        return (boolean) qaseApiClient.delete("/plan/{code}/{id}", routeParams).get("status");
    }
}
