package io.qase.api.models.v1.testrunresults;

import com.google.gson.annotations.SerializedName;
import io.qase.api.enums.RunResultStatus;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@SuppressWarnings("unused")
public class NewTestRunResults {
    @SerializedName("case_id")
    private Long caseId;
    private RunResultStatus status;
    private Long time;
    @SerializedName("member_id")
    private Integer memberId;
    private String comment;
    private String stacktrace;
    private Boolean defect;
    private List<Step> steps;
    private List<String> attachments;
}
