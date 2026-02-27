package io.qase.commons.models.domain;

public class TestResultExecution {
    public Long startTime;
    public TestResultStatus status;
    public String customStatus;
    public Long endTime;
    public Integer duration;
    public String stacktrace;
    public String thread;
    public transient Throwable throwable;
}
