package io.qase.api.models.v1.testrunresults.get_all;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class Entity {
    @SerializedName("case_id")
    private long caseId;
    private String comment;
    @SerializedName("end_time")
    private String endTime;
    private String hash;
    @SerializedName("is_api_result")
    private Boolean isApiResult;
    @SerializedName("run_id")
    private long runId;
    private String status;
    private List<Step> steps;
    @SerializedName("time_spent")
    private long timeSpent;
}
