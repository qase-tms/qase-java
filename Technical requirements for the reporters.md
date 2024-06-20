# Technical requirements for the reporters

## The repository should have the following folders

- `qase-api-client` - the client for the Qase API v1
- `qase-api-v2-client` - the client for the Qase API v2
- `qase-java-commons` - the common classes for the Qase reporters
- `qase-junit5-reporter` - the reporter for JUnit 5
- `qase-testng-reporter` - the reporter for TestNG
- `qase-junit4-reporter` - the reporter for JUnit 4
- `qase-cucumber-v7-reporter` - the reporter for Cucumber v7
- ...

## The commons package should contain the following classes

- `QaseApiV1Client` - the client for the Qase API v1
- `QaseApiV2Client` - the client for the Qase API v2
- `QaseReporter` - the common reporter for all frameworks. Facade for the specific reporters, like QaseTestopsReporter, QaseFileReporter, etc.
- `QaseTestopsReporter` - the reporter for TestOps. Uses the QaseApiV1Client or QaseApiV2Client
- `QaseFileReporter` - the reporter for the file. Writes the results to the file in the specified format

## The reporters should have the same config. The commons package should manage configuration for all reporters

Example of the config:

```json
{
 "mode": "testops",
 "fallback": "report",
 "environment": "local",
 "report": {
  "driver": "local",
  "connection": {
   "local": {
    "path": "./build/qase-report",
    "format": "json"
   }
  }
 },
 "testops": {
  "api": {
   "token": "token",
   "host": "qase.io"
  },
  "run": {
   "title": "Java run",
   "description": "Java run description",
   "complete": true
  },
  "defect": true,
  "project": "project_code",
  "batch": {
   "size": 10
  },
  "useV2": false
 }
}
```

You can read about the config in the [Qase config documentation](https://github.com/qase-tms/qase-python/blob/main/qase-pytest/docs/CONFIGURATION.md)

## Main features of the common reporter

### Mode

If the `mode` is `testops`, the reporter should send the results to the TestOps.
If the `mode` is `report`, the reporter should write the results to the file.

If we can't send the results to the main reporter (for example, QaseTestopsReporter), we should write the results to the fallback reporter(for example, QaseFileReporter).

### Debug mode

If the `debug` is `true`, the reporter should print the debug information to the console and the file. The file should be in the `./build/qase-debug-{datetime}.log` path. The debug information should contain the request and response data on each level of the reporter.

### Batch and parallel upload

The reporter should support the batch and parallel upload. The `batch.size` parameter should define the size of the batch.
The reporter should send the results in parallel.

### QaseFileReporter format

The reporter should write the results to the file in the specified format. The format should be defined in the `report.connection.local.format` parameter. The reporter should support the following formats:

- json
- jsonp

You can read about the formats in the [Qase spec](https://github.com/qase-tms/specs/tree/master/report)

## The reporters should have the following features

### Steps

Each reporter should support the steps and nested steps.
We can use annotations or tags to define the steps, like `@QaseStep`. This annotation should contain the step title.

### QaseId

Each reporter should support the QaseId. The QaseId should be defined in the `@QaseId` annotation or tag. Also, the reporter should support the old format of the QaseId.

### QaseTitle

Each reporter should support the QaseTitle. The QaseTitle should be defined in the `@QaseTitle` annotation or tag.

### QaseFields

Each reporter should support the QaseFields. The QaseFields should be defined in the `@QaseFields` annotation or tag.
This annotation should contain the key-value pairs.

### QaseIngore

Each reporter should support the QaseIgnore. The QaseIgnore should be defined in the `@QaseIgnore` annotation or tag.
If the test has the QaseIgnore annotation, the reporter should skip result of the test.

### QaseAttach

Each reporter should support the QaseAttach. The QaseAttach should be defined in the `QaseAttach` method.
This method should contain the path or paths to the files and create the attachments from strings. The attachments can be attached to the test or the step.

### Parametrize tests

Each reporter should support the parametrize tests.
The reporter should collect the parameters and the values as key-value pairs.

### Signature

Each reporter should support the signature. It is a unique identifier for the test.
The signature should be collected automatically and be unique for each test. 

Signature should be in lower case and without spaces (replace to `_`).

The signature should contain the following information:
- the test package, for example: `com/qase/tests/MyTest.java` -> `com::qase::tests::mytest.java`
- the test class name, for example: `mytest`
- the test method name, for example: `testmethod`
- the qase id if it is defined, for example: `123`
- the parameters if they are defined, for example: `{param1:value1}`

Example of the signature: `com::qase::tests::mytest.java::mytest::testmethod::123::{param1:value1}::{param2::value2}`

