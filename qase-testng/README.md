# Qase TMS TestNG Integration #

[![License](https://lxgaming.github.io/badges/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

## Description ##

This integration uploads test run results to Qase TMS via API.

## Usage

### Maven

Add the following dependency to your pom.xml:

```xml

<properties>
    <aspectj.version>1.9.8</aspectj.version>
</properties>

<dependency>
    <groupId>io.qase</groupId>
    <artifactId>qase-testng</artifactId>
    <version>2.1.5</version>
    <scope>test</scope>
</dependency>

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
```

### Gradle

Add the following dependency to build.gradle:

```groovy
configurations {
    aspectjweaver
}

dependencies {
    aspectjweaver "org.aspectj:aspectjweaver:1.9.8"
    testImplementation 'io.qase:qase-testng:2.1.1'
}

test.doFirst {
    jvmArgs "-javaagent:${configurations.aspectjweaver.singleFile}"
}
```

### Configuration

Use the following options to configure integration:

|          Key          |  Type   |                          Description                          |
|:---------------------:|:-------:|:-------------------------------------------------------------:|
|      QASE_ENABLE      | boolean |                         Use Qase TMS                          |
|   QASE_PROJECT_CODE   | string  |                   Project Code in Qase TMS                    |
|      QASE_RUN_ID      | integer |                    Test Run ID in Qase TMS                    |
|    QASE_API_TOKEN     | string  |                    API Token for Qase TMS                     |
|     QASE_USE_BULK     | boolean |                 Use Bulk Send (default: true)                 |
|     QASE_RUN_NAME     | string  |    Name of the new Test Run (only if QASE_RUN_ID not set)     |
| QASE_RUN_DESCRIPTION  | string  | Description of the new Test Run (only if QASE_RUN_ID not set) |
| QASE_RUN_AUTOCOMPLETE | boolean |           Complete test run after passing autotests           |

All options could be provided by both system properties and environment variables.

For example, you can provide options by system properties using CLI:

```bash
mvn clean test -DQASE_ENABLE=true -DQASE_PROJECT_CODE=PRJ -DQASE_RUN_ID=123 -DQASE_API_TOKEN=secret-token
```

### Link autotests with test-cases

To link tests with test-cases in Qase TMS you should use annotation `@io.qase.api.annotation.CaseId`:

```java
@Test
@CaseId(123)
public void someTest(){
        ...
        }
```

### TestCase as a Code

For using Test Case as a Code, you could mark your test by annotation `@io.qase.api.annotation.CaseTitle`:

```java
@Test
@CaseTitle("Case Title")
public void someTest(){
        steps.someStep1();
        steps.someStep2();
        }
```

The steps of the test case you can mark by annotation `@io.qase.api.annotation.Step`:

```java
@Step("Some step1")
public void someStep1(){
        // do something
        }

@Step("Some step2")
public void someStep2(){
        // do something
        }
```

You can add the method argument value to the step name by using the argument name in the placeholder:

```java
@Step("Step {arg1} and {arg2}")
public void step(String arg1,int arg2){
        // do something
        }
```

After the test run is completed, a test case will be created if it did not already exist.

### Sending tests to existing Test Run in Qase TMS

Test Run in TMS will contain only those test results, which are presented in testrun:

```bash
mvn clean test \
      -DQASE_ENABLE=true \
      -DQASE_PROJECT_CODE=PRJ \ # project, where your testrun exists in
      -DQASE_RUN_ID=123 \ # testrun id
      -DQASE_API_TOKEN=<your api token here>
```

### Creating Test Run in Qase TMS base on Autotest's test run

```bash
mvn clean test \
      -DQASE_ENABLE=true \
      -DQASE_PROJECT_CODE=PRJ \ # the project where your test run will be created
      -DQASE_RUN_NAME=NEW_RUN_NAME \ # name of new test run creating in Qase TMS
      -DQASE_RUN_DESCRIPTION=NEW_RUN_DESCRIPTION \ # description of new test run creating in Qase TMS
      -DQASE_API_TOKEN=<your api token here>
```
