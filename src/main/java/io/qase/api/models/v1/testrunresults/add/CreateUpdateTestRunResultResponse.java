package io.qase.api.models.v1.testrunresults.add;

import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class CreateUpdateTestRunResultResponse {
    private Result result;
    private Boolean status;
    private String errorMessage;
    private List<ErrorFields> errorFields;
}
