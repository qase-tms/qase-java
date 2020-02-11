package io.qase.api.models.v1.milestones;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class Milestones {

    private long count;
    @SerializedName("entities")
    private List<Milestone> milestoneList;
    private long filtered;
    private long total;

}
