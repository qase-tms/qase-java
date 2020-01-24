package io.qase.api.models.v1.milestones.add;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class CreateUpdateMilestoneResponse {
    private Result result;
    private Boolean status;
    private String errorMessage;
}
