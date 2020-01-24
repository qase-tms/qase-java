package io.qase.api.models.v1.shared_steps.add;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class CreateUpdateSharedStepRequest {
    private final String title;
    private final String action;
    @SerializedName("expected_result")
    private String expectedResult;
}
