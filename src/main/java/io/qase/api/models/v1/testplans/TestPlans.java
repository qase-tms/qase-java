package io.qase.api.models.v1.testplans;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class TestPlans {
    private long count;
    @SerializedName("entities")
    private List<TestPlan> testPlanList;
    private long filtered;
    private long total;
}
