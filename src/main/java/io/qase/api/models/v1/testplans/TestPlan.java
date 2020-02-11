package io.qase.api.models.v1.testplans;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class TestPlan {
    @SerializedName("average_time")
    private long averageTime;
    private List<Case> cases;
    @SerializedName("cases_count")
    private long casesCount;
    private String created;
    private String description;
    private long id;
    private String title;
    private String updated;
}
