# Qase TMS Cucumber 3 JVM Integration #
[![License](https://lxgaming.github.io/badges/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

## Description ##
This integration uploads test run results to Qase TMS via API.

To link autotest to test case in Qase TMS use tag `@caseId` or `@tmsLink`

For example:
```
Feature: example features

  @caseId=59
  Scenario: example scenario
    Given example step
    When example step
    Then example step


  @tmsLink=55
  Scenario: example scenario2
    Given example step2
    When example step2
    Then example step2
```

### Required params ###
All required params are passed through system properties or environment variables:

|  Key              | Description |
| :---------------: | :----------: |
| QASE_ENABLE       | Enable Integration |
| QASE_PROJECT_CODE | Project Code |
| QASE_RUN_ID       | Run Id |
| QASE_API_TOKEN    | Qase API Token |

## Maven ##

Add the following dependency and repository to your pom.xml:
```xml
<dependencies>
    <dependency>
        <groupId>io.qase</groupId>
        <artifactId>qase-cucumber3-jvm</artifactId>
        <version>3.0.1</version>
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
                    -javaagent:"${settings.localRepository}/org/aspectj/aspectjweaver/${aspectj.version}/aspectjweaver-${aspectj.version}.jar" -Dcucumber.options="--add-plugin io.qase.cucumber3.QaseEventListener"
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
