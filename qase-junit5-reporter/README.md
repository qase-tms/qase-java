# Qase TMS Junit5 reporter

Publish results simple and easy.

## Installation

To install the latest release version (4.0.x):

### Maven

Add the following dependency to your pom.xml:

```xml

<properties>
    <aspectj.version>1.9.22</aspectj.version>
</properties>

<dependencies>
<dependency>
    <groupId>io.qase</groupId>
    <artifactId>qase-junit5-reporter</artifactId>
    <version>4.0.0</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.junit</groupId>
    <artifactId>junit-bom</artifactId>
    <version>5.11.2</version>
    <type>pom</type>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-api</artifactId>
    <version>5.11.2</version>
    <scope>test</scope>
</dependency>
<dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter-params</artifactId>
    <version>5.11.2</version>
    <scope>test</scope>
</dependency>
</dependencies>

<build>
<plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>3.0.0-M5</version>
        <configuration>
            <argLine>
                -javaagent:"${settings.localRepository}/org/aspectj/aspectjweaver/${aspectj.version}/aspectjweaver-${aspectj.version}.jar"
            </argLine>
            <properties>
                <configurationParameters>
                    junit.jupiter.extensions.autodetection.enabled = true
                </configurationParameters>
            </properties>
        </configuration>
        <dependencies>
            <dependency>
                <groupId>org.aspectj</groupId>
                <artifactId>aspectjweaver</artifactId>
                <version>${aspectj.version}</version>
            </dependency>
        </dependencies>
    </plugin>
</plugins>
</build>
```

### Gradle

Add the following dependency to build.gradle:

```groovy
configurations {
    aspectjweaver
}

tasks.withType(JavaCompile).configureEach {
    // Allows the adapter to accept real parameter names
    options.compilerArgs.add("-parameters")
}

dependencies {
    aspectjweaver "org.aspectj:aspectjweaver:1.9.22"
    testImplementation platform('org.junit:junit-bom:5.10.0')
    testImplementation 'org.junit.jupiter:junit-jupiter'
    testImplementation 'org.junit.jupiter:junit-jupiter-api'
    testImplementation 'org.junit.jupiter:junit-jupiter-params'
    testImplementation 'org.junit.jupiter:junit-jupiter-engine'
    testImplementation 'org.junit.platform:junit-platform-launcher'
    testImplementation('io.qase:qase-junit5-reporter:4.0.0')
}

test {
    systemProperties = System.properties
}

compileTestJava {
    options.getCompilerArgs().add("-parameters")
}

test.doFirst {
    useJUnitPlatform()
    systemProperty "junit.jupiter.extensions.autodetection.enabled", true
    def weaver = configurations.aspectConfig.find { it.name.contains("aspectjweaver") }
    jvmArgs += "-javaagent:$weaver"
}
```

## Updating from v3 to v4

To update an existing test project using Qase reporter from version 3 to version 4,
run the following steps:

1. Change import paths in your test files:

   ```diff
   - import io.qase.api.annotation.*;
   + import io.qase.commons.annotation.*;
   ```                                        

2. Update reporter configuration in `qase.config.json` and/or environment variables —
   see the [configuration reference](#configuration) below.

## Getting started

The Junit5 reporter can auto-generate test cases
and suites from your test data.
Test results of subsequent test runs will match the same test cases
as long as their names and file paths don't change.

You can also annotate the tests with the IDs of existing test cases
from Qase.io before executing tests. It's a more reliable way to bind
autotests to test cases, that persists when you rename, move, or
parameterize your tests.

### Metadata

- `QaseId` - set the ID of the test case
- `QaseTitle` - set the title of the test case
- `QaseFields` - set the fields of the test case
- `QaseSuite` - set the suite of the test case
- `QaseIgnore` - ignore the test case in Qase. The test will be executed, but the results will not be sent to Qase.

- `Qase.comment` - set the comment of the test case
- `Qase.attach` - attach a file to the test case

You can find instructions on how to use annotations and methods here: [Usage](./docs/usage.md).

For example:

```java
package org.example;

import io.qase.commons.annotation.*;
import io.qase.commons.models.annotation.Field;
import org.junit.jupiter.api.Test;

public class SimpleTests {

    @Test
    @QaseId(1)
    @QaseTitle("Example test")
    public void test() {
        System.out.println("Example test");
    }
}
```

To execute Junit5 tests and report them to Qase.io, run the command:

#### Maven

```bash
mvn clean test
```

#### Gradle

```bash
gradle test
```

You can try it with the example project at [`examples/junit5`](../examples/junit5/).

<p align="center">
  <img width="65%" src="./screenshots/screenshot.png">
</p>

A test run will be performed and available at:

```
https://app.qase.io/run/QASE_PROJECT_CODE
```

## Configuration

Qase Junit5 reporter can be configured in multiple ways:

- using a separate config file `qase.config.json`,
- using environment variables (they override the values from the configuration files).
- using CLI arguments (they override the values from the configuration files and environment variables).

For a full list of configuration options, see
the [Configuration reference](../qase-java-commons/README.md#configuration).

Example `qase.config.json` config:

```json
{
  "mode": "testops",
  "fallback": "report",
  "debug": true,
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
      "token": "<token>",
      "host": "qase.io"
    },
    "run": {
      "title": "Regress run",
      "description": "Regress run description",
      "complete": true
    },
    "defect": false,
    "project": "<project_code>",
    "batch": {
      "size": 100
    }
  }
}
```

## Requirements

`junit5 >= 5.0.0`

<!-- references -->

[auth]: https://developers.qase.io/#authentication
