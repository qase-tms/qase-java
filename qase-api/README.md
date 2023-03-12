# [Qase TMS](https://qase.io) Java Api Client
[![License](https://lxgaming.github.io/badges/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

## Maven ##

Add the following dependency and repository to your pom.xml:
```xml
<dependency>
    <groupId>io.qase</groupId>
    <artifactId>qase-api</artifactId>
    <version>4.0.0</version>
</dependency>

```

## Usage ##
Qase.io uses API tokens to authenticate requests. You can view a manage your API keys in [API tokens pages](https://app.qase.io/user/api/token).

You must replace api_token with your personal API key.
 
```java
ApiClient apiClient = QaseClient.getApiClient();
apiClient.setApiKey("api_token");
```

### Projects ###

#### Get All Projects ####
This method allows to retrieve all projects available for your account. You can you limit and offset params to paginate.

```java
ProjectsApi projectsApi = new ProjectsApi(qaseApi);

List<Project> projects = projectsApi.getProjects(100, 0).getResult().getEntities();
```

#### Get All Projects ####
This method allows to retrieve a specific project.

```java
Project project = projectsApi.getProject("PROJ").getResult();
```

#### Create a new project ####
This method is used to create a new project through API.

```java
ProjectCreate project = new ProjectCreate()
    .code("PROJ")
    .title("Project title")
    .access(ProjectCreate.AccessEnum.NONE);
String code = projectsApi.createProject(project).getResult().getCode()
```

### Test cases ###

#### Get all test cases ####
This method allows to retrieve all test cases stored in selected project. You can you limit and offset params to paginate.

```java
CasesApi casesApi = new CasesApi(qaseApi);

GetCasesFiltersParameter filters = new GetCasesFiltersParameter()
        .automation("is-not-automated,to-be-automated")
        .behavior("positive")
        .milestoneId(11)
        .suiteId(2)
        .severity("critical")
        .priority("high,medium")
        .status("actual")
        .type("functional,acceptance")
        .search("title");
List<TestCase> testCases = casesApi.getCases("PRJCODE", filters, 100, 0)
        .getResult().getEntities();
```

#### Get a specific test case ####
This method allows to retrieve a specific test case.

```java
TestCase testCase = casesApi.getCase("PRJCODE", 4).getResult();
```

#### Delete test case ####
This method completely deletes a test case from repository.

```java
casesApi.deleteCase("PRJCODE", 4);
```

### Suites ###

#### Get all test suites ####
This method allows to retrieve all test suites stored in selected project. You can you limit and offset params to paginate.

```java
SuitesApi suitesApi = new SuitesApi(qaseApi);

List<Suite> suites = suitesApi.getSuites("PRJCODE", null, 100, 0)
        .getResult().getEntities();
```

#### Get a specific test suite ####
This method allows to retrieve a specific test suite.

```java
Suite suite = suitesApi.getSuite("PRJCODE", 18).getResult();
```

#### Create a new test suite ####
This method is used to create a new test suite through API.

```java
Long id = suitesApi.createSuite("PRJCODE", new SuiteCreate().title("SuiteTitle"))
        .getResult().getId();
```

#### Update test suite ####
This method is used to update a test suite through API. 

```java
suitesApi.updateSuite("PRJCODE", 18, new SuiteUpdate().title("NewSuiteTitle"));
```

#### Delete test suite ####
This method completely deletes a test suite from repository.

```java
suitesApi.deleteSuite("PRJCODE", 18, null);
```

### Milestones ###
#### Get all milestones ####
This method allows to retrieve all milestones stored in selected project. You can you limit and offset params to paginate.

```java
MilestonesApi milestonesApi = new MilestonesApi(qaseApi);

GetMilestonesFiltersParameter filters = new GetMilestonesFiltersParameter().search("title");
List<Milestone> milestones = milestonesApi.getMilestones("PRJCODE", filters, 100, 0)
        .getResult().getEntities();
```

#### Get a specific milestone ####
This method allows to retrieve a specific milestone.

```java
Milestone milestone = milestonesApi.getMilestone("PRJCODE", 1)
        .getResult();
```

#### Create a new milestone ####
This method is used to create a new milestone through API.

```java
Long id = milestonesApi.createMilestone("PRJCODE", new MilestoneCreate().title("MilestoneTitle"))
        .getResult().getId();
```
#### Update milestone ####
This method is used to update a milestone through API.

```java
Long id = milestonesApi.updateMilestone("PRJCODE", 6, new MilestoneUpdate().title("NewMilestoneTitle"))
        .getResult().getId();
```

#### Delete milestone ####
This method completely deletes a milestone from repository
```java
milestonesApi.deleteMilestone("PRJCODE", 6);
```

### Shared steps ###

#### Get all shared steps ####
This method allows to retrieve all shared steps stored in selected project. You can you limit and offset params to paginate.
```java
SharedStepsApi sharedStepsApi = new SharedStepsApi(qaseApi);

GetMilestonesFiltersParameter filters = new GetMilestonesFiltersParameter()
        .search("title");
List<SharedStep> sharedSteps = sharedStepsApi.getSharedSteps("PRJCODE", filters, 100, 0)
        .getResult().getEntities();
```

#### Get a specific shared step #####
This method allows to retrieve a specific shared step.
```java
SharedStep sharedStep = sharedStepsApi.getSharedStep("PRJCODE", "6676b8815da03124dc039d89cc111586a4f45dc9").getResult();
```

#### Create a new shared step ####
This method is used to create a new shared step through API.

```java
String hash = sharedStepsApi.createSharedStep("PRJCODE", new SharedStepCreate().title("title").action("step action")).getResult().getHash();
```

#### Update shared step ####
This method is used to update a shared step through API. 

```java
String hash = sharedStepsApi.updateSharedStep("PRJCODE", "6676b8815da03124dc039d89cc111586a4f45dc9",
        new SharedStepUpdate().title("title").action("step action")).getResult().getHash();
```

#### Delete shared step ####
This method completely deletes a shared step from repository. Also it will be removed from all test cases.

```java
sharedStepsApi.deleteSharedStep("PRJCODE", "6676b8815da03124dc039d89cc111586a4f45dc9");
```

### Test plans ###

#### Get all test plans ####
This method allows to retrieve all test plans stored in selected project. You can you limit and offset params to paginate.

```java
PlansApi plansApi = new PlansApi(qaseApi);

List<Plan> plans = plansApi.getPlans("PRJCODE", 100, 0).getResult().getEntities();
```

#### Get a specific test plan ####
This method allows to retrieve a specific test plan with detailed information about test cases in that plan and assignee.

```java
PlanDetailed planDetailed = plansApi.getPlan("PRJCODE", 1).getResult();
```

#### Create a new plan ####
This method is used to create a new test plan through API.

```java
Long id = plansApi.createPlan("PRJCODE", new PlanCreate().title("title").cases(Arrays.asList(1L, 2L, 3L)))
        .getResult().getId();
```

#### Update test plan ####
This method is used to update a test plan through API.

```java
Long id = plansApi.updatePlan("PRJCODE", 1, new PlanUpdate().title("title").description("description").cases(Arrays.asList(1L, 2L, 3L)))
        .getResult().getId();
```

#### Delete test plan #### 
This method completely deletes a test plan from repository

```java
plansApi.deletePlan("PRJCODE", 1);
```

### Test runs ###

#### Get all test runs ####
This method allows to retrieve all test runs stored in selected project. You can you limit and offset params to paginate.

```java
RunsApi runsApi = new RunsApi(qaseApi);

GetRunsFiltersParameter filters = new GetRunsFiltersParameter().status("complete");
List<Run> runs = runsApi.getRuns("PRJCODE", filters, 50, 0, "cases")
        .getResult().getEntities();
```

#### Get a specific test run ####
This method allows to retrieve a specific test run.

```java
Run run = runsApi.getRun("PRJ", 1, null).getResult();
```

#### Create a new test run ####
This method is used to create a new test run through API.

```java
RunCreate newTestRun = new RunCreate()
        .title("New test run")
        .cases(Arrays.asList(1L, 2L, 3L, 55L));
Long id = runsApi.createRun("PRJCODE", newTestRun)
        .getResult().getId();
```

#### Delete test run ####
This method completely deletes a test run from repository

```java
runsApi.deleteRun("PRJCODE", 1);
```

### Test run results ###

#### Get all test run results ####
This method allows to retrieve all test run results stored in selected project. You can you limit and offset params to paginate. Also you can use various filters to get specific results.

```java
ResultsApi resultsApi = new ResultsApi(qaseApi);

GetResultsFiltersParameter filters = new GetResultsFiltersParameter()
        .status("in_progress");
List<Result> results = resultsApi.getResults("PRJ", filters, 33, 3)
        .getResult().getEntities();
```

#### Get a specific test run result ####
This method allows to retrieve a specific test run result by hash.
```java
Result result = resultsApi.getResult("PRJCODE", "6676b8815da03124dc039d89cc111586a4f45dc9")
        .getResult();
```

#### Add a new test run result ####
This method allows to add a new test run result through API.
```java
ResultCreate resultCreate = new ResultCreate()
        .caseId(1L)
        .status(ResultCreate.StatusEnum.PASSED);
String hash = resultsApi.createResult("PRJCODE", 1, resultCreate)
        .getResult().getHash();
```

#### Update test run result ####
This method allows to update test run result through API. 
```java
ResultUpdate resultUpdate = new ResultUpdate().status(ResultUpdate.StatusEnum.FAILED);
resultsApi.updateResult("PRJCODE", 1, "6676b8815da03124dc039d89cc111586a4f45dc9",
        resultUpdate);
```

#### Delete test run result ####
This method completely deletes a test run result from repository
```java
resultsApi.deleteResult("PRJCODE", 1, "6676b8815da03124dc039d89cc111586a4f45dc9");
```

### Defects ###

#### Get all defects ####
This method allows to retrieve all defects stored in selected project. You can you limit and offset params to paginate.
```java
DefectsApi defectsApi = new DefectsApi(qaseApi);

GetDefectsFiltersParameter filters = new GetDefectsFiltersParameter()
        .status(GetDefectsFiltersParameter.StatusEnum.OPEN);
List<Defect> defects = defectsApi.getDefects("PROJ", filters, 88, 12).getResult().getEntities();
```

#### Get a specific defect ####
This method allows to retrieve a specific defect.
```java
Defect defect = defectsApi.getDefect("PRJCODE", 1).getResult();
```

#### Resolve ####
This method is used to resolve defect through API.
```java
defectsApi.resolveDefect("PRJCODE", 1)
```

#### Delete defect ####
This method completely deletes a defect from repository
```java
defectsApi.deleteDefect("PRJCODE", 1);
```

### Custom Fields ###

#### Get all custom fields ####
This method allows to retrieve all custom fields for a specific project. You can you limit and offset params to paginate.
```java
CustomFieldsApi customFieldsApi = new CustomFieldsApi(qaseApi);

List<CustomField> customFields = customFieldsApi.getCustomFields(null, 100, 0).getResult().getEntities();
```

#### Get a specific custom field ####
This method allows to retrieve one custom fields for specific project by id
```java
CustomField customField = customFieldsApi.getCustomField(1).getResult();
```

### Attachments ###

#### Get all attachments ####
This method allows to retrieve all attachments uploaded into your projects. You can you limit and offset params to paginate.

```java
AttachmentsApi attachmentsApi = new AttachmentsApi(qaseApi);

List<AttachmentGet> attachments = attachmentsApi.getAttachments(100, 0).getResult().getEntities();
```

#### Get a specific attachment ####
This method allows to retrieve a specific attachment by hash.

```java
AttachmentGet attachment = attachmentsApi.getAttachment("6676b8815da03124dc039d89cc111586a4f45dc9").getResult();
```

#### Upload attachment ####
This method allows to upload attachment to Qase. Max upload size: * Up to 32 Mb per file.
```java
File file = new File("1.png");
List<AttachmentGet> attachments = attachmentsApi.uploadAttachment("PRJCODE",
        Collections.singletonList(file))
        .getResult();
```

#### Delete attachment ####
This method completely deletes an attachment.
```java
attachmentsApi.deleteAttachment("6676b8815da03124dc039d89cc111586a4f45dc9");
```

#### Associate an attachment with a test case or step ####

There is a possibility to associate one or few files with an exact test case or a part of it (i.e. a step of a test case).
To do so, you can do it either by using `AttachmentsApi` with setting obtained ids directly to `ResultCreate`/`ResultCreateSteps` or by utilizing `Attachments` class.

There are a few `Attachments.addAttachmentsToCurrentContext` use-cases down below:

##### Test case with attachments

```java
@CaseId(1)
public void testCase() throws QaseException {
    // your test case code here
    Attachments.addAttachmentsToCurrentContext(
        Collections.singletonList(Paths.get("caseScreenshot.jpg").toFile())
    );
    // your test case code here
}
```

In the case above, `caseScreenshot.jpg` will be uploaded to the Qase web application, namely - to your project.
Afterward, the running test (of id 1) case will have the id of uploaded `caseScreenshot.jpg` associated with it.

##### Test step with attachments

```java
@CaseId(2)
public void testCase() throws QaseException {
    // your test case code here
    testStep();
    // your test case code here
}

@Step("A step of the test case")
public void testStep() throws QaseException {
    // your test step code here
    Attachments.addAttachmentsToCurrentContext(
        Collections.singletonList(Paths.get("stepScreenshot.jpg").toFile())
    );
    // your test step code here
}
```

In the case above, `stepScreenshot.jpg` will be uploaded to the Qase web application, namely - to your project.
Afterward, the running test (of id 1) case will have the step with `A step of the test case` action. 
In its turn, the step will have the id of uploaded `stepScreenshot.jpg` associated with it.

##### Both a case and a step with attachments

```java
@CaseId(3)
public void testCase() throws QaseException {
    // your test case code here
    Attachments.addAttachmentsToCurrentContext(
        Collections.singletonList(Paths.get("caseScreenshot.jpg").toFile())
    );
    testStep();
    // your test case code here
}

@Step("A step of the test case")
public void testStep() throws QaseException {
    // your test step code here
    Attachments.addAttachmentsToCurrentContext(
        Collections.singletonList(Paths.get("stepScreenshot.jpg").toFile())
    );
    // your test step code here
}
```


In the case above, both `caseScreenshot.jpg` and `stepScreenshot.jpg` will be uploaded to the Qase web application, namely - to your project.
Afterward, the running test (of id 3) case will have the step and the id of uploaded `caseScreenshot.jpg` associated with it.
In its turn, the step will have the id of uploaded `stepScreenshot.jpg` associated with it.

Note, `caseScreenshot.jpg` will be associated only with the test case and `stepScreenshot.jpg` will be associated only with the step.
