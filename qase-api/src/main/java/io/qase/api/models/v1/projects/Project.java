package io.qase.api.models.v1.projects;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Project {
    private String code;
    private Counts counts;
    private String title;
}
