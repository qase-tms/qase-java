package io.qase.api.models.v1.testrunresults.get;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class TestRunResultResponse {
    private Result result;
    private Boolean status;
}
