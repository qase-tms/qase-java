package io.qase.api.models.v1.projects.add;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class CreateUpdateProjectResponse {
    private Result result;
    private Boolean status;
    private String errorMessage;
}
