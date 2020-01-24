package io.qase.api.models.v1.suites.get;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class SuiteResponse {
    private Result result;
    private Boolean status;
}
