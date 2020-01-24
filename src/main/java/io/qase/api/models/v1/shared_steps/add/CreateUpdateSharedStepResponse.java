package io.qase.api.models.v1.shared_steps.add;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class CreateUpdateSharedStepResponse {
    private Result result;
    private Boolean status;
    private String errorMessage;
}
