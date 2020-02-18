package io.qase.api.models.v1.suites;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Suite {
    @SerializedName("cases_count")
    private long casesCount;
    private String created;
    private String description;
    private long id;
    @SerializedName("parent_id")
    private Long parentId;
    private long position;
    private String preconditions;
    private String title;
    private String updated;
}
