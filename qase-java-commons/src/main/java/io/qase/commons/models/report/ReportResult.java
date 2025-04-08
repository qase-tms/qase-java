package io.qase.commons.models.report;

import io.qase.commons.models.domain.*;

import java.util.List;
import java.util.Map;

public class ReportResult {
    public String id;
    public String title;
    public String signature;
    public String runId;
    public List<Long> testopsIds;
    public TestResultExecution execution;
    public Map<String, String> fields;
    public List<String> attachments;
    public List<ReportStepResult> steps;
    public Map<String, String> params;
    public List<List<String>> paramGroups;
    public String author;
    public Relations relations;
    public boolean muted;
    public String message;
}
