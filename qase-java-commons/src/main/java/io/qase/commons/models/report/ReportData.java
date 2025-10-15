package io.qase.commons.models.report;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Data model for step according to step.yaml specification
 */
public class ReportData {
    public String action;
    
    @SerializedName("expected_result")
    public String expectedResult;
    
    @SerializedName("input_data")
    public String inputData;
    
    public List<ReportAttachment> attachments;

    public ReportData() {
        this.attachments = new ArrayList<>();
    }
}
