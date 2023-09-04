# Qase TMS Java Integrations #
[![License](https://lxgaming.github.io/badges/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

## Description ##
The repository contains Java Libraries for the Qase TMS integration

## Usage

### Configuration

Use the following options to configure integration:

|          Key          |  Type   |                          Description                          |
|:---------------------:|:-------:|:-------------------------------------------------------------:|
|      QASE_ENABLE      | boolean |                         Use Qase TMS                          |
|   QASE_PROJECT_CODE   | string  |                   Project Code in Qase TMS                    |
|      QASE_RUN_ID      | integer |                    Test Run ID in Qase TMS                    |
|    QASE_API_TOKEN     | string  |                    API Token for Qase TMS                     |
|     QASE_USE_BULK     | boolean |                 Use Bulk Send (default: true)                 |
|     QASE_RUN_NAME     | string  |    Name of the new Test Run (only if QASE_RUN_ID not set)     |
| QASE_RUN_DESCRIPTION  | string  | Description of the new Test Run (only if QASE_RUN_ID not set) |
| QASE_RUN_AUTOCOMPLETE | boolean |           Complete test run after passing autotests           |

All options could be provided by both system properties and environment variables.

For example, you can provide options by system properties using CLI:

```bash
mvn clean test -DQASE_ENABLE=true -DQASE_PROJECT_CODE=PRJ -DQASE_RUN_ID=123 -DQASE_API_TOKEN=secret-token
```

### Link autotests with test-cases

To link tests with test-cases in Qase TMS you should use annotation `@io.qase.api.annotation.QaseId`:

```java
@Test
@CaseId(123)
public void someTest(){
        ...
        }
```

### TestCase as a Code

For using Test Case as a Code, you could mark your test by annotation `@io.qase.api.annotation.QaseTitle`:

```java
@Test
@CaseTitle("Case Title")
public void someTest(){
        steps.someStep1();
        steps.someStep2();
        }
```

The steps of the test case you can mark by annotation `@io.qase.api.annotation.Step`:

```java
@Step("Some step1")
public void someStep1(){
        // do something
        }

@Step("Some step2")
public void someStep2(){
        // do something
        }
```

You can add the method argument value to the step name by using the argument name in the placeholder:

```java
@Step("Step {arg1} and {arg2}")
public void step(String arg1,int arg2){
        // do something
        }
```

After the test run is completed, a test case will be created if it did not already exist.

### Sending tests to existing Test Run in Qase TMS

Test Run in TMS will contain only those test results, which are presented in testrun:

```bash
mvn clean test \
      -DQASE_ENABLE=true \
      -DQASE_PROJECT_CODE=PRJ \ # project, where your testrun exists in
      -DQASE_RUN_ID=123 \ # testrun id
      -DQASE_API_TOKEN=<your api token here>
```

### Creating Test Run in Qase TMS base on Autotest's test run

```bash
mvn clean test \
      -DQASE_ENABLE=true \
      -DQASE_PROJECT_CODE=PRJ \ # the project where your test run will be created
      -DQASE_RUN_NAME=NEW_RUN_NAME \ # name of new test run creating in Qase TMS
      -DQASE_RUN_DESCRIPTION=NEW_RUN_DESCRIPTION \ # description of new test run creating in Qase TMS
      -DQASE_API_TOKEN=<your api token here>
```
