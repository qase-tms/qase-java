package io.qase.api;

import io.qase.api.enums.Filters;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.shared_steps.NewSharedStep;
import io.qase.api.models.v1.shared_steps.SharedStep;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;

public final class SharedSteps {
    private final QaseApiClient qaseApiClient;

    public SharedSteps(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    public io.qase.api.models.v1.shared_steps.SharedSteps getAll(String projectCode, int limit, int offset, Filter filter) {
        return qaseApiClient.get(io.qase.api.models.v1.shared_steps.SharedSteps.class, "/shared_step/{code}", singletonMap("code", projectCode), filter, limit, offset);
    }

    public io.qase.api.models.v1.shared_steps.SharedSteps getAll(String projectCode, Filter filter) {
        return this.getAll(projectCode, 100, 0, filter);
    }

    public io.qase.api.models.v1.shared_steps.SharedSteps getAll(String projectCode) {
        return this.getAll(projectCode, 100, 0, new Filter());
    }

    public SharedStep get(String projectCode, String hash) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("hash", hash);
        return qaseApiClient.get(SharedStep.class, "/shared_step/{code}/{hash}", routeParams);
    }

    public Filter filter() {
        return new Filter();
    }


    public String create(String projectCode, String title, String action, String expectedResult) {
        NewSharedStep createUpdateSharedStepRequest = new NewSharedStep(title, action);
        createUpdateSharedStepRequest.setExpectedResult(expectedResult);
        return qaseApiClient.post(SharedStep.class,
                "/shared_step/{code}",
                singletonMap("code", projectCode),
                createUpdateSharedStepRequest).getHash();
    }

    public String create(String projectCode, String title, String action) {
        return create(projectCode, title, action, null);
    }


    public boolean delete(String projectCode, String hash) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("hash", hash);
        return (boolean) qaseApiClient.delete("/shared_step/{code}/{hash}", routeParams).get("status");
    }

    public String update(String projectCode, String hash, String title, String action, String expectedResult) {
        NewSharedStep createUpdateSharedStepRequest = new NewSharedStep(title, action);
        createUpdateSharedStepRequest.setExpectedResult(expectedResult);
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("hash", hash);
        return qaseApiClient.patch(SharedStep.class, "/shared_step/{code}/{id}", routeParams, createUpdateSharedStepRequest)
                .getHash();
    }

    public String update(String projectCode, String id, String title, String action) {
        return this.update(projectCode, id, title, action, null);
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
