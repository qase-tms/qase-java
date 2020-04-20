package io.qase.api.services.v1;

import io.qase.api.QaseApiClient;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.defects.Defect;
import io.qase.api.models.v1.defects.Defects;
import io.qase.api.services.DefectService;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;

public final class DefectServiceImpl implements DefectService {
    private final QaseApiClient qaseApiClient;

    public DefectServiceImpl(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    @Override
    public Defects getAll(String projectCode, int limit, int offset, RouteFilter filter) {
        return qaseApiClient.get(Defects.class, "/defect/{code}", singletonMap("code", projectCode), filter, limit, offset);
    }

    @Override
    public Defects getAll(String projectCode) {
        return this.getAll(projectCode, 100, 0, filter());
    }

    @Override
    public Defect get(String projectCode, long id) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", id);
        Map<String, Object> queryParams = new HashMap<>();
        return qaseApiClient.get(Defect.class, "/defect/{code}/{id}", routeParams, queryParams);
    }

    @Override
    public boolean resolve(String projectCode, long defectId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", defectId);
        return (boolean) qaseApiClient.patch("/defect/{code}/resolve/{id}", routeParams).get("status");
    }

    @Override
    public boolean delete(String projectCode, long defectId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", defectId);
        return (boolean) qaseApiClient.delete("/defect/{code}/{id}", routeParams).get("status");
    }
}
