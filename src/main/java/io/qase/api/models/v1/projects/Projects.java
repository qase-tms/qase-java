package io.qase.api.models.v1.projects;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class Projects {
    private long count;
    @SerializedName("entities")
    private List<Project> projectList;
    private long filtered;
    private long total;
}
