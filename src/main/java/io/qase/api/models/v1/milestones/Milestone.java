package io.qase.api.models.v1.milestones;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Milestone {
    private String created;
    private String description;
    @SerializedName("due_date")
    private String dueDate;
    private long id;
    private String title;
    private String updated;
}
