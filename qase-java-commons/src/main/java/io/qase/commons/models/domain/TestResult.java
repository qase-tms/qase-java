package io.qase.commons.models.domain;

import java.util.*;


public class TestResult {
    public String id = UUID.randomUUID().toString();
    public String title;
    public String signature;
    public String runId;
    public Long testopsId;
    public TestResultExecution execution;
    public Map<String, String> fields;
    public List<Attachment> attachments;
    public List<StepResult> steps;
    public Map<String, String> params;
    public List<List<String>> paramGroups;
    public String author;
    public Relations relations;
    public boolean muted;
    public String message;
}
