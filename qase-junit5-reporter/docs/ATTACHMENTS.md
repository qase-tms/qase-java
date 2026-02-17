# Attachments in JUnit 5

This guide covers how to attach files, screenshots, logs, and other content to your Qase test results using the JUnit 5 reporter.

---

## Overview

Attachments provide visual and contextual evidence of test execution. They can include:

- **Files** — Screenshots, logs, PDFs, reports
- **Screenshots** — Visual evidence of UI state
- **Logs** — Test execution logs, debug output
- **Binary data** — Any content generated during test execution

Attachments can be added to:
- **Test cases** — Visible in the overall test result (when called outside `@Step` methods)
- **Test steps** — Visible in specific step results (when called inside `@Step` methods)

The routing is automatic: `Qase.attach()` called inside a `@Step` method attaches to that step; called outside attaches to the test case.

**Import:**
```java
import io.qase.junit5.Qase;
```

---

## Attaching Files

### From File Path

Attach a single file by specifying its path. When using file paths, the MIME type is inferred by the server during upload.

```java
package com.example.tests;

import io.qase.junit5.Qase;
import org.junit.jupiter.api.Test;

public class FileAttachmentTests {
    @Test
    public void testWithFileAttachment() {
        Qase.attach("/path/to/screenshot.png");
    }
}
```

### Multiple Files

Use the varargs signature to attach multiple files at once.

```java
package com.example.tests;

import io.qase.junit5.Qase;
import org.junit.jupiter.api.Test;

public class MultipleFileAttachmentTests {
    @Test
    public void testWithMultipleFiles() {
        Qase.attach(
            "logs/test-output.log",
            "logs/server.log",
            "screenshots/final-state.png"
        );
    }
}
```

**Note:** When using file paths, no explicit MIME type is needed. The server infers the type during upload.

---

## Attaching Content from Memory

### Text Content

Attach text-based content generated during test execution, such as logs, reports, or debug output.

```java
package com.example.tests;

import io.qase.junit5.Qase;
import org.junit.jupiter.api.Test;

public class TextAttachmentTests {
    @Test
    public void testWithTextAttachment() {
        String testLog = "Step 1: Navigated to login page\n"
                       + "Step 2: Entered credentials\n"
                       + "Step 3: Clicked submit\n"
                       + "Result: Login successful";
        Qase.attach("test-log.txt", testLog, "text/plain");
    }
}
```

### Binary Content

Attach binary data such as screenshots, PDFs, or any byte array content. You must specify the MIME type explicitly.

```java
package com.example.tests;

import io.qase.junit5.Qase;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;

public class BinaryAttachmentTests {
    @Test
    public void testWithBinaryAttachment() {
        byte[] imageBytes = captureScreenshot(); // your screenshot method
        Qase.attach("screenshot.png", imageBytes, "image/png");
    }

    @Test
    public void testWithFileAsBytes() throws Exception {
        byte[] pdfBytes = Files.readAllBytes(Path.of("reports/summary.pdf"));
        Qase.attach("test-report.pdf", pdfBytes, "application/pdf");
    }

    private byte[] captureScreenshot() {
        // Your screenshot capture implementation
        return new byte[0];
    }
}
```

### JSON Data

Attach JSON responses, configuration data, or any structured data for debugging.

```java
package com.example.tests;

import io.qase.junit5.Qase;
import org.junit.jupiter.api.Test;

public class JsonAttachmentTests {
    @Test
    public void testWithJsonAttachment() {
        String jsonResponse = "{\n"
            + "  \"status\": 200,\n"
            + "  \"body\": {\"userId\": 1, \"name\": \"Test User\"},\n"
            + "  \"headers\": {\"Content-Type\": \"application/json\"}\n"
            + "}";
        Qase.attach("api-response.json", jsonResponse, "application/json");
    }
}
```

---

## Attaching to Steps

The `Qase.attach()` method automatically routes attachments based on execution context:

- **Inside a `@Step` method** → Attachment goes to that step
- **Outside a `@Step` method** → Attachment goes to the test case

```java
package com.example.tests;

import io.qase.junit5.Qase;
import io.qase.commons.annotation.Step;
import org.junit.jupiter.api.Test;

public class StepAttachmentTests {
    @Test
    public void testWithStepAttachments() {
        // This attachment goes to the TEST CASE
        Qase.attach("test-config.json", "{\"env\": \"staging\"}", "application/json");

        performLogin();  // Attachments inside go to the STEP
    }

    @Step("Perform login")
    private void performLogin() {
        // This attachment goes to the "Perform login" STEP
        Qase.attach("login-screenshot.png", captureScreenshot(), "image/png");
    }

    private byte[] captureScreenshot() {
        return new byte[0]; // Your screenshot implementation
    }
}
```

**Note:** There is no separate `attachToStep()` method. The same `Qase.attach()` call automatically routes based on context.

**AspectJ Requirement:** For step tracking to work, AspectJ weaver must be configured in your Maven or Gradle build. If AspectJ is not configured, all attachments (even those inside `@Step` methods) will go to the test case instead of the step. See the [Steps Guide](STEPS.md) for configuration details.

---

## Method Reference

All attachment methods are static methods in the `io.qase.junit5.Qase` class.

| Method Signature | Parameters | Use Case |
|------------------|------------|----------|
| `Qase.attach(String... files)` | File paths (varargs) | Attach existing files from filesystem |
| `Qase.attach(String fileName, String content, String contentType)` | fileName, text content, MIME type | Attach text data (logs, JSON, XML, HTML) |
| `Qase.attach(String fileName, byte[] content, String contentType)` | fileName, binary data, MIME type | Attach binary data (screenshots, PDFs) |

**Key points:**
- All methods are static — use `Qase.attach(...)`
- File path overload does not require explicit MIME type
- Content/bytes overloads require explicit MIME type
- Import: `io.qase.junit5.Qase`

---

## MIME Types

When using the content or bytes overloads, you must specify the MIME type explicitly. Here's a reference of common MIME types:

| Extension | MIME Type | Use Case |
|-----------|-----------|----------|
| `.png` | `image/png` | Screenshots |
| `.jpg`, `.jpeg` | `image/jpeg` | Photos, compressed screenshots |
| `.gif` | `image/gif` | Animated captures |
| `.svg` | `image/svg+xml` | Vector graphics, diagrams |
| `.txt` | `text/plain` | Logs, plain text output |
| `.log` | `text/plain` | Log files |
| `.json` | `application/json` | API responses, configuration |
| `.xml` | `application/xml` | XML data, SOAP responses |
| `.html` | `text/html` | HTML reports, rendered pages |
| `.csv` | `text/csv` | Data exports, test data |
| `.pdf` | `application/pdf` | Reports, generated documents |
| `.zip` | `application/zip` | Compressed archives |

**Important:** For file path attachments (`Qase.attach("/path/to/file")`), MIME types are inferred automatically by the server. For content/bytes attachments, always specify the correct MIME type.

---

## Common Use Cases

These examples show abstract patterns for common attachment scenarios. They use generic method names and avoid references to specific external libraries.

### Screenshot Capture

Capture and attach visual evidence of application state.

```java
package com.example.tests;

import io.qase.commons.annotation.Step;
import io.qase.junit5.Qase;
import org.junit.jupiter.api.Test;

public class ScreenshotTests {
    @Test
    public void testWithScreenshots() {
        navigateToPage();
        performAction();
    }

    @Step("Navigate to page")
    private void navigateToPage() {
        // Your navigation logic
        byte[] screenshot = captureScreenshot();
        Qase.attach("page-loaded.png", screenshot, "image/png");
    }

    @Step("Perform action")
    private void performAction() {
        // Your action logic
        byte[] screenshot = captureScreenshot();
        Qase.attach("action-result.png", screenshot, "image/png");
    }

    private byte[] captureScreenshot() {
        // Your screenshot implementation
        return new byte[0];
    }
}
```

### Log Capture

Collect and attach execution logs for debugging.

```java
package com.example.tests;

import io.qase.junit5.Qase;
import org.junit.jupiter.api.Test;

public class LogCaptureTests {
    @Test
    public void testWithLogCapture() {
        StringBuilder log = new StringBuilder();
        log.append("Test started at: ").append(java.time.Instant.now()).append("\n");

        // Test execution with logging
        log.append("Step 1: Initialized test data\n");
        log.append("Step 2: Executed main action\n");
        log.append("Step 3: Validated results\n");

        log.append("Test completed at: ").append(java.time.Instant.now()).append("\n");

        Qase.attach("test-execution.log", log.toString(), "text/plain");
    }
}
```

### API Response Capture

Attach HTTP response data for API testing.

```java
package com.example.tests;

import io.qase.junit5.Qase;
import org.junit.jupiter.api.Test;

public class ApiResponseTests {
    @Test
    public void testApiEndpoint() {
        // Execute API call using your HTTP client
        String responseBody = executeApiCall("/api/users");
        int statusCode = getLastStatusCode();

        // Format and attach response
        String report = String.format("Status: %d\nResponse:\n%s", statusCode, responseBody);
        Qase.attach("api-response.txt", report, "text/plain");

        // Also attach as JSON if response is JSON
        if (responseBody.trim().startsWith("{") || responseBody.trim().startsWith("[")) {
            Qase.attach("api-response.json", responseBody, "application/json");
        }
    }

    private String executeApiCall(String endpoint) {
        // Your HTTP client implementation
        return "{\"users\": []}";
    }

    private int getLastStatusCode() {
        // Your HTTP client status code retrieval
        return 200;
    }
}
```

### Report Generation

Generate and attach HTML or CSV reports.

```java
package com.example.tests;

import io.qase.junit5.Qase;
import org.junit.jupiter.api.Test;

public class ReportGenerationTests {
    @Test
    public void testWithReport() {
        // Generate HTML report
        String htmlReport = "<html><body>"
            + "<h1>Test Results Summary</h1>"
            + "<table>"
            + "<tr><td>Total Tests</td><td>10</td></tr>"
            + "<tr><td>Passed</td><td>8</td></tr>"
            + "<tr><td>Failed</td><td>2</td></tr>"
            + "<tr><td>Duration</td><td>45s</td></tr>"
            + "</table>"
            + "</body></html>";
        Qase.attach("results-summary.html", htmlReport, "text/html");

        // Generate CSV export
        String csvReport = "Test Name,Status,Duration\n"
            + "Test 1,Passed,5s\n"
            + "Test 2,Passed,3s\n"
            + "Test 3,Failed,2s\n";
        Qase.attach("results-data.csv", csvReport, "text/csv");
    }
}
```

### Conditional Attachments (On Failure)

Attach evidence only when tests fail to reduce overhead.

```java
package com.example.tests;

import io.qase.junit5.Qase;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ConditionalAttachmentTests {
    @Test
    public void testWithConditionalAttachment() {
        boolean testPassed = false;

        try {
            // Test execution
            performTestAction();
            testPassed = true;
        } catch (AssertionError | Exception e) {
            // Attach evidence only on failure
            byte[] screenshot = captureScreenshot();
            Qase.attach("failure-screenshot.png", screenshot, "image/png");

            String errorLog = "Test failed with error: " + e.getMessage();
            Qase.attach("failure-log.txt", errorLog, "text/plain");

            throw e; // Re-throw to fail the test
        }
    }

    private void performTestAction() {
        // Your test logic
    }

    private byte[] captureScreenshot() {
        return new byte[0];
    }
}
```

---

## Troubleshooting

### Attachments Not Appearing in Qase

**Symptoms:** Attachments are not visible in the Qase test results.

**Solutions:**
1. Verify `QASE_MODE=testops` is set — attachments only upload in testops mode
2. Enable debug logging: set `"debug": true` in `qase.config.json`
3. Check console output for upload errors or exceptions
4. Verify your API token has the necessary permissions

### File Path Not Found

**Symptoms:** Error messages about missing files or `FileNotFoundException`.

**Solutions:**
1. Use absolute paths or paths relative to the project root
2. Verify the file exists at runtime — files generated during tests must exist before the `attach()` call
3. Check file permissions — ensure the test process can read the file
4. For files created in temp directories, ensure they're not cleaned up before attachment

### Large File Attachments

**Symptoms:** Tests become slow or time out; memory issues.

**Solutions:**
- Large files slow test execution and upload — consider compressing before attaching
- Attach only on failure using try/catch pattern (see Conditional Attachments example)
- Consider text summaries instead of full binary data for large responses
- Set reasonable limits (e.g., compress images, truncate logs)

### Wrong MIME Type

**Symptoms:** Attachments appear as "unknown" type or fail to display correctly in Qase.

**Solutions:**
- For content/bytes overloads, MIME type is whatever you pass — ensure it matches the actual content
- Common mistake: passing `"text/plain"` for binary data or vice versa
- For JSON responses, use `"application/json"` not `"text/plain"`
- For PNG screenshots, use `"image/png"` not `"image/jpg"`

### Attachments on Wrong Test/Step

**Symptoms:** Attachments appear on the test case when they should be on a step, or vice versa.

**Solutions:**
- Attachments inside `@Step` methods go to the step; outside go to the test case
- Ensure AspectJ is configured correctly (see Steps Guide)
- If steps are not being processed by AspectJ, all attachments will go to the test case
- Verify `@Step` methods have the annotation and are called during test execution
- Check that AspectJ weaver is loaded — look for AspectJ messages in debug logs

### Memory Issues with Multiple Attachments

**Symptoms:** `OutOfMemoryError` or heap space issues when attaching many files.

**Solutions:**
- Attach files one at a time rather than holding all in memory
- Use file path attachments instead of loading files into byte arrays
- Process and attach files sequentially rather than building large collections
- Consider streaming large files or splitting into smaller chunks

### Attachments Timing Out

**Symptoms:** Tests hang or timeout during attachment upload.

**Solutions:**
- Check network connectivity to Qase servers
- Verify firewall rules allow outbound connections to Qase API
- Reduce attachment sizes or count
- Enable debug logging to see where the timeout occurs
- Check for very large files (>50MB may cause issues)

---

## See Also

- [Usage Guide](usage.md) — Overview of all reporter features
- [Steps Guide](STEPS.md) — Defining test steps for detailed reporting
- [Upgrade Guide](UPGRADE.md) — Migration from v3 to v4
