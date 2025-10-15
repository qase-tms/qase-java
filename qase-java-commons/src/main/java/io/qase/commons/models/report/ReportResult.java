package io.qase.commons.models.report;

import com.google.gson.annotations.SerializedName;
import io.qase.commons.models.domain.Relations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Test result model for file report according to result.yaml specification
 */
public class ReportResult {
    public String id;
    public String title;
    public String signature;
    public String runId;
    
    /**
     * Single testops ID (nullable)
     */
    @SerializedName("testops_id")
    public Long testopsId;
    
    /**
     * Array of testops IDs (nullable)
     */
    @SerializedName("testops_ids")
    public List<Long> testopsIds;
    
    public ReportExecution execution;
    public Map<String, String> fields;
    public List<ReportAttachment> attachments;
    public List<ReportStepResult> steps;
    public Map<String, String> params;
    
    @SerializedName("param_groups")
    public List<List<String>> paramGroups;
    
    public String author;
    public Relations relations;
    public boolean muted;
    public String message;

    public ReportResult() {
        this.attachments = new ArrayList<>();
        this.steps = new ArrayList<>();
    }
}
