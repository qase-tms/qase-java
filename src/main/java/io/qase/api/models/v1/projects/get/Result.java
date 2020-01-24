package io.qase.api.models.v1.projects.get;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Result {
    private String code;
    private Counts counts;
    private String title;
}
