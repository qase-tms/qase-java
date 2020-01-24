package io.qase.api.models.v1.projects.get_all;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Entity {
    private String code;
    private Counts counts;
    private String title;
}
