# [Qase TMS](https://qase.io) Java Api Client
[![License](https://lxgaming.github.io/badges/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

## Maven ##

Add the following dependency and repository to your pom.xml:
```xml
<dependency>
    <groupId>io.qase</groupId>
    <artifactId>qase-api</artifactId>
    <version>2.1.5</version>
</dependency>

```

## Usage ##
Qase.io uses API tokens to authenticate requests. You can view a manage your API keys in [API tokens pages](https://app.qase.io/user/api/token).

You must replace api_token with your personal API key.
 
```java
ApiClient qaseApi = Configuration.getDefaultApiClient();
qaseApi.setApiKey("api_token");
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
ProjectsApi projectsApi = new ProjectsApi(qaseApi);
Project project = projectsApi.getProject("PROJ").getResult();
```

#### Create a new project ####
This method is used to create a new project through API.

```java
ProjectsApi projectsApi = new ProjectsApi(qaseApi);
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
Filter filter = qaseApi.testCases().filter()
    .type(Type.other)
    .priority(Priority.high);
TestCases testCases = qaseApi.testCases().getAll("PRJCODE", filter);
List<TestCase> testCaseList = testCases.getTestCaseList();
```

#### Get a specific test case ####
This method allows to retrieve a specific test case.

```java
TestCase testCase = qaseApi.testCases().get("PRJCODE", 4);
```

#### Delete test case ####
This method completely deletes a test case from repository.

```java
boolean isDeleted = qaseApi.testCases().delete("PRJCODE", 4);
```

### Suites ###

#### Get all test suites ####
This method allows to retrieve all test suites stored in selected project. You can you limit and offset params to paginate.

```java
Suites suites = qaseApi.suites().getAll("PRJCODE");
List<Suite> suiteList = suites.getSuiteList();
```

#### Get a specific test suite ####
This method allows to retrieve a specific test suite.

```java
Suite suite = qaseApi.suites().get("PRJCODE", 18);
```

#### Create a new test suite ####
This method is used to create a new test suite through API.

```java
long id = qaseApi.suites().create("PRJCODE", "SuiteTitle", "Description");
```

#### Update test suite ####
This method is used to update a test suite through API. 

```java
qaseApi.suites().update("PRJCODE", 18, "NewSuiteTitle");
```

#### Delete test suite ####
This method completely deletes a test suite from repository.

```java
boolean isDeleted = qaseApi.suites().delete("PRJCODE", 18)
```

### Milestones ###
#### Get all milestones ####
This method allows to retrieve all milestones stored in selected project. You can you limit and offset params to paginate.

```java
Milestones milestones = qaseApi.milestones().getAll("PRJCODE");
List<Milestone> milestoneList = milestones.getMilestoneList();
```

#### Get a specific milestone ####
This method allows to retrieve a specific milestone.

```java
Milestone milestone = qaseApi.milestones().get("PRJCODE", 1)
```

#### Create a new milestone ####
This method is used to create a new milestone through API.

```java
long id = qaseApi.milestones().create("PRJCODE", "MilestoneTitle", "MilestoneDescription")
```
#### Update milestone ####
This method is used to update a milestone through API.

```java
long id = qaseApi.milestones().update("PRJCODE", 6, "NewMilestoneTitle");
```

#### Delete milestone ####
This method completely deletes a milestone from repository
```java
boolean isDeleted = qaseApi.milestones().delete("PRJCODE", 6);
```

### Shared steps ###

#### Get all shared steps ####
This method allows to retrieve all shared steps stored in selected project. You can you limit and offset params to paginate.
```java
SharedSteps sharedSteps = qaseApi.sharedSteps().getAll("PRJCODE");
List<SharedStep> sharedStepList = sharedSteps.getSharedStepList();
```

#### Get a specific shared step #####
This method allows to retrieve a specific shared step.
```java
SharedStep sharedStep = qaseApi.sharedSteps().get("PRJCODE", "6676b8815da03124dc039d89cc111586a4f45dc9");
```

#### Create a new shared step ####
This method is used to create a new shared step through API.

```java
String stepHashCode = qaseApi.sharedSteps().create("PRJCODE", "title", "step action", "step expected result");
```

#### Update shared step ####
This method is used to update a shared step through API. 

```java
String stepHashCode = qaseApi.sharedSteps().update("PRJCODE", "6676b8815da03124dc039d89cc111586a4f45dc9", "title", "step action", "step expected result");
```

#### Delete shared step ####
This method completely deletes a shared step from repository. Also it will be removed from all test cases.

```java
boolean isDeleted = qaseApi.sharedSteps().delete("PRJCODE", "6676b8815da03124dc039d89cc111586a4f45dc9");
```

### Test plans ###

#### Get all test plans ####
This method allows to retrieve all test plans stored in selected project. You can you limit and offset params to paginate.

```java
TestPlans testPlans = qaseApi.testPlans().getAll("PRJCODE");
List<TestPlan> testPlanList = testPlans.getTestPlanList();
```

#### Get a specific test plan ####
This method allows to retrieve a specific test plan with detailed information about test cases in that plan and assignee.

```java
TestPlan testPlan = qaseApi.testPlans().get("PRJCODE", 1);
```

#### Create a new plan ####
This method is used to create a new test plan through API.

```java
long id = qaseApi.testPlans().create("PRJCODE", "title", "description", 1, 2, 3);
```

#### Update test plan ####
This method is used to update a test plan through API.

```java
long id = qaseApi.testPlans().update("PRJCODE", 1, "title", "description", 1, 2, 3);
```

#### Delete test plan #### 
This method completely deletes a test plan from repository

```java
boolean isDeleted = qaseApi.testPlans().delete("PRJCODE", 1);
```

### Test runs ###

#### Get all test runs ####
This method allows to retrieve all test runs stored in selected project. You can you limit and offset params to paginate.

```java
TestRunService.Filter filter = qaseApi.testRuns().filter().status(active, complete);
TestRuns testRuns = qaseApi.testRuns().getAll("PRJCODE", 50, 0, filter, true);
List<TestRun> testRunList = testRuns.getTestRunList();
```

#### Get a specific test run ####
This method allows to retrieve a specific test run.

```java
TestRun testRun = qaseApi.testRuns().get("PRJCODE", 1, false);
```

#### Create a new test run ####
This method is used to create a new test run through API.

```java
qaseApi.testRuns().create("PRJCODE", "title", 1, 2, 3, 4);
```

#### Delete test run ####
This method completely deletes a test run from repository

```java
boolean isDeleted = qaseApi.testRuns().delete("PRJCODE", 1);
```

### Test run results ###

#### Get all test run results ####
This method allows to retrieve all test run results stored in selected project. You can you limit and offset params to paginate. Also you can use various filters to get specific results.

```java
TestRunResultService.Filter filter = qaseApi.testRunResults().filter().status(passed);
TestRunResults runResultsResponse = qaseApi.testRunResults().getAll("PRJCODE", 50, 0, filter);
List<TestRunResult> testRunResultList = runResultsResponse.getTestRunResultList();
```

#### Get a specific test run result ####
This method allows to retrieve a specific test run result by hash.
```java
TestRunResult testRunResult = qaseApi.testRunResults().get("PRJCODE", "6676b8815da03124dc039d89cc111586a4f45dc9");
```

#### Add a new test run result ####
This method allows to add a new test run result through API.
```java
String hash = qaseApi.testRunResults().create("PRJCODE", 1, 58, RunResultStatus.passed);
```

#### Update test run result ####
This method allows to update test run result through API. 
```java
String hash = qaseApi.testRunResults().update("PRJCODE", 1, "6676b8815da03124dc039d89cc111586a4f45dc9", passed, Duration.ofHours(2));
```

#### Delete test run result ####
This method completely deletes a test run result from repository
```java
boolean isDeleted = qaseApi.testRunResults().delete("PRJCODE", 1, "6676b8815da03124dc039d89cc111586a4f45dc9");
```

### Defects ###

#### Get all defects ####
This method allows to retrieve all defects stored in selected project. You can you limit and offset params to paginate.
```java
DefectService.Filter filter = qaseApi.defects().filter().status(open);
Defects defects = qaseApi.defects().getAll("PRJCODE", 50, 0, filter);
List<Defect> defectList = defects.getDefectList();
```

#### Get a specific defect ####
This method allows to retrieve a specific defect.
```java
Defect defect = qaseApi.defects().get("PRJCODE", 1);
```

#### Resolve ####
This method is used to resolve defect through API.
```java
boolean isResolved = qaseApi.defects().resolve("PRJCODE", 1);
```

#### Delete defect ####
This method completely deletes a defect from repository
```java
boolean isDeleted = qaseApi.defects().delete("PRJCODE", 1);
```

### Custom Fields ###

#### Get all custom fields ####
This method allows to retrieve all custom fields for a specific project. You can you limit and offset params to paginate.
```java
CustomFields customFields = qaseApi.customFields().getAll("PRJCODE");
List<CustomField> customFieldList = customFields.getCustomFieldList();
```

#### Get a specific custom field ####
This method allows to retrieve one custom fields for specific project by id
```java
CustomField customField = qaseApi.customFields().get("PRJCODE", 1);
```

### Attachments ###

#### Get all attachments ####
This method allows to retrieve all attachments uploaded into your projects. You can you limit and offset params to paginate.

```java
Attachments attachments = qaseApi.attachments().getAll();
List<Attachment> attachmentList = attachments.getAttachmentList();
```

#### Get a specific attachment ####
This method allows to retrieve a specific attachment by hash.

```java
Attachment attachment = qaseApi.attachments().get("6676b8815da03124dc039d89cc111586a4f45dc9");
```

#### Upload attachment ####
This method allows to upload attachment to Qase. Max upload size: * Up to 32 Mb per file.
```java
File file = new File("1.png");
List<Attachment> attachment = qaseApi.attachments().add("PRJCODE", file);
```

#### Delete attachment ####
This method completely deletes an attachment.
```java
boolean isDeleted = qaseApi.attachments().delete("6676b8815da03124dc039d89cc111586a4f45dc9");
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

### Team ###

#### Get all team members ####
This method allows to retrieve all users in your team. You can you limit and offset params to paginate.

```java
Users users = qaseApi.team().getAll();
List<User> userList = users.getUserList();
```

#### Get a specific team member
This method allows to retrieve a specific team member by id.

```java
User user = qaseApi.team().get(1);
```
