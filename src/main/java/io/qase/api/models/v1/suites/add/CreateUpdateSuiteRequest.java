package io.qase.api.models.v1.suites.add;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class CreateUpdateSuiteRequest {
    @SerializedName("parent_id")
    private Integer parentId;
    private final String title;
    private String description;
    private String preconditions;
}
