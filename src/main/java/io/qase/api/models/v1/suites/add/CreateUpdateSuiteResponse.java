package io.qase.api.models.v1.suites.add;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class CreateUpdateSuiteResponse {
    private Result result;
    private Boolean status;
    private String errorMessage;
}
