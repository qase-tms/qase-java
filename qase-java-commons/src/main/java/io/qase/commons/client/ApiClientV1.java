package io.qase.commons.client;

import io.qase.client.v1.ApiClient;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.api.AttachmentsApi;
import io.qase.client.v1.api.ResultsApi;
import io.qase.client.v1.api.RunsApi;
import io.qase.client.v1.models.*;
import io.qase.commons.QaseException;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.models.domain.Attachment;
import io.qase.commons.models.domain.StepResult;
import io.qase.commons.models.domain.TestResult;
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
    private final String url;

    public ApiClientV1(QaseConfig config) {
        this.config = config;
        this.client = new ApiClient();

        if (config.testops.api.host.equals("qase.io")) {
            this.client.setBasePath("https://api.qase.io/v1");
            this.url = "https://app.qase.io/";
        } else {
            String url = "https://api-" + config.testops.api.host + "/v1";
            this.client.setBasePath(url);
            this.url = "https://app-" + config.testops.api.host + "/";
        }

        this.client.setApiKey(config.testops.api.token);
    }

    @Override
    public Long createTestRun() throws QaseException {
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

        try {
            return Objects.requireNonNull(
                    new RunsApi(client)
                            .createRun(this.config.testops.project, model)
                            .getResult()
            ).getId();
        } catch (ApiException e) {
            throw new QaseException("Failed to create test run: " + e.getResponseBody(), e.getCause());
        }
    }

    @Override
    public void completeTestRun(Long runId) throws QaseException {
        try {
            new RunsApi(client)
                    .completeRun(this.config.testops.project, runId.intValue());
        } catch (ApiException e) {
            throw new QaseException("Failed to complete test run: " + e.getResponseBody(), e.getCause());
        }

        logger.info("Test run link: {}run/{}/dashboard/{}", this.url, this.config.testops.project, runId);
    }

    @Override
    public void uploadResults(Long runId, List<TestResult> results) throws QaseException {
        List<ResultCreate> models = results.stream()
                .map(this::convertResult)
                .collect(Collectors.toList());

        ResultCreateBulk model = new ResultCreateBulk().results(models);

        logger.debug("Uploading results: {}", model);

        try {
            new ResultsApi(client)
                    .createResultBulk(this.config.testops.project,
                            runId.intValue(),
                            model);
        } catch (ApiException e) {
            throw new QaseException("Failed to upload test results: " + e.getResponseBody(), e.getCause());
        }
    }

    private ResultCreate convertResult(TestResult result) {
        ResultCreateCase caseModel = new ResultCreateCase()
                .title(result.title);

        if (result.fields.containsKey("description")) {
            caseModel.setDescription(result.fields.get("description"));
        }

        if (result.fields.containsKey("severity")) {
            caseModel.setSeverity(result.fields.get("severity"));
        }

        if (result.fields.containsKey("priority")) {
            caseModel.setPriority(result.fields.get("priority"));
        }

        if (result.fields.containsKey("preconditions")) {
            caseModel.setPreconditions(result.fields.get("preconditions"));
        }

        if (result.fields.containsKey("postconditions")) {
            caseModel.setPostconditions(result.fields.get("postconditions"));
        }

        if (result.fields.containsKey("layer")) {
            caseModel.setLayer(result.fields.get("layer"));
        }

        if (result.relations != null) {
            String suite = result.relations.suite.data.stream().map(suiteData -> suiteData.title).collect(Collectors.joining("\t"));
            caseModel.setSuiteTitle(suite);
        }

        ResultCreate model = new ResultCreate()
                .caseId(result.testopsId)
                .status(result.execution.status.toString().toLowerCase())
                .comment(result.message)
                .defect(this.config.testops.defect)
                .timeMs(result.execution.duration.longValue())
                .stacktrace(result.execution.stacktrace)
                .param(result.params)
                .paramGroups(result.paramGroups);

        model.setCase(caseModel);

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
