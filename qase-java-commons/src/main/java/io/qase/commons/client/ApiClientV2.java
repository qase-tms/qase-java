package io.qase.commons.client;

import io.qase.client.v2.ApiException;
import io.qase.client.v2.api.ResultsApi;
import io.qase.client.v2.models.*;
import io.qase.commons.QaseException;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.logger.Logger;
import io.qase.commons.models.domain.StepResult;
import io.qase.commons.models.domain.TestResult;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ApiClientV2 implements ApiClient {
    private static final Logger logger = Logger.getInstance();

    private final QaseConfig config;
    private final ApiClientV1 apiClientV1;
    private final io.qase.client.v2.ApiClient client;

    public ApiClientV2(QaseConfig config) {
        this.config = config;
        apiClientV1 = new ApiClientV1(config);

        this.client = new io.qase.client.v2.ApiClient();

        if (config.testops.api.host.equals("qase.io")) {
            this.client.setBasePath("https://api.qase.io/v2");
        } else {
            String url = "https://api-" + config.testops.api.host + "/v2";
            this.client.setBasePath(url);
        }

        this.client.setApiKey(config.testops.api.token);
    }

    @Override
    public Long createTestRun() throws QaseException {
        return this.apiClientV1.createTestRun();
    }

    @Override
    public void completeTestRun(Long runId) throws QaseException {
        this.apiClientV1.completeTestRun(runId);
    }

    @Override
    public void updateExternalIssue(Long runId) throws QaseException {
        this.apiClientV1.updateExternalIssue(runId);
    }

    @Override
    public void uploadResults(Long runId, List<TestResult> results) throws QaseException {
        List<ResultCreate> models = results.stream()
                .map(this::convertResult)
                .collect(Collectors.toList());

        CreateResultsRequestV2 model = new CreateResultsRequestV2().results(models);

        logger.debug("Uploading results: %s", model);

        try {
            new ResultsApi(client)
                    .createResultsV2(this.config.testops.project,
                            runId,
                            model);
        } catch (ApiException e) {
            throw new QaseException("Failed to upload test results: " + e.getResponseBody(), e.getCause());
        }
    }

    @Override
    public List<Long> getTestCaseIdsForExecution() throws QaseException {
        return this.apiClientV1.getTestCaseIdsForExecution();
    }

    private ResultCreate convertResult(TestResult result) {
        List<String> attachments = result.attachments.stream()
                .map(this.apiClientV1::uploadAttachment)
                .filter(attachment -> !attachment.isEmpty())
                .collect(Collectors.toList());

        List<ResultStep> steps = result.steps.stream()
                .map(this::convertStepResult).collect(Collectors.toList());

        ResultExecution execution = new ResultExecution()
                .status(result.execution.status.toString().toLowerCase())
                .startTime(result.execution.startTime / 1000.0)
                .endTime(result.execution.endTime / 1000.0)
                .duration(result.execution.duration.longValue())
                .stacktrace(result.execution.stacktrace)
                .thread(result.execution.thread);

        List<RelationSuiteItem> data = new ArrayList<>();
        result.relations.suite.data.forEach(suiteData -> {
            RelationSuiteItem item = new RelationSuiteItem()
                    .title(suiteData.title);
            data.add(item);
        });
        RelationSuite suite = new RelationSuite().data(data);
        ResultRelations relations = new ResultRelations().suite(suite);

        ResultCreateFields fields = new ResultCreateFields();
        for (String key : result.fields.keySet()) {
            switch (key) {
                case "author":
                    fields.setAuthor(result.fields.get(key));
                    break;
                case "description":
                    fields.setDescription(result.fields.get(key));
                    break;
                case "preconditions":
                    fields.setPreconditions(result.fields.get(key));
                    break;
                case "postconditions":
                    fields.setPostconditions(result.fields.get(key));
                    break;
                case "layer":
                    fields.setLayer(result.fields.get(key));
                    break;
                case "severity":
                    fields.setSeverity(result.fields.get(key));
                    break;
                case "priority":
                    fields.setPriority(result.fields.get(key));
                    break;
                case "behavior":
                    fields.setBehavior(result.fields.get(key));
                    break;
                case "type":
                    fields.setType(result.fields.get(key));
                    break;
                case "muted":
                    fields.setMuted(result.fields.get(key));
                    break;
                case "isFlaky":
                    fields.setIsFlaky(result.fields.get(key));
                    break;
                default:
                    fields.putAdditionalProperty(key, result.fields.get(key));
            }
        }

        return new ResultCreate()
                .id(result.id)
                .title(result.title)
                .testopsIds(result.testopsIds)
                .signature(result.signature)
                .execution(execution)
                .fields(fields)
                .attachments(attachments)
                .steps(steps)
                .stepsType(ResultStepsType.CLASSIC)
                .params(result.params)
                .paramGroups(result.paramGroups)
                .relations(relations)
                .message(result.message)
                .defect(this.config.testops.defect);
    }

    private ResultStep convertStepResult(StepResult step) {
        ResultStepData data = new ResultStepData()
                .action(step.data.action);

        if (step.data.inputData != null) {
            data.inputData(step.data.inputData);
        }

        List<String> attachments = step.attachments.stream()
                .map(this.apiClientV1::uploadAttachment)
                .filter(attachment -> !attachment.isEmpty())
                .collect(Collectors.toList());

        ResultStepExecution execution = new ResultStepExecution()
                .status(ResultStepStatus.fromValue(step.execution.status.toString().toLowerCase()))
                .startTime(step.execution.startTime / 1000.0)
                .endTime(step.execution.endTime / 1000.0)
                .duration(step.execution.duration)
                .attachments(attachments);
        List<Object> steps = step.steps.stream()
                .map(this::convertStepResult).collect(Collectors.toList());

        return new ResultStep()
                .data(data)
                .execution(execution)
                .steps(steps);
    }
}
