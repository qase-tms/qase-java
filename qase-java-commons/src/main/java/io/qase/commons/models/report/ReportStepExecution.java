package io.qase.commons.models.report;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Execution model for step result according to step.yaml specification
 */
public class ReportStepExecution {
    /**
     * Status of the step execution
     */
    public String status;

    /**
     * Start time of the step execution in milliseconds (nullable)
     */
    @SerializedName("start_time")
    public Long startTime;

    /**
     * End time of the step execution in milliseconds (nullable)
     */
    @SerializedName("end_time")
    public Long endTime;

    /**
     * Duration of the step execution in milliseconds (nullable)
     */
    public Long duration;

    /**
     * Attachments for this step execution
     */
    public List<ReportAttachment> attachments;

    public ReportStepExecution() {
        this.attachments = new ArrayList<>();
    }
}

