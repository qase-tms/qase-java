package io.qase.api.services.v1;

import io.qase.api.QaseApiClient;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.suites.NewSuite;
import io.qase.api.models.v1.suites.Suite;
import io.qase.api.models.v1.suites.Suites;
import io.qase.api.services.SuiteService;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;

public final class SuiteServiceImpl implements SuiteService {
    private final QaseApiClient qaseApiClient;

    public SuiteServiceImpl(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    @Override
    public Suites getAll(String projectCode, int limit, int offset, RouteFilter filter) {
        return qaseApiClient.get(Suites.class, "/suite/{code}", singletonMap("code", projectCode), filter, limit, offset);
    }

    @Override
    public Suites getAll(String projectCode) {
        return this.getAll(projectCode, 100, 0, filter());
    }

    @Override
    public Suite get(String projectCode, long suiteId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", suiteId);
        return qaseApiClient.get(Suite.class, "/suite/{code}/{id}", routeParams);
    }

    @Override
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

    @Override
    public long create(String projectCode, String title) {
        return this.create(projectCode, title, null, null, null);
    }

    @Override
    public long create(String projectCode, String title, String description) {
        return this.create(projectCode, title, description, null, null);
    }

    @Override
    public long create(String projectCode, String title, String description, String precondition) {
        return this.create(projectCode, title, description, precondition, null);
    }

    @Override
    public long update(String projectCode, long testSuiteId, String title, String description, String preconditions, Integer parentId) {
        NewSuite createUpdateSuiteRequest = new NewSuite(title);
        createUpdateSuiteRequest.setDescription(description);
        createUpdateSuiteRequest.setPreconditions(preconditions);
        createUpdateSuiteRequest.setParentId(parentId);
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", testSuiteId);
        return qaseApiClient.patch(Suite.class, "/suite/{code}/{id}", routeParams, createUpdateSuiteRequest).getId();
    }

    @Override
    public long update(String projectCode, long testSuiteId, String title, String description, String preconditions) {
        return update(projectCode, testSuiteId, title, description, preconditions, null);
    }

    @Override
    public long update(String projectCode, long testSuiteId, String title, String description) {
        return update(projectCode, testSuiteId, title, description, null, null);
    }

    @Override
    public long update(String projectCode, long testSuiteId, String title) {
        return update(projectCode, testSuiteId, title, null, null, null);
    }

    @Override
    public boolean delete(String projectCode, long testSuiteId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", testSuiteId);
        return (boolean) qaseApiClient.delete("/suite/{code}/{id}", routeParams).get("status");
    }
}
