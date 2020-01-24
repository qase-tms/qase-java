package io.qase.api.models.v1.testrunresults.add;

import com.google.gson.annotations.SerializedName;
import io.qase.api.enums.RunResultStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@SuppressWarnings("unused")
public class CreateUpdateTestRunResultRequest {
    @SerializedName("case_id")
    private Long caseId;
    private String comment;
    private Boolean defect;
    @SerializedName("member_id")
    private Long memberId;
    private RunResultStatus status;
    private List<Step> steps;
    private Long time;
}
