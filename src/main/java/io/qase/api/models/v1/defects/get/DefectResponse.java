package io.qase.api.models.v1.defects.get;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class DefectResponse {
    private Result result;
    private Boolean status;
}
