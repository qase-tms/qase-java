package io.qase.commons.models.domain;


import java.util.*;

public class StepResult {
    public String id = UUID.randomUUID().toString();
    public String stepType = "text";
    public Data data;
    public String parentId;
    public StepExecution execution;
    public Throwable throwable;
    public List<Attachment> attachments;
    public List<StepResult> steps;

    public StepResult() {
        this.attachments = new ArrayList<>();
        this.steps = new ArrayList<>();
        this.execution = new StepExecution();
        this.data = new Data();
    }
}

