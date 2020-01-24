package io.qase.api;

import io.qase.api.enums.*;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.testcases.get.TestCaseResponse;
import io.qase.api.models.v1.testcases.get_all.TestCasesResponse;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.singletonMap;

public final class TestCases {
    private final QaseApiClient qaseApiClient;

    TestCases(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    /**
     * This method allows to retrieve all test cases stored in selected project.
     *
     * @param projectCode Project code
     * @param limit       A number of test cases in result set
     * @param offset      How many test cases should be skipped
     * @param filter
     * @return
     */
    public TestCasesResponse getAll(String projectCode, int limit, int offset, Filter filter) {
        return qaseApiClient.get(TestCasesResponse.class, "/case/{code}", singletonMap("code", projectCode), filter, limit, offset);
    }

    public TestCasesResponse getAll(String projectCode, Filter filter) {
        return getAll(projectCode, 100, 0, filter);
    }

    public TestCasesResponse getAll(String projectCode) {
        return getAll(projectCode, 100, 0, filter());
    }

    /**
     * This method allows to retrieve a specific test case.
     *
     * @param projectCode Project code
     * @param caseId      Test case id
     * @return
     */
    public TestCaseResponse get(String projectCode, int caseId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", caseId);
        return qaseApiClient.get(TestCaseResponse.class, "/case/{code}/{id}", routeParams);
    }

    /**
     * This method completely deletes a test case from repository
     *
     * @param projectCode Project code
     * @param caseId      Test case id
     */
    public boolean delete(String projectCode, int caseId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", caseId);
        return (boolean) qaseApiClient.delete("/case/{code}/{id}", routeParams).get("status");
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

        /**
         * ID of milestone
         *
         * @param id
         * @return
         */
        public Filter milestoneId(String id) {
            filters.put(Filters.milestone_id, id);
            return this;
        }

        /**
         * ID of test suite
         *
         * @param id
         * @return
         */
        public Filter suiteId(String id) {
            filters.put(Filters.suite_id, id);
            return this;
        }

        /**
         * A list of severity values
         *
         * @param severity
         * @return
         */
        public Filter severity(Severity... severity) {
            filters.put(Filters.severity, Arrays.stream(severity).map(Severity::name)
                    .collect(Collectors.joining(",")));
            return this;
        }

        /**
         * A list of priority values
         *
         * @param priorities
         * @return
         */
        public Filter priority(Priority... priorities) {
            filters.put(Filters.priority,
                    Arrays.stream(priorities).map(Priority::name).collect(Collectors.joining(",")));
            return this;
        }

        /**
         * A list of type values
         *
         * @param types
         * @return
         */
        public Filter type(Type... types) {
            filters.put(Filters.type, Arrays.stream(types).map(Type::name).collect(Collectors.joining(",")));
            return this;
        }

        /**
         * A list of behavior values separated by comma. Possible values: undefined, positive negative, destructive
         *
         * @param behaviors
         * @return
         */
        public Filter behavior(Behavior... behaviors) {
            filters.put(Filters.behavior, Arrays.stream(behaviors).map(Behavior::name)
                    .collect(Collectors.joining(",")));
            return this;
        }

        /**
         * A list of values separated by comma. Possible values: is-not-automated, automated to-be-automated
         *
         * @param automation
         * @return
         */
        public Filter automation(Automation... automation) {
            filters.put(Filters.behavior, Arrays.stream(automation).map(Automation::getName)
                    .collect(Collectors.joining(",")));
            return this;
        }

        /**
         * A list of values separated by comma. Possible values: actual, draft deprecated
         *
         * @param statuses
         * @return
         */
        public Filter status(Status... statuses) {
            filters.put(Filters.behavior, Arrays.stream(statuses).map(Status::name)
                    .collect(Collectors.joining(",")));
            return this;
        }
    }
}
