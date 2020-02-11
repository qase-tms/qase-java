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
                        if (request.getBody().isPresent()) {
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
                .setDefaultHeader("Token", apiToken)
                .setDefaultHeader("Content-Type", "application/json");
        this.qaseApiClient = new QaseApiClient(unirestInstance, baseUrl);
    }

    public Projects projects() {
        return new Projects(qaseApiClient);
    }

    public TestCases testCases() {
        return new TestCases(qaseApiClient);
    }

    public Suites suites() {
        return new Suites(qaseApiClient);
    }

    public Milestones milestones() {
        return new Milestones(qaseApiClient);
    }

    public SharedSteps sharedSteps() {
        return new SharedSteps(qaseApiClient);
    }

    public TestPlans testPlans() {
        return new TestPlans(qaseApiClient);
    }

    public TestRuns testRuns() {
        return new TestRuns(qaseApiClient);
    }

    public TestRunResults testRunResults() {
        return new TestRunResults(qaseApiClient);
    }

    public Defects defects() {
        return new Defects(qaseApiClient);
    }

    public CustomFields customFields() {
        return new CustomFields(qaseApiClient);
    }

    public Team team() {
        return new Team(qaseApiClient);
    }

    public Attachments attachments() {
        return new Attachments(qaseApiClient);
    }
}
