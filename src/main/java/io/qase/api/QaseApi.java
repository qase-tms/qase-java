package io.qase.api;

import io.qase.api.inner.GsonObjectMapper;
import kong.unirest.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class QaseApi {
    private static final Logger logger = LoggerFactory.getLogger("QaseApi");
    private final QaseApiClient qaseApiClient;

    public QaseApi(String apiToken) {
        this(apiToken, "https://api.qase.io/v1");
    }

    public QaseApi(String apiToken, String baseUrl) {
        UnirestInstance unirestInstance = Unirest.spawnInstance();
        unirestInstance.config()
                .interceptor(new Interceptor() {
                    @Override
                    public void onRequest(HttpRequest<?> request, Config config) {
                        logger.info("{} {}", request.getHttpMethod().name(), request.getUrl());
                        if (request.getBody().isPresent() && request.getBody().get().isEntityBody()) {
                            logger.info(request.getBody().get().uniPart().getValue().toString());
                        }
                    }

                    @Override
                    public void onResponse(HttpResponse<?> response, HttpRequestSummary request, Config config) {
                        logger.info("{} {}", response.getStatus(), response.getStatusText());
                        logger.info("{}", response.getHeaders().toString());
                        logger.info("{}", response.getBody());
                    }

                    @Override
                    public HttpResponse<?> onFail(Exception e, HttpRequestSummary request, Config config) throws UnirestException {
                        return null;
                    }
                })
                .setObjectMapper(new GsonObjectMapper())
                .addShutdownHook(true)
                .setDefaultHeader("Token", apiToken);
        this.qaseApiClient = new QaseApiClient(unirestInstance, baseUrl);
    }

    public ProjectService projects() {
        return new ProjectService(qaseApiClient);
    }

    public TestCaseService testCases() {
        return new TestCaseService(qaseApiClient);
    }

    public SuiteService suites() {
        return new SuiteService(qaseApiClient);
    }

    public MilestoneService milestones() {
        return new MilestoneService(qaseApiClient);
    }

    public SharedStepService sharedSteps() {
        return new SharedStepService(qaseApiClient);
    }

    public TestPlanService testPlans() {
        return new TestPlanService(qaseApiClient);
    }

    public TestRunService testRuns() {
        return new TestRunService(qaseApiClient);
    }

    public TestRunResultService testRunResults() {
        return new TestRunResultService(qaseApiClient);
    }

    public DefectService defects() {
        return new DefectService(qaseApiClient);
    }

    public CustomFieldService customFields() {
        return new CustomFieldService(qaseApiClient);
    }

    public TeamService team() {
        return new TeamService(qaseApiClient);
    }

    public AttachmentService attachments() {
        return new AttachmentService(qaseApiClient);
    }
}
