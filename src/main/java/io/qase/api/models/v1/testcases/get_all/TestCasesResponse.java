package io.qase.api.models.v1.testcases.get_all;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class TestCasesResponse {
    private Result result;
    private Boolean status;
}
