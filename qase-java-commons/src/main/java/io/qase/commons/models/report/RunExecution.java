package io.qase.commons.models.report;

import com.google.gson.annotations.SerializedName;

public class RunExecution {
    @SerializedName("start_time")
    public long startTime;
    @SerializedName("end_time")
    public long endTime;
    public int duration;
    @SerializedName("cumulative_duration")
    public int cumulativeDuration;

    public RunExecution(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = (int) (endTime - startTime);
        this.cumulativeDuration = 0;
    }

    public void track(ShortReportResult result) {
        this.cumulativeDuration += result.duration;
    }
}
