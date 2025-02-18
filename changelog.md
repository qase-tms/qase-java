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
