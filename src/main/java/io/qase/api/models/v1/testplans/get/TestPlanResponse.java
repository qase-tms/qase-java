package io.qase.api.models.v1.testplans.get;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class TestPlanResponse {
    private Result result;
    private Boolean status;
}
