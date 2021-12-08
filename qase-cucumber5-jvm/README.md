# Qase TMS Cucumber 5 JVM Integration #
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
| qase.enable       | Enable Integration |
| qase.project.code | Project Code |
| qase.run.id       | Run Id |
| qase.api.token    | Qase API Token |

## Maven ##

Add the following dependency and repository to your pom.xml:
```xml
<dependencies>
    <dependency>
        <groupId>io.qase</groupId>
        <artifactId>qase-cucumber5-jvm</artifactId>
        <version>2.0.0</version>
    </dependency>
</dependencies>
<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <version>3.0.0-M4</version>
            <configuration>
                <argLine>
                    -Dcucumber.options="--add-plugin", "io.qase.cucumber5.QaseEventListener"
                </argLine>
            </configuration>
        </plugin>
    </plugins>
</build>
```
