# Qase TMS TestNG Reporter

Publish your test results easily and effectively with Qase TMS.

## Installation

To install the latest release version (4.0.x), follow the instructions below for Maven and Gradle.

### Maven

Add the following dependency to your `pom.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
   <modelVersion>4.0.0</modelVersion>
   <groupId>your.group.id</groupId>
   <artifactId>your-artifact-id</artifactId>
   <version>1.0-SNAPSHOT</version>
   <properties>
      <aspectj.version>1.9.22</aspectj.version>
   </properties>

   <dependencies>
      <dependency>
         <groupId>io.qase</groupId>
         <artifactId>qase-testng-reporter</artifactId>
         <version>4.0.0</version>
         <scope>test</scope>
      </dependency>
      <dependency>
         <groupId>org.testng</groupId>
         <artifactId>testng</artifactId>
         <version>7.1.0</version>
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
</project>
```

### Gradle

Add the following dependencies to your `build.gradle`:

```groovy
configurations {
   aspectjweaver
}

tasks.withType(JavaCompile).configureEach {
   // Enables the adapter to accept real parameter names
   options.compilerArgs.add("-parameters")
}

dependencies {
   aspectjweaver "org.aspectj:aspectjweaver:1.9.22"
   testImplementation 'io.qase:qase-testng-reporter:4.0.0'
   testImplementation 'org.testng:testng:7.1.0'
}

test {
   systemProperties = System.properties
}

test.doFirst {
   useTestNG()
   def weaver = configurations.aspectjweaver.find { it.name.contains("aspectjweaver") }
   jvmArgs += "-javaagent:$weaver"
}
```

## Updating from v3 to v4

To update an existing project using the Qase reporter from version 3 to version 4, follow these steps:

1. **Change Import Paths**: Update your test files to change the import paths:
   ```diff
   - import io.qase.api.annotation.*;
   + import io.qase.commons.annotation.*;
   ```

2. **Update Reporter Configuration**: Modify your `qase.config.json` and/or environment variables. For more information,
   see the [Configuration Reference](#configuration) below.

## Getting Started

The TestNG reporter can auto-generate test cases and suites based on your test data. Test results from subsequent runs
will match the same test cases as long as their names and file paths remain unchanged.

You can also annotate tests with IDs of existing test cases from Qase.io before execution. This approach ensures a
reliable binding between your automated tests and test cases, even if you rename, move, or parameterize your tests.

### Metadata Annotations

- **`QaseId`**: Set the ID of the test case.
- **`QaseTitle`**: Set the title of the test case.
- **`QaseFields`**: Set custom fields for the test case.
- **`QaseSuite`**: Specify the suite for the test case.
- **`QaseIgnore`**: Ignore the test case in Qase. The test will execute, but results won't be sent to Qase.
- **`Qase.comment`**: Add a comment to the test case.
- **`Qase.attach`**: Attach a file to the test case.

For detailed instructions on using annotations and methods, refer to [Usage](./docs/usage.md).

### Example Test Case

Here’s a simple example of using Qase annotations in a TestNG test:

```java
package org.example;

import io.qase.commons.annotation.*;
import io.qase.testng.Qase;
import org.testng.annotations.Test;

public class SimpleTests {

    @Test
    @QaseId(1)
    @QaseTitle("Example Test")
    public void test() {
        System.out.println("Running example test");
    }
}
```

To execute your TestNG tests and report the results to Qase.io, use the following commands:

#### Maven

```bash
mvn clean test
```

#### Gradle

```bash
gradle test
```

You can try it with the example project at [`examples/testng`](../examples/testng/).

<p align="center">
  <img width="65%" src="./screenshots/screenshot.png" alt="Qase TestNG Screenshot">
</p>

After running the tests, results will be available at:

```
https://app.qase.io/run/QASE_PROJECT_CODE
```

## Configuration

The Qase TestNG reporter can be configured in multiple ways:

- **Configuration File**: Use a separate config file `qase.config.json`.
- **Environment Variables**: These override values from the configuration file.
- **CLI Arguments**: CLI arguments take precedence over both configuration files and environment variables.

For a complete list of configuration options, refer to
the [Configuration Reference](../qase-java-commons/README.md#configuration).

### Example `qase.config.json`

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
         "title": "Regression Run",
         "description": "Description of the regression run",
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

- **TestNG**: Version 7.0.0 or higher is required.
- **Java**: Version 1.8 or higher is required.

For further assistance, please refer to
the [Qase Authentication Documentation](https://developers.qase.io/#authentication).
