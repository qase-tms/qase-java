# Attachments in Cucumber 7

This guide covers how to attach files, screenshots, logs, and other content to your Qase test results using the Cucumber 7 reporter.

---

## Overview

Qase Cucumber 7 Reporter supports attaching various types of content to test results:

- **Files** — Attach files from the filesystem
- **Screenshots** — Attach images captured during test execution
- **Logs** — Attach text logs or console output
- **Binary data** — Attach any binary content from memory

Attachments can be added to:
- **Test cases** — Visible in the overall test result
- **Test steps** — Visible in specific step results

In Cucumber, each Given/When/Then step is automatically reported as a test step. Calling `Qase.attach()` inside a step definition attaches to that Gherkin step. Calling `Qase.attach()` outside step definitions (e.g., in hooks) attaches to the test case.

Import: `import io.qase.cucumber7.Qase;`

---

## Attaching Files

### From File Path

Attach one or more files from your filesystem:

```java
package com.example.steps;

import io.qase.cucumber7.Qase;
import io.cucumber.java.en.Then;

public class AttachmentSteps {
    @Then("I save the test evidence")
    public void saveTestEvidence() {
        Qase.attach("/path/to/screenshot.png");
    }
}
```

### Multiple Files

Attach multiple files at once using varargs:

```java
Qase.attach(
    "logs/test-output.log",
    "logs/server.log",
    "screenshots/final-state.png"
);
```

---

## Attaching Content from Memory

### Text Content

Attach text data such as logs or configuration:

```java
package com.example.steps;

import io.qase.cucumber7.Qase;
import io.cucumber.java.en.Then;

public class LogSteps {
    @Then("I capture the test log")
    public void captureTestLog() {
        String testLog = "Step 1: Navigated to login page\n"
                       + "Step 2: Entered credentials\n"
                       + "Step 3: Clicked submit\n"
                       + "Result: Login successful";
        Qase.attach("test-log.txt", testLog, "text/plain");
    }
}
```

### Binary Content

Attach binary data such as screenshots or PDFs:

```java
package com.example.steps;

import io.qase.cucumber7.Qase;
import io.cucumber.java.en.Then;

public class ScreenshotSteps {
    @Then("I capture a screenshot")
    public void captureScreenshot() {
        byte[] imageBytes = takeScreenshot(); // your screenshot method
        Qase.attach("screenshot.png", imageBytes, "image/png");
    }

    private byte[] takeScreenshot() {
        // Your screenshot capture logic
        return new byte[0];
    }
}
```

### JSON Data

Attach JSON responses or structured data:

```java
package com.example.steps;

import io.qase.cucumber7.Qase;
import io.cucumber.java.en.Then;

public class ApiSteps {
    @Then("I capture the API response")
    public void captureApiResponse() {
        String jsonResponse = "{\n"
            + "  \"status\": 200,\n"
            + "  \"body\": {\"userId\": 1, \"name\": \"Test User\"}\n"
            + "}";
        Qase.attach("api-response.json", jsonResponse, "application/json");
    }
}
```

---

## Attaching to Steps

In Cucumber, each Given/When/Then step is automatically reported as a test step. When you call `Qase.attach()` within a step definition, the attachment is automatically associated with that Gherkin step:

```java
package com.example.steps;

import io.qase.cucumber7.Qase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class CheckoutSteps {
    @Given("the user is on the product page")
    public void userOnProductPage() {
        // This attachment goes to the "Given" step
        Qase.attach("product-page.png", captureScreenshot(), "image/png");
    }

    @When("the user adds the item to cart")
    public void addItemToCart() {
        // This attachment goes to the "When" step
        Qase.attach("cart-state.json", getCartState(), "application/json");
    }

    @Then("the cart should contain {int} item(s)")
    public void verifyCartItems(int expectedCount) {
        // This attachment goes to the "Then" step
        Qase.attach("verification.txt", "Expected: " + expectedCount, "text/plain");
    }

    private byte[] captureScreenshot() {
        // Your screenshot capture logic
        return new byte[0];
    }

    private String getCartState() {
        // Your cart state retrieval logic
        return "{}";
    }
}
```

**Note:** There is no separate method for step-level attachments. The same `Qase.attach()` call automatically routes to the current Gherkin step being executed.

### Attaching in Hooks

Attachments called in `@Before` or `@After` hooks go to the test case, not a specific step:

```java
package com.example.hooks;

import io.qase.cucumber7.Qase;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

public class Hooks {
    @After
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            // This attachment goes to the TEST CASE (not a step)
            Qase.attach("failure-screenshot.png", captureScreenshot(), "image/png");
        }
    }

    private byte[] captureScreenshot() {
        // Your screenshot capture logic
        return new byte[0];
    }
}
```

---

## Method Reference

All methods are static on the `Qase` class. Import: `io.qase.cucumber7.Qase`

| Method Signature | Parameters | Use Case |
|---|---|---|
| `Qase.attach(String... files)` | File paths (varargs) | Attach existing files from filesystem |
| `Qase.attach(String fileName, String content, String contentType)` | fileName, text content, MIME type | Attach text data (logs, JSON, XML, HTML) |
| `Qase.attach(String fileName, byte[] content, String contentType)` | fileName, binary data, MIME type | Attach binary data (screenshots, PDFs) |

---

## MIME Types

Common MIME types for attachments:

| File Type | MIME Type | Example File |
|-----------|-----------|--------------|
| Text | `text/plain` | `log.txt` |
| JSON | `application/json` | `response.json` |
| XML | `application/xml` | `request.xml` |
| HTML | `text/html` | `report.html` |
| CSV | `text/csv` | `data.csv` |
| PNG | `image/png` | `screenshot.png` |
| JPEG | `image/jpeg` | `photo.jpg` |
| GIF | `image/gif` | `animation.gif` |
| PDF | `application/pdf` | `report.pdf` |
| ZIP | `application/zip` | `archive.zip` |

For other file types, refer to the standard MIME type list or specify `application/octet-stream` for generic binary data.

---

## Common Use Cases

### Screenshot Capture

Capture screenshots in step definitions to document test state:

```java
package com.example.steps;

import io.qase.cucumber7.Qase;
import io.cucumber.java.en.Then;

public class VerificationSteps {
    @Then("the page displays the expected content")
    public void verifyPageContent() {
        byte[] screenshot = captureScreenshot();
        Qase.attach("verification-screenshot.png", screenshot, "image/png");
    }

    private byte[] captureScreenshot() {
        // Your screenshot capture logic
        return new byte[0];
    }
}
```

### Log Capture

Attach logs in `@After` hooks to capture test execution details:

```java
package com.example.hooks;

import io.qase.cucumber7.Qase;
import io.cucumber.java.After;
import io.cucumber.java.Scenario;

public class Hooks {
    @After
    public void afterScenario(Scenario scenario) {
        String testLog = collectTestLogs();
        Qase.attach("test-execution.log", testLog, "text/plain");
    }

    private String collectTestLogs() {
        // Your log collection logic
        return "";
    }
}
```

### API Response Capture

Attach API responses in Then steps to document verification data:

```java
package com.example.steps;

import io.qase.cucumber7.Qase;
import io.cucumber.java.en.Then;

public class ApiSteps {
    @Then("the API returns the expected response")
    public void verifyApiResponse() {
        String response = getApiResponse();
        Qase.attach("api-response.json", response, "application/json");
    }

    private String getApiResponse() {
        // Your API response retrieval logic
        return "{}";
    }
}
```

### Report Generation

Generate and attach HTML reports for rich test documentation:

```java
package com.example.steps;

import io.qase.cucumber7.Qase;
import io.cucumber.java.en.Then;

public class ReportSteps {
    @Then("I generate the test report")
    public void generateTestReport() {
        String htmlReport = "<html><body>"
            + "<h1>Test Report</h1>"
            + "<p>Test completed successfully</p>"
            + "</body></html>";
        Qase.attach("test-report.html", htmlReport, "text/html");
    }
}
```

---

## Troubleshooting

### Attachments Not Appearing in Qase

**Problem:** Attachments are not visible in test results.

**Solution:**
1. Verify `QASE_MODE=testops` is set (or `qase.mode=testops` in config)
2. Enable debug logging: `"debug": true` in qase.config.json
3. Verify `QaseEventListener` is registered as a Cucumber plugin in your test runner configuration

### Attachments on Wrong Step

**Problem:** Attachments appear on the wrong step or at the test case level.

**Solution:**
- In Cucumber, each Given/When/Then is a separate step. Attachments called within a step definition go to that step.
- Attachments in `@Before`/`@After` hooks go to the test case, not a specific step.
- If you need to attach to a specific step from outside the step definition, call `Qase.attach()` within that step definition method.

### File Path Not Found

**Problem:** File attachment fails with "file not found" error.

**Solution:**
- Use absolute paths or paths relative to project root
- Verify file exists at the time of attachment call
- Check file permissions ensure the test process can read the file

### Large File Attachments

**Problem:** Large attachments slow down test execution or cause upload failures.

**Solution:**
- Consider attaching only on failure (use `Scenario.isFailed()` in `@After` hook)
- Compress large logs before attaching
- Set reasonable file size limits for your test suite
- Filter verbose logs to include only relevant information

---

## See Also

- [Usage Guide](usage.md) — Overview of all reporter features
- [Steps Guide](STEPS.md) — How Gherkin steps are reported to Qase
- [Upgrade Guide](UPGRADE.md) — Migration from v3 to v4
