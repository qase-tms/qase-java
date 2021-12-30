# Qase TMS TestNG Integration #
[![License](https://lxgaming.github.io/badges/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

## Description ##
This integration uploads test run results to Qase TMS via API.

To link autotest to test case in Qase TMS use annotation `@CaseId` or `@io.qameta.allure.TmsLink`

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
<dependency>
    <groupId>io.qase</groupId>
    <artifactId>qase-testng</artifactId>
    <version>2.0.1</version>
    <scope>test</scope>
</dependency>
```

## Run example ##

```
mvn clean test -Dqase.enable=true -Dqase.project.code=PRJ -Dqase.run.id=123 -Dqase.api.token=secret-token
```
