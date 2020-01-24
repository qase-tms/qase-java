package io.qase.api.models.v1.projects.add;

import io.qase.api.enums.Access;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class CreateUpdateProjectRequest {
    private final String code;
    private final String title;
    private String description;
    private Access access = Access.none;
    private String group;
}
