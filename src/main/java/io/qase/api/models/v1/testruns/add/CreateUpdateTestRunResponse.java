package io.qase.api.models.v1.testruns.add;

import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class CreateUpdateTestRunResponse {
    private Result result;
    private Boolean status;
    private String errorMessage;
    private List<ErrorFields> errorFields;
}
