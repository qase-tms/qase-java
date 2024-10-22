# Qase TMS JUnit5 Reporter

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
      <maven.compiler.source>1.8</maven.compiler.source>
      <maven.compiler.target>1.8</maven.compiler.target>
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
               <!-- This section is required for the proper functioning of the reporter. -->
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
   // This line is required for the proper functioning of the reporter.
   systemProperty "junit.jupiter.extensions.autodetection.enabled", true
   def weaver = configurations.aspectjweaver.find { it.name.contains("aspectjweaver") }
   jvmArgs += "-javaagent:$weaver"
}
```

## Updating from v3 to v4

To update an existing test project using the Qase reporter from version 3 to version 4, follow these steps:

1. **Change Import Paths**: Update your test files to change the import paths:
   ```diff
   - import io.qase.api.annotation.*;
   + import io.qase.commons.annotation.*;
   ```

2. **Update Reporter Configuration**: Modify your `qase.config.json` and/or environment variables. For more information,
   see the [Configuration Reference](#configuration) below.

## Getting Started

The JUnit5 reporter can auto-generate test cases and suites based on your test data. Test results from subsequent runs
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

Here’s a simple example of using Qase annotations in a JUnit5 test:

```java
package org.example;

import io.qase.commons.annotation.*;
import org.junit.jupiter.api.Test;

public class SimpleTests {

    @Test
    @QaseId(1)
    @QaseTitle("Example Test")
    public void test() {
        System.out.println("Running example test");
    }
}
```

To execute your JUnit5 tests and report the results to Qase.io, use the following commands:

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
  <img width="65%" src="./screenshots/screenshot.png" alt="Qase JUnit5 Screenshot">
</p>

After running the tests, results will be available at:

```
https://app.qase.io/run/QASE_PROJECT_CODE
```

## Configuration

The Qase JUnit5 reporter can be configured in multiple ways:

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

- **JUnit5**: Version 5.0.0 or higher is required.
- **Java**: Version 1.8 or higher is required.

For further assistance, please refer to
the [Qase Authentication Documentation](https://developers.qase.io/#authentication).
