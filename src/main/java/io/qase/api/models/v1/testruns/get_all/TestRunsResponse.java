package io.qase.api.models.v1.testruns.get_all;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class TestRunsResponse {
    private Result result;
    private Boolean status;
}
