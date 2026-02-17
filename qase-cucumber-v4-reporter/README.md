# [Qase TestOps](https://qase.io) Cucumber 4 Reporter

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/io.qase/qase-cucumber-v4-reporter)](https://mvnrepository.com/artifact/io.qase/qase-cucumber-v4-reporter)

The Qase Cucumber 4 Reporter enables seamless integration between your Cucumber 4 tests and [Qase TestOps](https://qase.io), providing automatic test result reporting, test case management, and comprehensive test analytics.

## Features

- Link automated tests to Qase test cases by ID
- Auto-create test cases from your test code
- Report test results with rich metadata (fields, attachments, steps)
- Support for parameterized scenarios
- Flexible configuration (file, environment variables, system properties)
- Real-time reporting with batched uploads

## Installation

### Maven

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>io.qase</groupId>
    <artifactId>qase-cucumber-v4-reporter</artifactId>
    <version>4.1.31</version>
    <scope>test</scope>
</dependency>
```

### Gradle

Add the following to your `build.gradle`:

```groovy
testImplementation 'io.qase:qase-cucumber-v4-reporter:4.1.31'
```

> **Note:** The reporter requires AspectJ weaver for step functionality. See the [examples directory](../examples/cucumber4/) for complete Maven and Gradle setup including Cucumber runner and AspectJ configuration.

## Quick Start

**1. Create `qase.config.json` in your project root:**

```json
{
  "mode": "testops",
  "testops": {
    "project": "YOUR_PROJECT_CODE",
    "api": {
      "token": "YOUR_API_TOKEN"
    }
  }
}
```

**2. Add Qase ID to your scenario:**

```gherkin
Feature: Login

  @QaseId=1
  Scenario: Successful login
    Given the user is on the login page
    When the user enters valid credentials
    Then the user is redirected to the dashboard
```

**3. Run your tests:**

```bash
mvn clean test
```

## Configuration

The reporter is configured via (in order of priority):

1. **System properties** (`-Dqase.*`, highest priority)
2. **Environment variables** (`QASE_*`)
3. **Config file** (`qase.config.json`)

### Minimal Configuration

| Option | Environment Variable | Description |
|--------|---------------------|-------------|
| `mode` | `QASE_MODE` | Set to `testops` to enable reporting |
| `testops.project` | `QASE_TESTOPS_PROJECT` | Your Qase project code |
| `testops.api.token` | `QASE_TESTOPS_API_TOKEN` | Your Qase API token |

### Example `qase.config.json`

```json
{
  "mode": "testops",
  "fallback": "report",
  "testops": {
    "project": "YOUR_PROJECT_CODE",
    "api": {
      "token": "YOUR_API_TOKEN"
    },
    "run": {
      "title": "Cucumber 4 Automated Run"
    },
    "batch": {
      "size": 100
    }
  },
  "report": {
    "driver": "local",
    "connection": {
      "local": {
        "path": "./build/qase-report",
        "format": "json"
      }
    }
  }
}
```

> **Full configuration reference:** See [qase-java-commons](../qase-java-commons/README.md#configuration) for all available options including logging, status mapping, execution plans, and more.

## Usage

### Link Tests with Test Cases

Associate your scenarios with Qase test cases using the `@QaseId` tag:

```gherkin
@QaseId=42
Scenario: Verify checkout flow
  Given the user has items in the cart
  When the user proceeds to checkout
  Then the order is placed successfully
```

### Add Metadata

Enhance your scenarios with additional information:

```gherkin
@QaseId=1
@QaseTitle=Verify_user_authentication
@QaseFields:{"priority":"high","layer":"api"}
Scenario: User login
  Given the user is on the login page
  When the user enters valid credentials
  Then the user is logged in
```

### Ignore Tests in Qase

Exclude specific scenarios from Qase reporting (test still runs, but results are not sent):

```gherkin
@QaseIgnore
Scenario: Not tracked in Qase
  Given some precondition
  Then some result
```

### Test Result Statuses

| Cucumber Result | Qase Status | Description |
|----------------|-------------|-------------|
| PASSED | passed | Scenario completed successfully |
| FAILED (assertion) | failed | Step failed due to assertion error (`AssertionError`, `assertEquals`, etc.) |
| FAILED (other) | invalid | Step failed due to non-assertion error (`NullPointerException`, `RuntimeException`, etc.) |
| SKIPPED | skipped | Scenario was skipped |
| PENDING | skipped | Step definition is pending |
| UNDEFINED | invalid | Step definition is missing |

> For more usage examples, see the [Usage Guide](docs/usage.md).

## Running Tests

### Maven

```bash
mvn clean test
```

### Gradle

```bash
gradle test
```

## Requirements

- **Cucumber**: Version 4.8.1 or higher
- **Java**: Version 1.8 or higher

## Documentation

| Guide | Description |
|-------|-------------|
| [Usage Guide](docs/usage.md) | Complete usage reference with all tags and options |
| [Attachments](docs/ATTACHMENTS.md) | Adding screenshots, logs, and files to test results |
| [Steps](docs/STEPS.md) | Defining test steps for detailed reporting |
| [Upgrade Guide](docs/UPGRADE.md) | Migration guide from v3 to v4 |
| [Configuration Reference](../qase-java-commons/README.md#configuration) | Full configuration options reference |

## Examples

See the [examples directory](../examples/cucumber4/) for complete working examples with Maven and Gradle.

## License

Apache License 2.0. See [LICENSE](../LICENSE) for details.
