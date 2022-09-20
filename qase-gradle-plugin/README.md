# This is a Gradle support plugin for Qase application

## Exposed tasks

### `qaseTest`
  - Either (1) delegates execution to the `java` plugin of Gradle or (2) runs a test-plan scoped subset of `@CaseId`-annotated methods.
  - Requires the `java` plugin of Gradle to be present during the build.
  - If `QASE_RUN_ID` variable is not specified and `QASE_TEST_PLAN_ID` is present among the environment variables, a test-plan scoped run is triggered.
  - Additional environmental variables and test framework options are still required to be specified additionally.
