package io.qase.api.models.v1.projects;

import io.qase.api.enums.Access;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class NewProject {
    private final String code;
    private final String title;
    private String description;
    private Access access = Access.none;
    private String group;
}
