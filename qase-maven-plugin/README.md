# This is a Maven support plugin for Qase application

## Exposed goals

### `qase:qaseTest`
- Either (1) directly delegates execution to the `maven-surefire-plugin` or (2) runs a test-plan scoped subset of `@CaseId`-annotated test methods/scenarios.
- If `QASE_RUN_ID` variable is not specified and `QASE_TEST_PLAN_ID` is present among the environment variables, a test-plan scoped run is triggered.
- Additional environmental variables and test framework options are still required to be specified additionally.
- Should be run after the tests are compiled.
