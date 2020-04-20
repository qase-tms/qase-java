package io.qase.api.models.v1.shared_steps;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class NewSharedStep {
    private final String title;
    private final String action;
    @SerializedName("expected_result")
    private String expectedResult;
}
