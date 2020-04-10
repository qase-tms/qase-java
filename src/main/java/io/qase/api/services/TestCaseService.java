package io.qase.api.services;

import io.qase.api.enums.*;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.testcases.TestCase;
import io.qase.api.models.v1.testcases.TestCases;

import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.stream.Collectors;

public interface TestCaseService {

    /**
     * This method allows to retrieve all test cases stored in selected project.
     *
     * @param projectCode Project code
     * @param limit       A number of test cases in result set
     * @param offset      How many test cases should be skipped
     * @param filter
     * @return
     */
    TestCases getAll(String projectCode, int limit, int offset, RouteFilter filter);

    TestCases getAll(String projectCode, RouteFilter filter);

    TestCases getAll(String projectCode);

    /**
     * This method allows to retrieve a specific test case.
     *
     * @param projectCode Project code
     * @param caseId      Test case id
     * @return
     */
    TestCase get(String projectCode, int caseId);

    /**
     * This method completely deletes a test case from repository
     *
     * @param projectCode Project code
     * @param caseId      Test case id
     */
    boolean delete(String projectCode, int caseId);

    default Filter filter() {
        return new Filter();
    }

    class Filter implements RouteFilter {
        private final Map<Filters, String> filters = new EnumMap<>(Filters.class);

        private Filter() {
        }

        @Override
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
        public Filter milestoneId(int id) {
            filters.put(Filters.milestone_id, String.valueOf(id));
            return this;
        }

        /**
         * ID of test suite
         *
         * @param id
         * @return
         */
        public Filter suiteId(int id) {
            filters.put(Filters.suite_id, String.valueOf(id));
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
            filters.put(Filters.automation, Arrays.stream(automation).map(Automation::getName)
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
            filters.put(Filters.status, Arrays.stream(statuses).map(Status::name)
                    .collect(Collectors.joining(",")));
            return this;
        }
    }
}
