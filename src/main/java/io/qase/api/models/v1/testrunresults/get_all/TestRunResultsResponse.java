package io.qase.api.models.v1.testrunresults.get_all;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class TestRunResultsResponse {
    private Result result;
    private Boolean status;
}
