package io.qase.api;

import io.qase.api.enums.Filters;
import io.qase.api.enums.RunStatus;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.testruns.NewTestRun;
import io.qase.api.models.v1.testruns.TestRun;
import io.qase.api.models.v1.testruns.TestRuns;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.singletonMap;

public final class TestRunService {
    private final QaseApiClient qaseApiClient;

    public TestRunService(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    public TestRuns getAll(String projectCode, int limit, int offset, Filter filter, boolean includeCases) {
        Map<String, Object> queryParams = new HashMap<>();
        queryParams.put("limit", limit);
        queryParams.put("offset", offset);
        if (includeCases) {
            queryParams.put("include", "cases");
        }
        return qaseApiClient.get(TestRuns.class, "/run/{code}", singletonMap("code", projectCode), queryParams, filter);
    }

    public TestRuns getAll(String projectCode, boolean includeCases) {
        return this.getAll(projectCode, 100, 0, new Filter(), includeCases);
    }

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

    public long create(String projectCode, String title, Integer environmentId, String description, Integer... cases) {
        NewTestRun createUpdateTestRunRequest = new NewTestRun(title, Arrays.asList(cases));
        createUpdateTestRunRequest.setDescription(description);
        createUpdateTestRunRequest.setEnvironmentId(environmentId);
        return qaseApiClient.post(TestRun.class, "/run/{code}", singletonMap("code", projectCode), createUpdateTestRunRequest)
                .getId();
    }

    public long create(String projectCode, String title, Integer... cases) {
        return this.create(projectCode, title, null, null, cases);
    }

    public boolean delete(String projectCode, long testRunId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", testRunId);
        return (boolean) qaseApiClient.delete("/run/{code}/{id}", routeParams).get("status");
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
         * @param statuses A list of status values. Possible values: active, complete, abort
         * @return
         */
        public Filter status(RunStatus... statuses) {
            filters.put(Filters.status, Arrays.stream(statuses).map(RunStatus::name)
                    .collect(Collectors.joining(",")));
            return this;
        }
    }
}
