package io.qase.api;

import io.qase.api.enums.DefectStatus;
import io.qase.api.enums.Filters;
import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.defects.Defect;
import io.qase.api.models.v1.defects.Defects;

import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;

public final class DefectService {
    private final QaseApiClient qaseApiClient;

    public DefectService(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    public Defects getAll(String projectCode, int limit, int offset, Filter filter) {
        return qaseApiClient.get(Defects.class, "/defect/{code}", singletonMap("code", projectCode), filter, limit, offset);
    }

    public Defects getAll(String projectCode) {
        return this.getAll(projectCode, 100, 0, new Filter());
    }

    public Defect get(String projectCode, long id) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", id);
        Map<String, Object> queryParams = new HashMap<>();
        return qaseApiClient.get(Defect.class, "/defect/{code}/{id}", routeParams, queryParams);
    }

    public boolean resolve(String projectCode, long defectId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", defectId);
        return (boolean) qaseApiClient.patch("/defect/{code}/resolve/{id}", routeParams).get("status");
    }

    public boolean delete(String projectCode, long defectId) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", defectId);
        return (boolean) qaseApiClient.delete("/defect/{code}/{id}", routeParams).get("status");
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
         * @param status A list of status values. Possible values: active, complete, abort
         * @return
         */
        public Filter status(DefectStatus status) {
            filters.put(Filters.status, status.name());
            return this;
        }
    }
}
