package io.qase.commons.reporters;

import io.qase.commons.QaseException;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.models.domain.Data;
import io.qase.commons.models.domain.StepResult;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.report.*;
import io.qase.commons.writers.Writer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileReporter implements InternalReporter {
    private static final Logger logger = LoggerFactory.getLogger(FileReporter.class);

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
        this.startTime = System.currentTimeMillis();
        this.writer.prepare();
    }

    @Override
    public void completeTestRun() throws QaseException {
        long endTime = System.currentTimeMillis();

        Gson gson = new Gson();

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
    public void addResult(TestResult result) throws QaseException {
        this.results.add(result);
    }

    @Override
    public void uploadResults() throws QaseException {
        // Do nothing
    }

    private ReportResult convertTestResult(TestResult result) {
        ReportResult reportResult = new ReportResult();
        reportResult.id = result.id;
        reportResult.title = result.title;
        reportResult.execution = result.execution;
        reportResult.muted = result.muted;
        reportResult.message = result.message;
        reportResult.relations = result.relations;
        reportResult.params = result.params;
        reportResult.paramGroups = result.paramGroups;
        reportResult.fields = result.fields;
        reportResult.testopsId = result.testopsId;
        reportResult.runId = result.runId;
        reportResult.author = result.author;
        reportResult.signature = result.signature;

        reportResult.steps = result.steps.stream()
                .map(this::convertStepResult)
                .collect(Collectors.toList());
        reportResult.attachments = result.attachments.stream()
                .map(this.writer::writeAttachment)
                .filter(attachment -> !attachment.isEmpty())
                .collect(Collectors.toList());

        return reportResult;
    }

    private ReportStepResult convertStepResult(StepResult stepResult) {
        ReportStepResult reportStepResult = new ReportStepResult();
        reportStepResult.id = stepResult.id;
        reportStepResult.parentId = stepResult.parentId;
        reportStepResult.data = this.convertData(stepResult.data);
        reportStepResult.execution = stepResult.execution;
        reportStepResult.steps = stepResult.steps.stream()
                .map(this::convertStepResult)
                .collect(Collectors.toList());

        reportStepResult.attachments = stepResult.attachments.stream()
                .map(this.writer::writeAttachment)
                .filter(attachment -> !attachment.isEmpty())
                .collect(Collectors.toList());

        return reportStepResult;
    }

    private ReportData convertData(Data data) {
        ReportData reportData = new ReportData();
        reportData.action = data.action;
        reportData.expectedResult = data.expectedResult;
        reportData.inputData = data.inputData;

        reportData.attachments = data.attachments.stream()
                .map(this.writer::writeAttachment)
                .filter(attachment -> !attachment.isEmpty())
                .collect(Collectors.toList());

        return reportData;
    }


    @Override
    public List<TestResult> getResults() {
        return this.results;
    }

    @Override
    public void setResults(List<TestResult> results) {
        this.results.clear();
        this.results.addAll(results);
    }
}
