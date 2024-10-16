package io.qase.commons.models.domain;

import com.google.gson.Gson;

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
    public boolean ignore = false;

    public TestResult() {
        this.attachments = new ArrayList<>();
        this.steps = new ArrayList<>();
        this.params = new HashMap<>();
        this.paramGroups = new ArrayList<>();
        this.relations = new Relations();
        this.execution = new TestResultExecution();
        this.fields = new HashMap<>();
    }

    @Override
    public String toString() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
