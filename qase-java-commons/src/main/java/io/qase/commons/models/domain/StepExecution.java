package io.qase.commons.models.domain;

import java.time.Instant;

public class StepExecution {
    public StepResultStatus status;
    public Long startTime;
    public Long endTime;
    public Long duration;

    public StepExecution() {
        this.startTime = Instant.now().toEpochMilli();
        this.status = StepResultStatus.UNTESTED;
    }

    public void stop() {
        this.endTime = Instant.now().toEpochMilli();
        this.duration = this.endTime - this.startTime;
    }
}

