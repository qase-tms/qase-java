# qase-java 4.1.42

## Bug fixes

- Fixed NPE in TestNG and JUnit4 reporters when `CasesStorage.getCurrentCase()` returns null due to ThreadLocal thread mismatch under concurrent execution
  - Added `CasesStorage.isCaseInProgress()` guard in `stopTestCase()` — same pattern already used in JUnit5 and Cucumber reporters
  - Prevents `SurefireBooterForkException` crash when running large test suites (~220+ tests) with Maven Surefire

# qase-java 4.1.41

## What's new

- Refactored all Cucumber v4-v7 QaseEventListeners to extend shared `AbstractCucumberEventListener` base class in commons — eliminates ~200 lines of duplicated event handling code
- Created `CucumberTestCaseAdapter` interface and version-specific adapters (V3-V7) for uniform test case data extraction
- Extracted shared utilities into commons: `TestResultBuilder.fromCucumber()`, `TestResultCompletion`, `AnnotationReader`, `DescriptionAnnotationReader`, `IntegrationUtils.detectFrameworkVersion()`
- Refactored JUnit4, JUnit5, and TestNG QaseListeners to use shared commons utilities
- Fixed NPE in Cucumber reporters when `CasesStorage.stopCase()` was called before `TestResultCompletion.complete()`

# qase-java 4.1.40

## What's new

- Added per-batch upload progress logging at INFO level — each batch reports file count, bytes, elapsed time, and retry count
- Added run-completion upload summary at INFO level — total results, attachments, bytes, upload time, and failed batch count
- Added parallel attachment batch upload via configurable thread pool (`batch.threads` in `qase.config.json`, default: 1)
- Added disk-backed attachment staging — large `contentBytes` are written to temp files and released from memory immediately
- Added dynamic upload timeout — scales with total attachment size (1 MB/s baseline) instead of fixed timeout
- Added batch error isolation — a single failed batch no longer aborts the entire upload, other batches continue
- Added graceful attachment degradation — on upload failure, test results are re-sent without attachments to prevent data loss
- Sanitized debug log output to prevent binary content from corrupting logs — `contentBytes` and long `content` fields are replaced with size placeholders

# qase-java 4.1.39

## What's new

- Added configurable upload timeout for batch result uploads via `batch.uploadTimeout` in `qase.config.json` (default: 300 seconds), `QASE_TESTOPS_BATCH_UPLOAD_TIMEOUT` environment variable, or system property — prevents result loss when uploading large attachments

# qase-java 4.1.38

## What's new

- Added configurable HTTP timeout for API clients via `QASE_TESTOPS_API_TIMEOUT_SECONDS` environment variable, system property, or `qase.config.json` — applies connect/read/write timeout to both V1 and V2 clients
- Added HTTP 408 (Request Timeout) to retryable status codes in `RetryHelper`
- Enhanced retry warnings to include attempt number for better debugging
- Documented `CoreReporter.executeWithFallback()` thread safety contract

## Bug fixes

- Fixed `TestopsReporter` deadlock under concurrent test execution — replaced `synchronized` lock with volatile `asyncError` field for atomic error handling
- Fixed `StepStorage` orphaned in-progress step leak — `stopSteps()` now cleans up orphaned step chains before processing results
- Hardened config parsing: non-numeric values for batch size and timeout now fall back to defaults with a warning instead of crashing
- Empty/blank API token and project code are now treated as null (previously caused silent API failures)
- Invalid batch sizes (≤ 0) are now rejected with a warning, using default value of 200

## Dependencies

- Updated AspectJ weaver from 1.9.22 to 1.9.25.1 (fixes JDK 21+ compatibility)
- Updated TestNG in examples to 7.5.1 (Java 8) / 7.10.2 (Java 11) to fix Zip Slip vulnerability

# qase-java 4.1.37

## Bug fixes

- Fixed NPE in TestNG reporter when a test is skipped before `onTestStart` is called (#224)
  - When a test is skipped via `SkipException` thrown in another listener's `onTestStart`, the Qase `onTestStart` is never invoked, leaving `CasesStorage` empty
  - `onTestSkipped` now checks `CasesStorage.isCaseInProgress()` and initializes the test case if needed before processing the skip
- Fixed memory leak in `StepStorage` — `ThreadLocal` entries are now properly cleaned up after each test
- Result uploading is now asynchronous to prevent blocking test execution

# qase-java 4.1.36

## What's new

- Added custom status support for test results (#220)
  - New `customStatus` field on `TestResultExecution` — when set, takes priority over the standard status enum when sending results to the Qase API (e.g. `server_error`, `known_bug`)
  - New `throwable` field on `TestResultExecution` — stores the original exception so `HooksListener` implementations can analyze it programmatically
  - Updated all listeners (TestNG, JUnit4, JUnit5, Cucumber v3–v7) to populate `throwable`
  - Updated `ApiClientV2` and `FileReporter` to use `customStatus` when set

# qase-java 4.1.35

## Bug fixes

- Fixed missing test results when `@BeforeAll` fails in JUnit 5
  - When `@BeforeAll` throws an exception, tests are now reported as SKIPPED with the failure details
  - Uses `TestExecutionListener.executionFinished()` to detect failed class containers and find unstarted test descendants
  - Handles `@Nested` classes (recursive), `@ParameterizedTest`/`@RepeatedTest` containers, and `@QaseIgnore` exclusion
  - Thread-safe tracking via `ConcurrentHashMap`

# qase-java 4.1.34

## Bug fixes

- Fixed test result loss during parallel execution caused by missing retry logic and unflushed buffers
  - Added `RetryHelper` utility with exponential backoff (3 retries: 1s, 3s, 9s) for transient API errors (timeout, 5xx, 429)
  - Wrapped all API calls in `ApiClientV1` and `ApiClientV2` with retry logic to prevent permanent failures on transient errors
  - Fixed `TestopsReporter.completeTestRun()` to flush buffered results before completing the run
  - Added support for `complete=false` flag to skip API run completion while still uploading results

# qase-java 4.1.33

## What's new

- Added thread-safety to CoreReporter and fixed API client issues for parallel test execution
- Added support for status mapping configuration

# qase-java 4.1.32

## What's new

- Brought FileReporter JSON output into full compliance with specs/report schemas:
  - Added `@SerializedName` annotations for snake_case serialization (`start_time`, `end_time`, `cumulative_duration`, `public_id`)
  - Fixed `RunExecution.duration` calculation (removed erroneous `* 1000` multiplication)
  - Changed `host_data` from array of objects to flat JSON object (`Map<String, String>`)
  - Made `SuiteData.publicId` nullable (`Integer` instead of `int`)
  - Renamed `RunStats.broken` to `blocked` and added `invalid` field per spec
  - Added `BLOCKED` to `TestResultStatus` enum
  - Removed non-spec fields: `runId`, `testopsId` (singular), `author` from `ReportResult`; `size` from `ReportAttachment`; `attachments` from step `ReportData`
  - Deleted unused `ReportHostData` class

# qase-java 4.1.31

## What's new

- Fixed an issue where the test run link was not being generated correctly when filtering by status.

# qase-java 4.1.30

## What's new

- Fixed issue with headers in API requests.

# qase-java 4.1.29

## What's new

- Fixed issue with test result cleanup in JUnit5 reporter.

# qase-java 4.1.28

## What's new

- Fixed issue with root suite in reporters.

# qase-java 4.1.25

## What's new

- Improved the upload mechanism for attachments. Now the reporter will upload attachments in batches of 20 files.

# qase-java 4.1.24

## What's new

- Updated API clients to the latest specification

# qase-java 4.1.22

## What's new

- Added support for public report link for test runs.

# qase-java 4.1.21

## What's new

- Added support for logging configuration.

# qase-java 4.1.19

## What's new

- Updated API clients to the latest specification

# qase-java 4.1.18

## What's new

- Added support for external link for test runs.
- Added support for filtering test results by status.
  
# qase-java 4.1.17

## What's new

- Fixed issue with parallel test execution in reporters.
- Support AspectJ assertions for handling test results.

# qase-java 4.1.16

## What's new

- Improved exception handling in reporters to better determine the status of test results.
- Enhanced test result classification: tests now receive `FAILED` status when failing due to assertions (assert, assertEquals, assertTrue, etc.) and `INVALID` status when failing due to other reasons (NullPointerException, RuntimeException, network errors, etc.).
- Updated all reporters (JUnit4, JUnit5, TestNG, Cucumber v3-v7) to use consistent logic for determining test result statuses.

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
