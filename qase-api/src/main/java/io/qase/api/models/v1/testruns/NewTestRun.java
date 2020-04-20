package io.qase.api.models.v1.testruns;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class NewTestRun {
    private final String title;
    private String description;
    private final List<Integer> cases;
    @SerializedName("environment_id")
    private Integer environmentId;
}
