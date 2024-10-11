package io.qase.commons.reporters;

import io.qase.commons.QaseException;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.models.domain.Attachment;
import io.qase.commons.models.domain.Data;
import io.qase.commons.models.domain.StepResult;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.report.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FileReporter implements Reporter {
    private static final Logger logger = LoggerFactory.getLogger(FileReporter.class);

    private final QaseConfig config;
    final List<ReportResult> results;
    private Long startTime;

    private final String rootPath;
    private final String resultsPath;
    private final String attachmentPath;

    public FileReporter(QaseConfig config) {
        this.config = config;
        this.results = new ArrayList<>();

        this.rootPath = Paths.get(System.getProperty("user.dir"), this.config.report.connection.path).toString();
        this.resultsPath = Paths.get(this.rootPath, "results").toString();
        this.attachmentPath = Paths.get(this.rootPath, "attachments").toString();
    }

    @Override
    public void startTestRun() throws QaseException {
        this.startTime = System.currentTimeMillis();

        Path path = Paths.get(this.rootPath);
        this.createDirectory(path);

        Path resultsPath = Paths.get(this.resultsPath);
        this.createDirectory(resultsPath);

        Path attachmentPath = Paths.get(this.attachmentPath);
        this.createDirectory(attachmentPath);
    }

    @Override
    public void completeTestRun() throws QaseException {
        long endTime = System.currentTimeMillis();
        Run run = new Run(this.config.testops.run.title,
                this.startTime, endTime, this.config.environment);

        run.addResults(this.results);

        Gson gson = new Gson();

        String path = Paths.get(this.rootPath, "run.json").toString();
        File file = new File(path);

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(gson.toJson(run));
        } catch (IOException e) {
            throw new QaseException("Failed to write run result to file: " + path, e.getCause());
        }
    }

    @Override
    public void addResult(TestResult result) throws QaseException {
        this.results.add(this.convertTestResult(result));
    }

    @Override
    public void uploadResults() throws QaseException {
        Gson gson = new Gson();

        for (ReportResult result : this.results) {
            String path = Paths.get(this.resultsPath, (result.id + ".json")).toString();
            File file = new File(path);

            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(gson.toJson(result));
            } catch (IOException e) {
                throw new QaseException("Failed to write report result to file: " + path, e.getCause());
            }
        }
    }

    private void createDirectory(Path path) throws QaseException {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new QaseException("Failed to create directory for report: " + path, e.getCause());
            }
        }
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
                .map(this::saveAttachment)
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
                .map(this::saveAttachment)
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
                .map(this::saveAttachment)
                .filter(attachment -> !attachment.isEmpty())
                .collect(Collectors.toList());

        return reportData;
    }

    String saveAttachment(Attachment attachment) {
        if (attachment.filePath != null) {
            Path source = Paths.get(attachment.filePath);
            Path destination = Paths.get(this.attachmentPath, (attachment.id + "-" + source.getFileName().toString()));

            try {
                Files.copy(source, destination);
                return destination.toString();
            } catch (IOException e) {
                logger.error("Failed to save attachment: {}", attachment.filePath, e);
            }
            return "";
        }

        String destination = Paths.get(this.attachmentPath, (attachment.id + "-" + attachment.fileName)).toString();
        File file = new File(destination);

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(attachment.content);
            return destination;
        } catch (IOException e) {
            logger.error("Failed to write attachment content to file: {}", e.getMessage(), e);
            return "";
        }
    }
}
