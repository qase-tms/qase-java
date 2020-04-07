package io.qase.api.services;

import io.qase.api.enums.Filters;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.suites.Suite;
import io.qase.api.models.v1.suites.Suites;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public interface SuiteService {

    /**
     * This method allows to retrieve all test suites stored in selected project.
     *
     * @param projectCode Project code
     * @param limit       A number of suites in result set
     * @param offset      How many suites should be skipped
     * @param filter      Provide a string that will be used to search by title
     * @return
     */
    Suites getAll(String projectCode, int limit, int offset, RouteFilter filter);

    /**
     * This method allows to retrieve all test suites stored in selected project.
     *
     * @param projectCode Project code
     * @return
     */
    Suites getAll(String projectCode);

    /**
     * This method allows to retrieve a specific test suite.
     *
     * @param projectCode Project code
     * @param suiteId     Test suite id
     * @return
     */
    Suite get(String projectCode, long suiteId);

    /**
     * create a new test suite through API.
     *
     * @param projectCode  Project code
     * @param title        Test suite title. Required field.
     * @param description  Test suite description
     * @param precondition Test suite preconditions
     * @param parentId     Parent suite ID
     * @return
     */
    long create(String projectCode,
                String title,
                String description,
                String precondition,
                Integer parentId);

    /**
     * create a new test suite through API.
     *
     * @param projectCode Project code
     * @param title       Test suite title. Required field.
     * @return
     */
    long create(String projectCode, String title);

    /**
     * create a new test suite through API.
     *
     * @param projectCode Project code
     * @param title       Test suite title. Required field.
     * @param description Test suite description
     * @return
     */
    long create(String projectCode, String title, String description);

    /**
     * create a new test suite through API.
     *
     * @param projectCode  Project code
     * @param title        Test suite title. Required field.
     * @param description  Test suite description
     * @param precondition Test suite preconditions
     * @return
     */
    long create(String projectCode, String title, String description, String precondition);

    long update(String projectCode, long testSuiteId, String title, String description, String preconditions, Integer parentId);

    long update(String projectCode, long testSuiteId, String title, String description, String preconditions);

    long update(String projectCode, long testSuiteId, String title, String description);

    long update(String projectCode, long testSuiteId, String title);

    /**
     * This method completely deletes a test suite from repository
     *
     * @param projectCode Project code
     * @param testSuiteId Test suite id
     * @return
     */
    boolean delete(String projectCode, long testSuiteId);


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
    }
}
