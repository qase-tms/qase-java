package io.qase.commons.models.report;

import com.google.gson.annotations.SerializedName;

/**
 * Data model for step according to step.yaml specification
 */
public class ReportData {
    public String action;

    @SerializedName("expected_result")
    public String expectedResult;

    @SerializedName("input_data")
    public String inputData;
}
