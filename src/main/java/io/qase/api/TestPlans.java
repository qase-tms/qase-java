package io.qase.api;

import io.qase.api.enums.Filters;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.testplans.add.CreateUpdateTestPlanRequest;
import io.qase.api.models.v1.testplans.add.CreateUpdateTestPlanResponse;
import io.qase.api.models.v1.testplans.get.TestPlanResponse;
import io.qase.api.models.v1.testplans.get_all.TestPlansResponse;

import java.util.*;

import static java.util.Collections.singletonMap;

public final class TestPlans {
    private final QaseApiClient qaseApiClient;

    public TestPlans(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    public TestPlansResponse getAll(String projectCode, int limit, int offset, Filter filter) {
        return qaseApiClient.get(TestPlansResponse.class, "/plan/{code}", singletonMap("code", projectCode), filter, limit, offset);
    }

    public TestPlansResponse getAll(String projectCode) {
        return this.getAll(projectCode, 100, 0, new Filter());
    }

    public TestPlanResponse get(String projectCode, String id) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", id);
        return qaseApiClient.get(TestPlanResponse.class, "/plan/{code}/{id}", routeParams);
    }

    public CreateUpdateTestPlanResponse create(String projectCode, String title, String description, Integer... cases) {
        CreateUpdateTestPlanRequest createUpdateTestPlanRequest = new CreateUpdateTestPlanRequest(title, Arrays.asList(cases));
        createUpdateTestPlanRequest.setDescription(description);
        return qaseApiClient.post(CreateUpdateTestPlanResponse.class, "/plan/{code}", singletonMap("code", projectCode), createUpdateTestPlanRequest);
    }

    public CreateUpdateTestPlanResponse create(String projectCode, String title, Integer... cases) {
        return this.create(projectCode, title, null, cases);
    }

    public CreateUpdateTestPlanResponse update(String projectCode, String testPlanId, String title, String description, Integer... cases) {
        CreateUpdateTestPlanRequest createUpdateTestPlanRequest = new CreateUpdateTestPlanRequest(title, Arrays.asList(cases));
        createUpdateTestPlanRequest.setDescription(description);
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", testPlanId);
        return qaseApiClient.patch(CreateUpdateTestPlanResponse.class, "/plan/{code}/{id}", routeParams, createUpdateTestPlanRequest);
    }

    public boolean delete(String projectCode, String testPlanId) {
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
