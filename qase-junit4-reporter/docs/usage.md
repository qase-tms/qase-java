# Qase Integration in JUnit 4

This guide provides comprehensive instructions for using Qase annotations and API with JUnit 4.

> **Configuration:** For complete configuration reference including all available options, environment variables, and examples, see the reporter [README](../../qase-junit4-reporter/README.md).

## Table of Contents

**Test Linking**
- [Adding QaseID](#adding-qaseid)
  - [Single ID](#single-id)
  - [Multiple IDs](#multiple-ids)

**Test Metadata**
- [Adding Title](#adding-title)
- [Adding Fields](#adding-fields)
  - [System Fields Reference](#system-fields-reference)
  - [Example](#fields-example)
- [Adding Suite](#adding-suite)
  - [Simple Suite](#simple-suite)
  - [Nested Suites](#nested-suites)

**Test Control**
- [Ignoring Tests](#ignoring-tests)

**Enhanced Reporting**
- [Working with Attachments](#working-with-attachments)
  - [Attach File from Path](#attach-file-from-path)
  - [Attach Content from Code](#attach-content-from-code)
  - [Attach to Specific Step](#attach-to-specific-step)
  - [Supported MIME Types](#supported-mime-types)
- [Working with Steps](#working-with-steps)
  - [Using @Step Annotation](#using-step-annotation)
  - [Nested Steps](#nested-steps)
- [Working with Parameters](#working-with-parameters)

**Execution**
- [Running Tests](#running-tests)
  - [Basic Execution](#basic-execution)
  - [With Environment Variables](#with-environment-variables)
  - [With System Properties](#with-system-properties)
  - [Targeting Specific Tests](#targeting-specific-tests)

**Reference**
- [Complete Examples](#complete-examples)
- [Troubleshooting](#troubleshooting)
- [See Also](#see-also)

---

## Adding QaseID

You can link your test to one or more test cases in Qase by using the `@QaseId` or `@QaseIds` annotations.

### Single ID

Use `@QaseId` to associate a single test case ID with your test method.

```java
package com.example.tests;

import io.qase.commons.annotation.QaseId;
import org.junit.Test;

public class SimpleTests {

    @Test
    @QaseId(123)
    public void testWithQaseId() {
        System.out.println("Test linked to Qase test case 123");
    }
}
```

### Multiple IDs

Use `@QaseIds` to link a test to multiple test cases.

```java
package com.example.tests;

import io.qase.commons.annotation.QaseIds;
import org.junit.Test;

public class SimpleTests {

    @Test
    @QaseIds({123, 456, 789})
    public void testWithMultipleIds() {
        System.out.println("Test linked to multiple test cases");
    }
}
```

---

## Adding Title

Set a custom title for your test result using the `@QaseTitle` annotation. If not specified, the method name is used as the default title.

```java
package com.example.tests;

import io.qase.commons.annotation.QaseTitle;
import org.junit.Test;

public class SimpleTests {

    @Test
    @QaseTitle("User login with valid credentials")
    public void testLogin() {
        System.out.println("Test with custom title");
    }
}
```

---

## Adding Fields

Enrich your test cases with additional metadata using the `@QaseFields` annotation. This allows you to set system fields like description, severity, priority, and more.

### System Fields Reference

| Field Name | Description | Valid Values |
|------------|-------------|--------------|
| `description` | Test case description | Any text |
| `preconditions` | Test preconditions (supports Markdown) | Any text |
| `postconditions` | Test postconditions | Any text |
| `severity` | Test severity level | `blocker`, `critical`, `major`, `normal`, `minor`, `trivial` |
| `priority` | Test priority | `high`, `medium`, `low` |
| `layer` | Test layer | `e2e`, `api`, `unit` |

### Fields Example

```java
package com.example.tests;

import io.qase.commons.annotation.QaseFields;
import io.qase.commons.models.annotation.Field;
import org.junit.Test;

public class SimpleTests {

    @Test
    @QaseFields({
        @Field(name = "description", value = "Verify that user can login with valid credentials"),
        @Field(name = "preconditions", value = "User account exists in the system"),
        @Field(name = "postconditions", value = "User is redirected to dashboard"),
        @Field(name = "severity", value = "critical"),
        @Field(name = "priority", value = "high"),
        @Field(name = "layer", value = "e2e")
    })
    public void testLoginFlow() {
        System.out.println("Test with metadata fields");
    }
}
```

---

## Adding Suite

Categorize your tests into suites and sub-suites using the `@QaseSuite` annotation.

### Simple Suite

Assign a test to a single suite.

```java
package com.example.tests;

import io.qase.commons.annotation.QaseSuite;
import org.junit.Test;

public class SimpleTests {

    @Test
    @QaseSuite("Authentication")
    public void testLogin() {
        System.out.println("Test in Authentication suite");
    }
}
```

### Nested Suites

Create nested suites by separating parent and child with `\t` (tab character).

```java
package com.example.tests;

import io.qase.commons.annotation.QaseSuite;
import org.junit.Test;

public class SimpleTests {

    @Test
    @QaseSuite("Authentication\tLogin")
    public void testSuccessfulLogin() {
        System.out.println("Test in Authentication > Login suite");
    }

    @Test
    @QaseSuite("Authentication\tLogout")
    public void testLogout() {
        System.out.println("Test in Authentication > Logout suite");
    }
}
```

---

## Ignoring Tests

Exclude a test from being reported to Qase using the `@QaseIgnore` annotation. The test will still execute locally, but its results will not be sent to Qase.

```java
package com.example.tests;

import io.qase.commons.annotation.QaseIgnore;
import org.junit.Test;

public class SimpleTests {

    @Test
    @QaseIgnore
    public void testInDevelopment() {
        System.out.println("This test runs but is not reported to Qase");
    }
}
```

---

## Working with Attachments

Attach files, screenshots, logs, and other content to your test results using the `Qase.attach()` methods.

### Attach File from Path

Attach one or more files by specifying their file paths.

```java
package com.example.tests;

import io.qase.junit4.Qase;
import org.junit.Test;

public class AttachmentTests {

    @Test
    public void testWithAttachment() {
        System.out.println("Test with file attachment");

        // Attach a single file
        Qase.attach("path/to/screenshot.png");

        // Attach multiple files
        Qase.attach("path/to/log1.txt", "path/to/log2.txt");
    }
}
```

### Attach Content from Code

Attach content generated in your test code, such as strings or byte arrays.

```java
package com.example.tests;

import io.qase.junit4.Qase;
import org.junit.Test;
import java.nio.charset.StandardCharsets;

public class AttachmentTests {

    @Test
    public void testWithContentAttachment() {
        // Attach string content
        String report = "Test execution report:\nAll checks passed";
        Qase.attach("report.txt", report, "text/plain");

        // Attach byte array (e.g., generated image)
        byte[] imageBytes = generateScreenshot();
        Qase.attach("screenshot.png", imageBytes, "image/png");
    }

    private byte[] generateScreenshot() {
        return "fake-image-data".getBytes(StandardCharsets.UTF_8);
    }
}
```

### Attach to Specific Step

When called inside a `@Step` method, attachments are automatically associated with that step.

```java
package com.example.tests;

import io.qase.commons.annotation.Step;
import io.qase.junit4.Qase;
import org.junit.Test;

public class AttachmentTests {

    @Test
    public void testWithStepAttachment() {
        openLoginPage();
        enterCredentials();
    }

    @Step("Open login page")
    private void openLoginPage() {
        System.out.println("Opening login page");
        // This attachment is linked to "Open login page" step
        Qase.attach("login-page.png", screenshotBytes(), "image/png");
    }

    @Step("Enter credentials")
    private void enterCredentials() {
        System.out.println("Entering credentials");
        // This attachment is linked to "Enter credentials" step
        Qase.attach("credentials-form.png", screenshotBytes(), "image/png");
    }

    private byte[] screenshotBytes() {
        return new byte[0]; // Placeholder
    }
}
```

### Supported MIME Types

Common MIME types for attachments:

| File Extension | MIME Type |
|----------------|-----------|
| `.png` | `image/png` |
| `.jpg`, `.jpeg` | `image/jpeg` |
| `.txt` | `text/plain` |
| `.json` | `application/json` |
| `.xml` | `application/xml` |
| `.html` | `text/html` |
| `.pdf` | `application/pdf` |
| `.log` | `text/plain` |

> For more details, see [Attachments Guide](ATTACHMENTS.md).

---

## Working with Steps

Break down your tests into steps for better visibility and debugging.

### Using @Step Annotation

Annotate methods with `@Step` to report them as test steps. The method must be called from your test method.

**Important:** AspectJ weaver is required for step tracking. Ensure it is configured in your Maven or Gradle build (see examples in the repository).

```java
package com.example.tests;

import io.qase.commons.annotation.Step;
import org.junit.Test;

public class StepTests {

    @Test
    public void testLoginFlow() {
        openBrowser();
        navigateToLoginPage();
        enterCredentials();
        clickLoginButton();
        verifyDashboard();
    }

    @Step("Open browser")
    private void openBrowser() {
        System.out.println("Browser opened");
    }

    @Step("Navigate to login page")
    private void navigateToLoginPage() {
        System.out.println("Navigated to login page");
    }

    @Step("Enter user credentials")
    private void enterCredentials() {
        System.out.println("Credentials entered");
    }

    @Step("Click login button")
    private void clickLoginButton() {
        System.out.println("Login button clicked");
    }

    @Step("Verify dashboard is displayed")
    private void verifyDashboard() {
        System.out.println("Dashboard verified");
    }
}
```

### Nested Steps

Steps can call other steps, creating a hierarchy of nested steps.

```java
package com.example.tests;

import io.qase.commons.annotation.Step;
import org.junit.Test;

public class NestedStepTests {

    @Test
    public void testComplexFlow() {
        performLogin();
        performCheckout();
    }

    @Step("Perform login")
    private void performLogin() {
        openLoginPage();
        submitCredentials();
    }

    @Step("Open login page")
    private void openLoginPage() {
        System.out.println("Login page opened");
    }

    @Step("Submit credentials")
    private void submitCredentials() {
        System.out.println("Credentials submitted");
    }

    @Step("Perform checkout")
    private void performCheckout() {
        addItemToCart();
        proceedToPayment();
    }

    @Step("Add item to cart")
    private void addItemToCart() {
        System.out.println("Item added to cart");
    }

    @Step("Proceed to payment")
    private void proceedToPayment() {
        System.out.println("Proceeded to payment");
    }
}
```

> For more details, see [Steps Guide](STEPS.md).

---

## Working with Parameters

JUnit 4 supports parameterized tests using the `@RunWith(Parameterized.class)` runner with the `@Parameterized.Parameters` annotation.

```java
package com.example.tests;

import io.qase.commons.annotation.QaseId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ParameterizedTests {

    @Parameters
    public static Collection<Object[]> loginDataProvider() {
        return Arrays.asList(new Object[][] {
            { "user1@example.com", "password123" },
            { "user2@example.com", "securePass456" },
            { "user3@example.com", "testPass789" }
        });
    }

    private final String email;
    private final String password;

    public ParameterizedTests(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @Test
    @QaseId(100)
    public void testLoginWithDifferentUsers() {
        System.out.println("Testing login with: " + email);
        // Test implementation
    }
}
```

---

## Running Tests

### Basic Execution

Run all tests using Maven or Gradle.

**Maven:**
```bash
mvn clean test
```

**Gradle:**
```bash
./gradlew clean test
```

### With Environment Variables

Set Qase configuration via environment variables.

**Maven:**
```bash
QASE_MODE=testops \
QASE_TESTOPS_PROJECT=DEMO \
QASE_TESTOPS_API_TOKEN=your_api_token_here \
mvn clean test
```

**Gradle:**
```bash
QASE_MODE=testops \
QASE_TESTOPS_PROJECT=DEMO \
QASE_TESTOPS_API_TOKEN=your_api_token_here \
./gradlew clean test
```

### With System Properties

Pass configuration as system properties.

**Maven:**
```bash
mvn clean test \
  -Dqase.mode=testops \
  -Dqase.testops.project=DEMO \
  -Dqase.testops.api.token=your_api_token_here
```

**Gradle:**
```bash
./gradlew clean test \
  -Dqase.mode=testops \
  -Dqase.testops.project=DEMO \
  -Dqase.testops.api.token=your_api_token_here
```

### Targeting Specific Tests

Run specific test classes or methods.

**Maven:**
```bash
# Run specific test class
mvn test -Dtest=SimpleTests

# Run specific test method
mvn test -Dtest=SimpleTests#testWithQaseId
```

**Gradle:**
```bash
# Run specific test class
./gradlew test --tests SimpleTests

# Run specific test method
./gradlew test --tests SimpleTests.testWithQaseId
```

**Configuration Note:** You can also use a `qase.config.json` file at the project root instead of environment variables or system properties. See the [README](../../qase-junit4-reporter/README.md) for details.

---

## Complete Examples

Here's a comprehensive example showing how to combine QaseID, title, fields, suite, steps, and attachments in a realistic test scenario.

```java
package com.example.tests;

import io.qase.commons.annotation.*;
import io.qase.commons.models.annotation.Field;
import io.qase.junit4.Qase;
import org.junit.Test;

public class LoginTest {

    @Test
    @QaseId(123)
    @QaseTitle("Successful login with valid credentials")
    @QaseFields({
        @Field(name = "description", value = "Verify that a user can successfully log in with valid email and password"),
        @Field(name = "preconditions", value = "User account exists with email: user@example.com"),
        @Field(name = "postconditions", value = "User is logged in and redirected to dashboard"),
        @Field(name = "severity", value = "critical"),
        @Field(name = "priority", value = "high"),
        @Field(name = "layer", value = "e2e")
    })
    @QaseSuite("Authentication\tLogin")
    public void testSuccessfulLogin() {
        openLoginPage();
        enterCredentials("user@example.com", "password123");
        clickLoginButton();
        verifyDashboardDisplayed();
    }

    @Step("Open login page")
    private void openLoginPage() {
        System.out.println("Opening login page at /login");
        // Attach screenshot of the page
        Qase.attach("login-page.png", captureScreenshot(), "image/png");
    }

    @Step("Enter user credentials")
    private void enterCredentials(String email, String password) {
        System.out.println("Entering email: " + email);
        System.out.println("Entering password: ********");
        // Attach form data for debugging
        String formData = "Email: " + email + "\nPassword: [REDACTED]";
        Qase.attach("form-data.txt", formData, "text/plain");
    }

    @Step("Click login button")
    private void clickLoginButton() {
        System.out.println("Clicking login button");
    }

    @Step("Verify dashboard is displayed")
    private void verifyDashboardDisplayed() {
        System.out.println("Verifying dashboard elements");
        // Attach screenshot of dashboard
        Qase.attach("dashboard.png", captureScreenshot(), "image/png");
    }

    private byte[] captureScreenshot() {
        // Placeholder for actual screenshot capture logic
        return new byte[0];
    }
}
```

**Example project structure:**

```text
my-project/
├── pom.xml (or build.gradle)
├── qase.config.json
└── src/
    └── test/
        └── java/
            └── com/
                └── example/
                    └── tests/
                        ├── LoginTest.java
                        ├── CheckoutTest.java
                        └── ProfileTest.java
```

**Sample qase.config.json:**

```json
{
  "mode": "testops",
  "debug": false,
  "testops": {
    "api": {
      "token": "your_api_token_here"
    },
    "project": "DEMO",
    "run": {
      "title": "Automated Test Run",
      "complete": true
    }
  }
}
```

---

## Troubleshooting

### Tests not appearing in Qase

**Possible causes:**
- Mode is not set to `testops` (check configuration)
- Invalid or missing API token
- Incorrect project code
- Network connectivity issues

**Solutions:**
1. Verify `qase.mode=testops` in your configuration
2. Check that your API token has the correct permissions
3. Confirm the project code matches your Qase project
4. Enable debug mode: `qase.debug=true` to see detailed logs

### Attachments not uploading

**Possible causes:**
- File path does not exist or is incorrect
- File size exceeds limits
- Insufficient permissions to read the file

**Solutions:**
1. Verify file paths are correct and files exist
2. Check file size (very large files may fail)
3. Enable debug logging to see attachment processing details
4. Ensure the file is accessible by the test process

### Results going to wrong test cases

**Possible causes:**
- QaseID does not match the test case ID in Qase
- Multiple tests using the same QaseID
- Wrong project code in configuration

**Solutions:**
1. Verify QaseID annotations match the test case IDs in your Qase project
2. Check for duplicate QaseIDs across your test suite
3. Confirm the project code in your configuration

### Steps not appearing in test results

**Possible causes:**
- AspectJ weaver is not configured
- Step method is not being called from the test
- Step method does not have the `@Step` annotation

**Solutions:**
1. Verify AspectJ weaver is configured in your `pom.xml` or `build.gradle`
2. Check the AspectJ configuration in the example projects: [examples/junit4/](../../examples/junit4/)
3. Ensure step methods are actually invoked during test execution
4. Verify the `@Step` annotation is present on the method

### Runner conflicts with parameterized tests

**Possible causes:**
- Multiple `@RunWith` annotations or conflicting runners

**Solutions:**
1. Ensure only one `@RunWith(Parameterized.class)` annotation is used per test class
2. If you need multiple runners, consider using the `@Rule` approach or separating test classes
3. Check for conflicts with other JUnit 4 runners in your test class

### Parameterized tests creating duplicate test cases

**Possible causes:**
- Auto-create setting is enabled and each parameter set is creating a new test case

**Solutions:**
1. Check your Qase project settings for auto-create behavior
2. Use a single `@QaseId` for the parameterized test to link all iterations to one test case
3. Configure `testops.run.complete=true` to finalize runs and avoid partial data

---

## See Also

- [README](../../qase-junit4-reporter/README.md) — Configuration reference and setup instructions
- [Attachments Guide](ATTACHMENTS.md) — Detailed guide on working with attachments
- [Steps Guide](STEPS.md) — Advanced step patterns and best practices
- [Upgrade Guide](UPGRADE.md) — Migration guide for upgrading from older versions
- [Examples](../../examples/junit4/) — Complete example projects with Maven and Gradle configurations
