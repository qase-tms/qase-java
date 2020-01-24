package io.qase.api.models.v1.testplans.get;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Case {
    @SerializedName("assignee_id")
    private long assigneeId;
    @SerializedName("case_id")
    private long caseId;
}
