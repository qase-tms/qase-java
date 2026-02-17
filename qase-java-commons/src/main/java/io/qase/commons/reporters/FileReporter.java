package io.qase.commons.reporters;

import io.qase.commons.QaseException;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.models.domain.Attachment;
import io.qase.commons.models.domain.Data;
import io.qase.commons.models.domain.StepResult;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.report.*;
import io.qase.commons.writers.Writer;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FileReporter implements InternalReporter {

    private final QaseConfig config;
    final List<TestResult> results;
    private Long startTime;

    private final Writer writer;

    public FileReporter(QaseConfig config, Writer writer) {
        this.config = config;
        this.results = new ArrayList<>();
        this.writer = writer;
    }

    @Override
    public void startTestRun() throws QaseException {
        this.startTime = Instant.now().toEpochMilli();
        this.writer.prepare();
    }

    @Override
    public void completeTestRun() throws QaseException {
        long endTime = Instant.now().toEpochMilli();

        List<ReportResult> results = this.results.stream()
                .map(this::convertTestResult)
                .collect(Collectors.toList());

        for (ReportResult result : results) {
            this.writer.writeResult(result);
        }

        Run run = new Run(this.config.testops.run.title,
                this.startTime, endTime, this.config.environment);

        run.addResults(results);

        this.writer.writeRun(run);
    }

    @Override
    public synchronized void addResult(TestResult result) throws QaseException {
        this.results.add(result);
    }

    @Override
    public synchronized void uploadResults() throws QaseException {
        // Do nothing
    }

    private ReportResult convertTestResult(TestResult result) {
        ReportResult reportResult = new ReportResult();
        reportResult.id = result.id;
        reportResult.title = result.title;
        reportResult.muted = result.muted;
        reportResult.message = result.message;
        reportResult.relations = result.relations;
        reportResult.params = result.params;
        reportResult.paramGroups = result.paramGroups;
        reportResult.fields = result.fields;
        reportResult.testopsIds = result.testopsIds;
        reportResult.signature = result.signature;

        // Convert execution
        reportResult.execution = convertExecution(result.execution);

        // Convert steps
        reportResult.steps = result.steps.stream()
                .map(this::convertStepResult)
                .collect(Collectors.toList());

        // Convert attachments
        reportResult.attachments = result.attachments.stream()
                .map(this.writer::writeAttachment)
                .filter(attachment -> attachment != null)
                .collect(Collectors.toList());

        return reportResult;
    }

    private ReportExecution convertExecution(io.qase.commons.models.domain.TestResultExecution execution) {
        ReportExecution reportExecution = new ReportExecution();
        reportExecution.status = execution.status != null ? execution.status.toString().toLowerCase() : null;
        reportExecution.startTime = execution.startTime;
        reportExecution.endTime = execution.endTime;
        reportExecution.duration = execution.duration != null ? execution.duration.longValue() : null;
        reportExecution.stacktrace = execution.stacktrace;
        reportExecution.thread = execution.thread;
        return reportExecution;
    }

    private ReportStepExecution convertStepExecution(io.qase.commons.models.domain.StepExecution execution, List<Attachment> attachments) {
        ReportStepExecution reportStepExecution = new ReportStepExecution();
        reportStepExecution.status = execution.status != null ? execution.status.toString().toLowerCase() : null;
        reportStepExecution.startTime = execution.startTime;
        reportStepExecution.endTime = execution.endTime;
        reportStepExecution.duration = execution.duration;
        
        // Convert attachments for step execution
        reportStepExecution.attachments = attachments.stream()
                .map(this.writer::writeAttachment)
                .filter(attachment -> attachment != null)
                .collect(Collectors.toList());
        
        return reportStepExecution;
    }

    private ReportStepResult convertStepResult(StepResult stepResult) {
        ReportStepResult reportStepResult = new ReportStepResult();
        reportStepResult.id = stepResult.id;
        reportStepResult.parentId = stepResult.parentId;
        reportStepResult.stepType = stepResult.stepType != null ? stepResult.stepType : "text";
        reportStepResult.data = this.convertData(stepResult.data);
        
        // Convert execution with attachments
        reportStepResult.execution = convertStepExecution(stepResult.execution, stepResult.attachments);
        
        // Convert nested steps
        reportStepResult.steps = stepResult.steps.stream()
                .map(this::convertStepResult)
                .collect(Collectors.toList());

        return reportStepResult;
    }

    private ReportData convertData(Data data) {
        ReportData reportData = new ReportData();
        reportData.action = data.action;
        reportData.expectedResult = data.expectedResult;
        reportData.inputData = data.inputData;

        return reportData;
    }


    @Override
    public synchronized List<TestResult> getResults() {
        return this.results;
    }

    @Override
    public synchronized void setResults(List<TestResult> results) {
        this.results.clear();
        this.results.addAll(results);
    }

    @Override
    public List<Long> getTestCaseIdsForExecution() {
        return Collections.emptyList();
    }
}
