package io.qase.commons.models.report;

public class RunExecution {
    public long startTime;
    public long endTime;
    public int duration;
    public int cumulativeDuration;

    public RunExecution(long startTime, long endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = (int) ((endTime - startTime) * 1000);
        this.cumulativeDuration = 0;
    }

    public void track(ShortReportResult result) {
        this.cumulativeDuration += result.duration;
    }
}
