package io.qase.api.models.v1.milestones.get;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class MilestoneResponse {
    private Result result;
    private Boolean status;
    private String errorMessage;
}
