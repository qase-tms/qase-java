# [Qase TestOps](https://qase.io) JUnit 4 Reporter

[![License](https://img.shields.io/badge/License-Apache_2.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/io.qase/qase-junit4-reporter)](https://mvnrepository.com/artifact/io.qase/qase-junit4-reporter)

The Qase JUnit 4 Reporter enables seamless integration between your JUnit 4 tests and [Qase TestOps](https://qase.io), providing automatic test result reporting, test case management, and comprehensive test analytics.

## Features

- Link automated tests to Qase test cases by ID
- Auto-create test cases from your test code
- Report test results with rich metadata (fields, attachments, steps)
- Support for parameterized tests
- Flexible configuration (file, environment variables, system properties)
- Real-time reporting with batched uploads

## Installation

### Maven

Add the following dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>io.qase</groupId>
    <artifactId>qase-junit4-reporter</artifactId>
    <version>4.1.32</version>
    <scope>test</scope>
</dependency>
```

### Gradle

Add the following to your `build.gradle`:

```groovy
testImplementation 'io.qase:qase-junit4-reporter:4.1.32'
```

> **Note:** The reporter requires AspectJ weaver for step functionality. See the [examples directory](../examples/junit4/) for complete Maven and Gradle setup including AspectJ configuration.

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

**2. Add Qase ID to your test:**

```java
package com.example;

import io.qase.commons.annotation.QaseId;
import io.qase.commons.annotation.QaseTitle;
import org.junit.Test;

public class SampleTest {

    @Test
    @QaseId(1)
    @QaseTitle("Open login page")
    public void testLogin() {
        // your test logic
    }
}
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
      "title": "JUnit 4 Automated Run"
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

Associate your tests with Qase test cases using the `@QaseId` annotation:

```java
@Test
@QaseId(42)
public void testFeature() {
    // ...
}
```

### Add Metadata

Enhance your tests with additional information:

```java
@Test
@QaseId(1)
@QaseTitle("Verify user authentication")
@QaseFields({"priority:high", "layer:api"})
@QaseSuite("Authentication")
public void testAuth() {
    Qase.comment("Testing auth with valid credentials");
    // ...
}
```

### Ignore Tests in Qase

Exclude specific tests from Qase reporting (test still runs, but results are not sent):

```java
@Test
@QaseIgnore
public void testNotTracked() {
    // ...
}
```

### Test Result Statuses

| JUnit 4 Result | Qase Status | Description |
|----------------|-------------|-------------|
| Passed | passed | Test completed successfully |
| Failed (assertion) | failed | Test failed due to assertion error (`AssertionError`, `assertEquals`, etc.) |
| Failed (other) | invalid | Test failed due to non-assertion error (`NullPointerException`, `RuntimeException`, etc.) |
| Skipped | skipped | Test was skipped or ignored |

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

- **JUnit 4**: Version 4.13.1 or higher
- **Java**: Version 1.8 or higher

## Documentation

| Guide | Description |
|-------|-------------|
| [Usage Guide](docs/usage.md) | Complete usage reference with all annotations and options |
| [Attachments](docs/ATTACHMENTS.md) | Adding screenshots, logs, and files to test results |
| [Steps](docs/STEPS.md) | Defining test steps for detailed reporting |
| [Upgrade Guide](docs/UPGRADE.md) | Migration guide from v3 to v4 |
| [Configuration Reference](../qase-java-commons/README.md#configuration) | Full configuration options reference |

## Examples

See the [examples directory](../examples/junit4/) for complete working examples with Maven and Gradle.

## License

Apache License 2.0. See [LICENSE](../LICENSE) for details.
