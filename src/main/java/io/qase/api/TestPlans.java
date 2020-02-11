package io.qase.api;

import io.qase.api.enums.Filters;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.testplans.NewTestPlan;
import io.qase.api.models.v1.testplans.TestPlan;

import java.util.*;

import static java.util.Collections.singletonMap;

public final class TestPlans {
    private final QaseApiClient qaseApiClient;

    public TestPlans(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    public io.qase.api.models.v1.testplans.TestPlans getAll(String projectCode, int limit, int offset, Filter filter) {
        return qaseApiClient.get(io.qase.api.models.v1.testplans.TestPlans.class, "/plan/{code}", singletonMap("code", projectCode), filter, limit, offset);
    }

    public io.qase.api.models.v1.testplans.TestPlans getAll(String projectCode) {
        return this.getAll(projectCode, 100, 0, new Filter());
    }

    public TestPlan get(String projectCode, long id) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", id);
        return qaseApiClient.get(TestPlan.class, "/plan/{code}/{id}", routeParams);
    }

    public long create(String projectCode, String title, String description, Integer... cases) {
        NewTestPlan createUpdateTestPlanRequest = new NewTestPlan(title, Arrays.asList(cases));
        createUpdateTestPlanRequest.setDescription(description);
        return qaseApiClient.post(TestPlan.class, "/plan/{code}", singletonMap("code", projectCode), createUpdateTestPlanRequest).getId();
    }

    public long create(String projectCode, String title, Integer... cases) {
        return this.create(projectCode, title, null, cases);
    }

    public long update(String projectCode, long testPlanId, String title, String description, Integer... cases) {
        NewTestPlan createUpdateTestPlanRequest = new NewTestPlan(title, Arrays.asList(cases));
        createUpdateTestPlanRequest.setDescription(description);
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", testPlanId);
        return qaseApiClient.patch(TestPlan.class, "/plan/{code}/{id}", routeParams, createUpdateTestPlanRequest).getId();
    }

    public boolean delete(String projectCode, long testPlanId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", testPlanId);
        return (boolean) qaseApiClient.delete("/plan/{code}/{id}", routeParams).get("status");
    }

    public Filter filter() {
        return new Filter();
    }


    public static class Filter implements RouteFilter {
        private final Map<Filters, String> filters = new EnumMap<>(Filters.class);

        private Filter() {
        }

        public Map<Filters, String> getFilters() {
            return Collections.unmodifiableMap(filters);
        }

        /**
         * String that will be used to search by name
         *
         * @param search
         * @return
         */
        public Filter search(String search) {
            filters.put(Filters.search, search);
            return this;
        }
    }
}
