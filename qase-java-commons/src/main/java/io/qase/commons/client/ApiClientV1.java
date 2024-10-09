package io.qase.commons.client;

import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.api.AttachmentsApi;
import io.qase.client.v1.api.ResultsApi;
import io.qase.client.v1.api.RunsApi;
import io.qase.client.v1.models.*;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.models.Attachment;
import io.qase.commons.models.StepResult;
import io.qase.commons.models.TestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Qase API V1 client
 */
public class ApiClientV1 implements io.qase.commons.client.ApiClient {
    private static final Logger logger = LoggerFactory.getLogger(ApiClientV1.class);

    private final QaseConfig config;
    private final ApiClient client;

    public ApiClientV1(QaseConfig config) {
        this.config = config;
        this.client = new ApiClient();

        this.client.setBasePath(config.testops.api.host);
        this.client.setApiKey(config.testops.api.token);
    }

    @Override
    public Long createTestRun() throws ApiException {
        RunCreate model = new RunCreate()
                .title(this.config.testops.run.title)
                .isAutotest(true);

        if (this.config.testops.run.description != null) {
            model.setDescription(this.config.testops.run.description);
        }

        if (this.config.environment != null) {
            model.setEnvironmentSlug(this.config.environment);
        }

        if (this.config.testops.plan.id != 0) {
            model.setPlanId((long) this.config.testops.plan.id);
        }

        return Objects.requireNonNull(
                new RunsApi(client)
                        .createRun(this.config.testops.project, model)
                        .getResult()
        ).getId();
    }

    @Override
    public void completeTestRun(Long runId) throws ApiException {
        new RunsApi(client)
                .completeRun(this.config.testops.project, runId.intValue());
    }

    @Override
    public void uploadResults(Long runId, List<TestResult> results) throws ApiException {
        List<ResultCreate> models = results.stream()
                .map(this::convertResult)
                .collect(Collectors.toList());


        new ResultsApi(client)
                .createResultBulk(this.config.testops.project,
                        runId.intValue(),
                        new ResultCreateBulk().results(models));
    }

    private ResultCreate convertResult(TestResult result) {
        ResultCreate model = new ResultCreate()
                .caseId(result.testopsId)
                .status(result.execution.status.toString().toLowerCase())
                .comment(result.message)
                .defect(this.config.testops.defect)
                .startTime(result.execution.startTime.intValue())
                .time(result.execution.endTime)
                .timeMs(result.execution.duration.longValue())
                .stacktrace(result.execution.stacktrace)
                .param(result.params)
                .paramGroups(result.paramGroups);

        List<TestStepResultCreate> steps = result.steps.stream()
                .map(this::convertStepResult).collect(Collectors.toList());
        model.setSteps(steps);

        List<String> attachments = result.attachments.stream()
                .map(this::uploadAttachment)
                .filter(attachment -> !attachment.isEmpty())
                .collect(Collectors.toList());
        model.setAttachments(attachments);


        return model;
    }

    private TestStepResultCreate convertStepResult(StepResult step) {
        TestStepResultCreate model = new TestStepResultCreate()
                .status(TestStepResultCreate.StatusEnum.fromValue(step.execution.status.toString().toLowerCase()))
                .action(step.data.action);

        List<Object> steps = step.steps.stream()
                .map(this::convertStepResult).collect(Collectors.toList());

        model.setSteps(steps);

        List<String> attachments = step.attachments.stream()
                .map(this::uploadAttachment)
                .filter(attachment -> !attachment.isEmpty())
                .collect(Collectors.toList());
        model.setAttachments(attachments);

        return model;
    }

    private String uploadAttachment(Attachment attachment) {
        AttachmentsApi api = new AttachmentsApi(client);
        File file = createOrGetFile(attachment);

        if (file == null) {
            return "";
        }

        try {
            List<Attachmentupload> response = api.uploadAttachment(this.config.testops.project, Collections.singletonList(file)).getResult();
            return processUploadResponse(response, file);
        } catch (ApiException e) {
            logger.error("Failed to upload attachment: {}", e.getResponseBody());
            return "";
        }
    }

    private File createOrGetFile(Attachment attachment) {
        if (attachment.filePath != null) {
            return new File(attachment.filePath);
        }

        String tempPath = Paths.get(System.getProperty("user.dir"), attachment.fileName).toString();
        File file = new File(tempPath);

        try (FileWriter fileWriter = new FileWriter(file)) {
            fileWriter.write(attachment.content);
        } catch (IOException e) {
            logger.error("Failed to write attachment content to file: {}", e.getMessage());
            return null;
        }

        return file;
    }

    private String processUploadResponse(List<Attachmentupload> response, File file) {
        if (file != null && file.exists()) {
            file.delete();
        }

        if (response == null || response.isEmpty()) {
            return "";
        }

        return response.get(0).getHash();
    }

}
