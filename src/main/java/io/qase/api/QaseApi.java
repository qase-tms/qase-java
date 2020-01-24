package io.qase.api;

import io.qase.api.inner.GsonObjectMapper;
import kong.unirest.Unirest;
import kong.unirest.UnirestInstance;

public final class QaseApi {
    private final QaseApiClient qaseApiClient;

    public QaseApi(String apiToken) {
        this(apiToken, "https://api.qase.io/v1");
    }

    public QaseApi(String apiToken, String baseUrl) {
        UnirestInstance unirestInstance = Unirest.spawnInstance();
        unirestInstance.config()
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
