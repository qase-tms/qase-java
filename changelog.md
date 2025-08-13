# qase-java 4.1.15

## What's new

Updated API client to the latest specification

# qase-java 4.1.14

## Bug fixes

- Fixed JUnit5 PostDiscoveryFilter instantiation error: `[ERROR] org.junit.platform.launcher.PostDiscoveryFilter: Provider io.qase.junit5.Junit5PostDiscoveryFilter could not be instantiated`

# qase-java 4.1.13

## What's new

Updated API client to the latest specification

# qase-java 4.1.12

## What's new

Added support for configurations in test runs. You can now specify configuration groups and values that will be associated with your test runs. This includes:

- JSON configuration format with `values` array and `createIfNotExists` flag
- Environment variable support: `QASE_TESTOPS_RUN_CONFIGURATIONS="browser=chrome,environment=staging"`
- Environment variable support for createIfNotExists: `QASE_TESTOPS_CONFIGURATIONS_CREATE_IF_NOT_EXISTS="true"`
- System property support: `-DQASE_TESTOPS_RUN_CONFIGURATIONS="browser=firefox,os=linux"`
- System property support for createIfNotExists: `-DQASE_TESTOPS_CONFIGURATIONS_CREATE_IF_NOT_EXISTS="true"`
- Automatic creation of configuration groups and values when `createIfNotExists` is true

# qase-java 4.1.11

## What's new

Updated API client to the latest version.

# qase-java 4.1.10

## What's new

Fixed a link to failed test in the console output.

# qase-java 4.1.9

## What's new

Resolved issue with handling of `null` values in test case IDs in Cucumber reporters.

# qase-java 4.1.8

## What's new

Added support tags for test runs.

# qase-java 4.1.7

## What's new

Added support `in-progress` status for test steps in API clients.

# qase-java 4.1.6

## What's new

Fixed a JSON parsing error (java.io.IOException) in qase-api-client when handling TestCaseParams with anyOf schemas.

# qase-java 4.1.5

## What's new

Added support for BDD keywords (Given, When, Then, And, ect.) in Cucumber reporters for better step representation.

# qase-java 4.1.4

## What's new

Added support for step parameters and table arguments in Cucumber reporters. Step definitions with arguments and
tabular data are now correctly captured and displayed in test results.

# qase-java 4.1.3

## What's new

- Resolved issue with serialization of models containing fields not described in the OpenAPI specification.
- Unspecified fields are now properly handled and stored in `additionalProperties` to ensure compatibility with dynamic payloads.

# qase-java 4.1.2

## What's new

- Resolved issue with handling of nested (child) suites in Cucumber reporters
- Enhanced logging to provide more detailed and clear output during test execution

# qase-java 4.1.1

## What's new

- Removed dependency on `slf4j` and `logback`, replaced with custom internal logger
- Updated dependencies to fix known security issues

# qase-java 4.1.0

## What's new

- Updated API clients to the latest supported versions.
- Improved logic for handling multiple QaseID values in test results.
- Removed `useV2` configuration option. The reporter now always uses API v2 for sending results.

# qase-java 4.0.12

## What's new

- Logging of host system details to improve debugging and traceability.
- Output of installed packages in logs for better environment visibility.

# qase-java 4.0.11

## What's new

Implemented local time support for test run creation to improve time tracking.

# qase-java 4.0.10

## What's new

Introduced the ability to execute custom hooks before a test completes, allowing users to run custom code at the end of
test execution. More details can be found in
the [documentation](https://github.com/qase-tms/qase-java/tree/main/qase-java-commons#readme)

# qase-java 4.0.9

## What's new

Resolved an issue where `execution.start_time` was not in UTC, leading to submission errors (
`The execution.start time must be at least {unix time}`).

# qase-java 4.0.8

## What's new

Added a new `attach` method in Cucumber reporters that allows passing a byte array to attach files to test results.

# qase-java 4.0.7

## What's new

Resolved PatternSyntaxException in Cucumber reporters caused by unescaped trailing backslash in regular expressions.

# qase-java 4.0.6

## What's new

- Resolved issues with listener connection when using aspects and Maven integration in Junit4.
- Updated documentation to guide proper listener setup.

# qase-java 4.0.4

## What's new

Implemented support for running only the tests included in a test plan for Junit5 reporter.  
To enable this feature, specify the test plan ID in the reporter configuration.  
You can set it either in the `qase.config.json` file or through the `QASE_TESTOPS_PLAN_ID` environment variable.

# qase-java 4.0.3

## What's new

Implemented support for running only the tests included in a test plan for TestNG reporter.  
To enable this feature, specify the test plan ID in the reporter configuration.  
You can set it either in the `qase.config.json` file or through the `QASE_TESTOPS_PLAN_ID` environment variable.
