package io.qase.api;

import io.qase.api.enums.Filters;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.suites.NewSuite;
import io.qase.api.models.v1.suites.Suite;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;

public final class Suites {
    private final QaseApiClient qaseApiClient;

    public Suites(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    /**
     * This method allows to retrieve all test suites stored in selected project.
     *
     * @param projectCode Project code
     * @param limit       A number of suites in result set
     * @param offset      How many suites should be skipped
     * @param filter      Provide a string that will be used to search by title
     * @return
     */
    public io.qase.api.models.v1.suites.Suites getAll(String projectCode, int limit, int offset, Filter filter) {
        return qaseApiClient.get(io.qase.api.models.v1.suites.Suites.class, "/suite/{code}", singletonMap("code", projectCode), filter, limit, offset);
    }

    /**
     * This method allows to retrieve all test suites stored in selected project.
     *
     * @param projectCode Project code
     * @return
     */
    public io.qase.api.models.v1.suites.Suites getAll(String projectCode) {
        return this.getAll(projectCode, 100, 0, new Filter());
    }

    /**
     * This method allows to retrieve a specific test suite.
     *
     * @param projectCode Project code
     * @param suiteId     Test suite id
     * @return
     */
    public Suite get(String projectCode, long suiteId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", suiteId);
        return qaseApiClient.get(Suite.class, "/suite/{code}/{id}", routeParams);
    }

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
    public long create(String projectCode,
                       String title,
                       String description,
                       String precondition,
                       Integer parentId) {
        NewSuite createUpdateSuiteRequest = new NewSuite(title);
        createUpdateSuiteRequest.setDescription(description);
        createUpdateSuiteRequest.setPreconditions(precondition);
        createUpdateSuiteRequest.setParentId(parentId);
        return qaseApiClient.post(Suite.class, "/suite/{code}", singletonMap("code", projectCode), createUpdateSuiteRequest)
                .getId();
    }

    /**
     * create a new test suite through API.
     *
     * @param projectCode Project code
     * @param title       Test suite title. Required field.
     * @return
     */
    public long create(String projectCode, String title) {
        return this.create(projectCode, title, null, null, null);
    }

    /**
     * create a new test suite through API.
     *
     * @param projectCode Project code
     * @param title       Test suite title. Required field.
     * @param description Test suite description
     * @return
     */
    public long create(String projectCode, String title, String description) {
        return this.create(projectCode, title, description, null, null);
    }

    /**
     * create a new test suite through API.
     *
     * @param projectCode  Project code
     * @param title        Test suite title. Required field.
     * @param description  Test suite description
     * @param precondition Test suite preconditions
     * @return
     */
    public long create(String projectCode, String title, String description, String precondition) {
        return this.create(projectCode, title, description, precondition, null);
    }

    public long update(String projectCode, long testSuiteId, String title) {
        NewSuite createUpdateSuiteRequest = new NewSuite(title);
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", testSuiteId);
        return qaseApiClient.patch(Suite.class, "/suite/{code}/{id}", routeParams, createUpdateSuiteRequest).getId();
    }

    /**
     * This method completely deletes a test suite from repository
     *
     * @param projectCode Project code
     * @param testSuiteId Test suite id
     * @return
     */
    public boolean delete(String projectCode, String testSuiteId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", testSuiteId);
        return (boolean) qaseApiClient.delete("/suite/{code}/{id}", routeParams).get("status");
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
