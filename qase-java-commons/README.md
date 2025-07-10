# Qase Java Commons

This module is an SDK for developing test reporters for Qase TMS.
It's using `qase-api-client` as an API client, and all Qase reporters are, in turn,
using this package.
You should use it if you're developing your own test reporter for a special-purpose framework.

To report results from tests using a popular framework or test runner,
don't install this module directly and
use the corresponding reporter module instead:

* [TestNG](https://github.com/qase-tms/qase-java/tree/main/qase-testng-reporter#readme)
* [Junit4](https://github.com/qase-tms/qase-java/tree/main/qase-junit4-reporter#readme)
* [Junit5](https://github.com/qase-tms/qase-java/tree/main/qase-junit5-reporter#readme)
* [Cucumber3](https://github.com/qase-tms/qase-java/tree/main/qase-cucumber3-reporter#readme)
* [Cucumber4](https://github.com/qase-tms/qase-java/tree/main/qase-cucumber4-reporter#readme)
* [Cucumber5](https://github.com/qase-tms/qase-java/tree/main/qase-cucumber5-reporter#readme)
* [Cucumber6](https://github.com/qase-tms/qase-java/tree/main/qase-cucumber6-reporter#readme)
* [Cucumber7](https://github.com/qase-tms/qase-java/tree/main/qase-cucumber7-reporter#readme)

## Configuration

Qase Java Reporters can be configured in multiple ways:

- using a config file `qase.config.json`
- using environment variables

All configuration options are listed in the table below:

| Description                                                                                                                | Config file                | Environment variable            | CLI option                      | Default value                           | Required | Possible values            |
|----------------------------------------------------------------------------------------------------------------------------|----------------------------|---------------------------------|---------------------------------|-----------------------------------------|----------|----------------------------|
| **Common**                                                                                                                 |                            |                                 |                                 |                                         |          |                            |
| Mode of reporter                                                                                                           | `mode`                     | `QASE_MODE`                     | `QASE_MODE`                     | `off`                                   | No       | `testops`, `report`, `off` |
| Fallback mode of reporter                                                                                                  | `fallback`                 | `QASE_FALLBACK`                 | `QASE_FALLBACK`                 | `off`                                   | No       | `testops`, `report`, `off` |
| Environment                                                                                                                | `environment`              | `QASE_ENVIRONMENT`              | `QASE_ENVIRONMENT`              | undefined                               | No       | Any string                 |
| Root suite                                                                                                                 | `rootSuite`                | `QASE_ROOT_SUITE`               | `QASE_ROOT_SUITE`               | undefined                               | No       | Any string                 |
| Enable debug logs                                                                                                          | `debug`                    | `QASE_DEBUG`                    | `QASE_DEBUG`                    | `False`                                 | No       | `True`, `False`            |
| **Qase Report configuration**                                                                                              |                            |                                 |                                 |                                         |          |                            |
| Driver used for report mode                                                                                                | `report.driver`            | `QASE_REPORT_DRIVER`            | `QASE_REPORT_DRIVER`            | `local`                                 | No       | `local`                    |
| Path to save the report                                                                                                    | `report.connection.path`   | `QASE_REPORT_CONNECTION_PATH`   | `QASE_REPORT_CONNECTION_PATH`   | `./build/qase-report`                   |          |                            |
| Local report format                                                                                                        | `report.connection.format` | `QASE_REPORT_CONNECTION_FORMAT` | `QASE_REPORT_CONNECTION_FORMAT` | `json`                                  |          | `json`, `jsonp`            |
| **Qase TestOps configuration**                                                                                             |                            |                                 |                                 |                                         |          |                            |
| Token for [API access](https://developers.qase.io/#authentication)                                                         | `testops.api.token`        | `QASE_TESTOPS_API_TOKEN`        | `QASE_TESTOPS_API_TOKEN`        | undefined                               | Yes      | Any string                 |
| Qase API host. For enterprise users, specify address: `example.qase.io`                                          | `testops.api.host`         | `QASE_TESTOPS_API_HOST`         | `QASE_TESTOPS_API_HOST`         | `qase.io`                               | No       | Any string                 |
| Qase enterprise environment                                                                                                | `testops.api.enterprise`   | `QASE_TESTOPS_API_ENTERPRISE`   | `QASE_TESTOPS_API_ENTERPRISE`   | `False`                                 | No       | `True`, `False`            |
| Code of your project, which you can take from the URL: `https://app.qase.io/project/DEMOTR` - `DEMOTR` is the project code | `testops.project`          | `QASE_TESTOPS_PROJECT`          | `QASE_TESTOPS_PROJECT`          | undefined                               | Yes      | Any string                 |
| Qase test run ID                                                                                                           | `testops.run.id`           | `QASE_TESTOPS_RUN_ID`           | `QASE_TESTOPS_RUN_ID`           | undefined                               | No       | Any integer                |
| Qase test run title                                                                                                        | `testops.run.title`        | `QASE_TESTOPS_RUN_TITLE`        | `QASE_TESTOPS_RUN_TITLE`        | `Automated run <Current date and time>` | No       | Any string                 |
| Qase test run description                                                                                                  | `testops.run.description`  | `QASE_TESTOPS_RUN_DESCRIPTION`  | `QASE_TESTOPS_RUN_DESCRIPTION`  | `<Framework name> automated run`        | No       | Any string                 |
| Qase test run complete                                                                                                     | `testops.run.complete`     | `QASE_TESTOPS_RUN_COMPLETE`     | `QASE_TESTOPS_RUN_COMPLETE`     | `True`                                  |          | `True`, `False`            |
| Qase test run tags                                                                                                         | `testops.run.tags`         | `QASE_TESTOPS_RUN_TAGS`         | `QASE_TESTOPS_RUN_TAGS`         | undefined                               | No       | Comma-separated strings    |
| Qase test run configurations                                                                                               | `testops.run.configurations` | `QASE_TESTOPS_RUN_CONFIGURATIONS` | `QASE_TESTOPS_RUN_CONFIGURATIONS` | undefined                               | No       | Comma-separated key=value pairs |
| Qase test run configurations create if not exists                                                                         | `testops.run.configurations.createIfNotExists` | `QASE_TESTOPS_CONFIGURATIONS_CREATE_IF_NOT_EXISTS` | `QASE_TESTOPS_CONFIGURATIONS_CREATE_IF_NOT_EXISTS` | `False`                                 | No       | `True`, `False`            |
| Qase test plan ID                                                                                                          | `testops.plan.id`          | `QASE_TESTOPS_PLAN_ID`          | `QASE_TESTOPS_PLAN_ID`          | undefined                               | No       | Any integer                |
| Size of batch for sending test results                                                                                     | `testops.batch.size`       | `QASE_TESTOPS_BATCH_SIZE`       | `QASE_TESTOPS_BATCH_SIZE`       | `200`                                   | No       | Any integer                |
| Enable defects for failed test cases                                                                                       | `testops.defect`           | `QASE_TESTOPS_DEFECT`           | `QASE_TESTOPS_DEFECT`           | `False`                                 | No       | `True`, `False`            |

### Example `qase.config.json` config:

```json
{
  "mode": "testops",
  "fallback": "report",
  "debug": false,
  "environment": "local",
  "captureLogs": false,
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
      "complete": true,
      "tags": [
        "tag1",
        "tag2"
      ],
      "configurations": {
        "values": [
          {
            "name": "browser",
            "value": "chrome"
          },
          {
            "name": "environment",
            "value": "staging"
          }
        ],
        "createIfNotExists": true
      }
    },
    "defect": false,
    "project": "<project_code>",
    "batch": {
      "size": 100
    }
  }
}
```

## How to add an attachment for a failed test?

You need to implement the `HooksListener` interface and override the `beforeTestStop` method.

For example:

```java
import io.qase.commons.hooks.HooksListener;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.domain.TestResultStatus;
import io.qase.testng.Qase;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class AttachmentManager implements HooksListener {

    @Override
    public void beforeTestStop(final TestResult result) {
        if (result.execution.status == TestResultStatus.FAILED) {
            // Add a screenshot
            Qase.attach("Failure Screenshot.png", ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");
            
            // Add log file
            Qase.attach("/logs/failed.log");
            
            // Add any text
            Qase.attach("file.txt", "any text", "plain/text");
        }
    }
}
```

After that, you need to add file **io.qase.commons.hooks.HooksListener** to **resources/META-INF/services** folder:
```text
<your-package>.AttachmentManager
```
