package io.qase.api;

import io.qase.api.enums.Filters;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.shared_steps.add.CreateUpdateSharedStepRequest;
import io.qase.api.models.v1.shared_steps.add.CreateUpdateSharedStepResponse;
import io.qase.api.models.v1.shared_steps.get.SharedStepResponse;
import io.qase.api.models.v1.shared_steps.get_all.SharedStepsResponse;

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

    public SharedStepsResponse getAll(String projectCode, int limit, int offset, Filter filter) {
        return qaseApiClient.get(SharedStepsResponse.class, "/shared_step/{code}", singletonMap("code", projectCode), filter, limit, offset);
    }

    public SharedStepsResponse getAll(String projectCode, Filter filter) {
        return this.getAll(projectCode, 100, 0, filter);
    }

    public SharedStepsResponse getAll(String projectCode) {
        return this.getAll(projectCode, 100, 0, new Filter());
    }

    public SharedStepResponse get(String projectCode, String hash) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("hash", hash);
        return qaseApiClient.get(SharedStepResponse.class, "/shared_step/{code}/{hash}", routeParams);
    }

    public Filter filter() {
        return new Filter();
    }


    public CreateUpdateSharedStepResponse create(String projectCode, String title, String action, String expectedResult) {
        CreateUpdateSharedStepRequest createUpdateSharedStepRequest = new CreateUpdateSharedStepRequest(title, action);
        createUpdateSharedStepRequest.setExpectedResult(expectedResult);
        return qaseApiClient.post(CreateUpdateSharedStepResponse.class,
                "/shared_step/{code}",
                singletonMap("code", projectCode),
                createUpdateSharedStepRequest);
    }

    public CreateUpdateSharedStepResponse create(String projectCode, String title, String action) {
        return create(projectCode, title, action, null);
    }


    public boolean delete(String projectCode, String hash) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("hash", hash);
        return (boolean) qaseApiClient.delete("/shared_step/{code}/{hash}", routeParams).get("status");
    }

    public CreateUpdateSharedStepResponse update(String projectCode, String hash, String title, String action, String expectedResult) {
        CreateUpdateSharedStepRequest createUpdateSharedStepRequest = new CreateUpdateSharedStepRequest(title, action);
        createUpdateSharedStepRequest.setExpectedResult(expectedResult);
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("hash", hash);
        return qaseApiClient.patch(CreateUpdateSharedStepResponse.class, "/shared_step/{code}/{id}", routeParams, createUpdateSharedStepRequest);
    }

    public CreateUpdateSharedStepResponse update(String projectCode, String id, String title, String action) {
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
