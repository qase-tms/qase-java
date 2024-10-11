package io.qase.commons.models.report;

import io.qase.commons.models.domain.StepExecution;

import java.util.List;

public class ReportStepResult {
    public String id;
    public ReportData data;
    public String parentId;
    public StepExecution execution;
    public List<String> attachments;
    public List<ReportStepResult> steps;
}
