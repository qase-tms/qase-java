package io.qase.api.models.v1.shared_steps.get;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class SharedStepResponse {
    private Result result;
    private Boolean status;
}
