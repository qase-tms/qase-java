package io.qase.api.models.v1.shared_steps;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class SharedSteps {
    private long count;
    @SerializedName("entities")
    private List<SharedStep> sharedStepList;
    private long filtered;
    private long total;
}
