package io.qase.api.models.v1.suites;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class NewSuite {
    @SerializedName("parent_id")
    private Integer parentId;
    private final String title;
    private String description;
    private String preconditions;
}
