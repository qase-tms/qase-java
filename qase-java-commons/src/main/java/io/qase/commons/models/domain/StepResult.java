package io.qase.commons.models.domain;


import java.util.*;

public class StepResult {
    public String id;
    public Data data;
    public String parentId;
    public StepExecution execution;
    public List<Attachment> attachments;
    public List<StepResult> steps;
}

