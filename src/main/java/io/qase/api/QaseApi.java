package io.qase.api;

import io.qase.api.inner.GsonObjectMapper;
import io.qase.api.services.*;
import io.qase.api.services.v1.*;
import kong.unirest.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class QaseApi {
    private static final Logger logger = LoggerFactory.getLogger("QaseApi");
    private final ProjectService projects;
    private final TestCaseService testCases;
    private final SuiteService suites;
    private final MilestoneService milestones;
    private final SharedStepService sharedSteps;
    private final TestPlanService testPlans;
    private final TestRunService testRuns;
    private final TestRunResultService testRunResults;
    private final DefectService defects;
    private final CustomFieldService customFields;
    private final TeamService team;
    private final AttachmentService attachments;

    public QaseApi(final String apiToken) {
        this(apiToken, "https://api.qase.io/v1");
    }

    public QaseApi(final String apiToken, final String baseUrl) {
        UnirestInstance unirestInstance = Unirest.spawnInstance();
        unirestInstance.config()
                .setObjectMapper(new GsonObjectMapper())
                .addShutdownHook(true)
                .setDefaultHeader("Token", apiToken);
        QaseApiClient qaseApiClient = new QaseApiClient(unirestInstance, baseUrl);
        this.projects = new ProjectServiceImpl(qaseApiClient);
        this.testCases = new TestCaseServiceImpl(qaseApiClient);
        this.suites = new SuiteServiceImpl(qaseApiClient);
        this.milestones = new MilestoneServiceImpl(qaseApiClient);
        this.sharedSteps = new SharedStepServiceImpl(qaseApiClient);
        this.testPlans = new TestPlanServiceImpl(qaseApiClient);
        this.testRuns = new TestRunServiceImpl(qaseApiClient);
        this.testRunResults = new TestRunResultServiceImpl(qaseApiClient);
        this.defects = new DefectServiceImpl(qaseApiClient);
        this.customFields = new CustomFieldServiceImpl(qaseApiClient);
        this.team = new TeamServiceImpl(qaseApiClient);
        this.attachments = new AttachmentServiceImpl(qaseApiClient);
    }

    public ProjectService projects() {
        return this.projects;
    }

    public TestCaseService testCases() {
        return this.testCases;
    }

    public SuiteService suites() {
        return this.suites;
    }

    public MilestoneService milestones() {
        return this.milestones;
    }

    public SharedStepService sharedSteps() {
        return this.sharedSteps;
    }

    public TestPlanService testPlans() {
        return this.testPlans;
    }

    public TestRunService testRuns() {
        return this.testRuns;
    }

    public TestRunResultService testRunResults() {
        return this.testRunResults;
    }

    public DefectService defects() {
        return this.defects;
    }

    public CustomFieldService customFields() {
        return this.customFields;
    }

    public TeamService team() {
        return this.team;
    }

    public AttachmentService attachments() {
        return this.attachments;
    }
}
