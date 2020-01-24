package io.qase.api;

import io.qase.api.models.v1.customfields.get.CustomFieldResponse;
import io.qase.api.models.v1.customfields.get_all.CustomFieldsResponse;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;

public final class CustomFields {
    private final QaseApiClient qaseApiClient;

    public CustomFields(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    public CustomFieldsResponse getAll(String projectCode, int limit, int offset) {
        return qaseApiClient.get(CustomFieldsResponse.class, "/custom_field/{code}", singletonMap("code", projectCode), null, limit, offset);
    }

    public CustomFieldsResponse getAll(String projectCode) {
        return this.getAll(projectCode, 100, 0);
    }

    public CustomFieldResponse get(String projectCode, long id) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("id", id);
        Map<String, Object> queryParams = new HashMap<>();
        return qaseApiClient.get(CustomFieldResponse.class, "/custom_field/{code}/{id}", routeParams, queryParams);
    }
}
