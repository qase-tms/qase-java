package io.qase.commons.models.domain;

public class StepExecution {
    public StepResultStatus status;
    public long startTime;
    public long endTime;
    public int duration;

    public StepExecution() {
        this.startTime = System.currentTimeMillis();
        this.status = StepResultStatus.UNTESTED;
    }

    public void stop() {
        this.endTime = System.currentTimeMillis();
        this.duration = (int) (this.endTime - this.startTime);
    }
}

