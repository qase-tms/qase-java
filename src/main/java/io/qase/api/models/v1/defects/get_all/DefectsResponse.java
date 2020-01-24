package io.qase.api.models.v1.defects.get_all;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class DefectsResponse {
    private Result result;
    private Boolean status;
}
