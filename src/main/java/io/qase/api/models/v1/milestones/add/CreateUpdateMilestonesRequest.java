package io.qase.api.models.v1.milestones.add;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class CreateUpdateMilestonesRequest {
    private final String title;
    private String description;
}
