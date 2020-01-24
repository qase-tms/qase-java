package io.qase.api.models.v1.testplans.add;

import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class CreateUpdateTestPlanResponse {
    private Result result;
    private Boolean status;
    private String errorMessage;
    private List<ErrorFields> errorFields;
}
