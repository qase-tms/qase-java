package io.qase.commons.models.report;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Step result model for file report according to step.yaml specification
 */
public class ReportStepResult {
    public String id;
    
    /**
     * Type of the step
     */
    @SerializedName("step_type")
    public String stepType;
    
    public ReportData data;
    
    @SerializedName("parent_id")
    public String parentId;
    
    public ReportStepExecution execution;
    
    public List<ReportStepResult> steps;

    public ReportStepResult() {
        this.steps = new ArrayList<>();
    }
}
