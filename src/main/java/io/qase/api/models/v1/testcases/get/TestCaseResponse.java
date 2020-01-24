package io.qase.api.models.v1.testcases.get;

import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class TestCaseResponse {
    private List<Result> result;
    private Boolean status;
}
