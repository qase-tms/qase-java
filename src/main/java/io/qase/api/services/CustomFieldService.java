package io.qase.api.services;

import io.qase.api.models.v1.customfields.CustomField;
import io.qase.api.models.v1.customfields.CustomFields;

public interface CustomFieldService {

    CustomFields getAll(String projectCode, int limit, int offset);

    CustomFields getAll(String projectCode);

    CustomField get(String projectCode, long id);
}
