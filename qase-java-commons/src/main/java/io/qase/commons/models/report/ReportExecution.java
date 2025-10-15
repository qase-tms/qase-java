package io.qase.commons.models.report;

import com.google.gson.annotations.SerializedName;

/**
 * Execution model for test result according to result.yaml specification
 */
public class ReportExecution {
    /**
     * Status of the test execution
     */
    public String status;

    /**
     * Start time of the test execution in milliseconds (nullable)
     */
    @SerializedName("start_time")
    public Long startTime;

    /**
     * End time of the test execution in milliseconds (nullable)
     */
    @SerializedName("end_time")
    public Long endTime;

    /**
     * Duration of the test execution in milliseconds (nullable)
     */
    public Long duration;

    /**
     * Stacktrace in case of failure (nullable)
     */
    public String stacktrace;

    /**
     * Thread name where the test was executed (nullable)
     */
    public String thread;
}

