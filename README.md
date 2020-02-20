# [Qase TMS](https://qase.io) Java Api Client
[![Download](https://api.bintray.com/packages/qase/maven/io.qase.qase-api/images/download.svg?version=0.0.1) ](https://bintray.com/qase/maven/io.qase.qase-api/0.0.1/link)
[![License](https://lxgaming.github.io/badges/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

## Maven ##

Add the following dependency and repository to your pom.xml:
```xml
<dependency>
    <groupId>io.qase</groupId>
    <artifactId>qase-api</artifactId>
    <version>1.0.0</version>
</dependency>
```


```xml
<repository>
    <id>io.qase</id>
    <url>https://dl.bintray.com/qase/maven/</url>
</repository>
```

## Usage ##
Qase.io uses API tokens to authenticate requests. You can view an manage your API keys in [API tokens pages](https://app.qase.io/user/api/token).

You must replace api_token with your personal API key.
 
```java
QaseApi qaseApi = new QaseApi("api_token")
```

### Projects ###

#### Get All Projects ####
This method allows to retrieve all projects available for your account. You can you limit and offset params to paginate.

```java
Projects projects = qaseApi.projects().getAll();
List<Project> projectList = projects.getProjectList();
```

#### Get All Projects ####
This method allows to retrieve a specific project.

```java
Project project = qaseApi.projects().get("PRJCODE");
```

#### Create a new project ####
This method is used to create a new project through API.

```java
String projectCode = qaseApi.projects().create("PRJCODE", "Project description");
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
### Test plans ###
### Test runs ###
### Test run results ###
### Defects ###
### Custom Fields ###
### Attachments ###
### Team ###
``