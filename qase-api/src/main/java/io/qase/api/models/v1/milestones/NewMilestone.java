package io.qase.api.models.v1.milestones;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class NewMilestone {
    private final String title;
    private String description;
}
