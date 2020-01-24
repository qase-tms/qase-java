package io.qase.api.models.v1.testruns.get;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class TestRunResponse {
    private Result result;
    private Boolean status;
}
