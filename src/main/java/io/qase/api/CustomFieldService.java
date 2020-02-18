package io.qase.api;

import io.qase.api.models.v1.customfields.CustomField;
import io.qase.api.models.v1.customfields.CustomFields;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;

public final class CustomFieldService {
    private final QaseApiClient qaseApiClient;

    public CustomFieldService(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    public CustomFields getAll(String projectCode, int limit, int offset) {
        return qaseApiClient.get(CustomFields.class, "/custom_field/{code}", singletonMap("code", projectCode), null, limit, offset);
    }

    public CustomFields getAll(String projectCode) {
        return this.getAll(projectCode, 100, 0);
    }

    public CustomField get(String projectCode, long id) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", id);
        Map<String, Object> queryParams = new HashMap<>();
        return qaseApiClient.get(CustomField.class, "/custom_field/{code}/{id}", routeParams, queryParams);
    }
}
