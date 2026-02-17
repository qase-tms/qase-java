# Qase Integration in TestNG

This guide provides comprehensive instructions for using Qase annotations and API with TestNG. Learn how to link test cases, add metadata, work with steps and attachments, and leverage TestNG's powerful testing features with Qase reporting.

> **Configuration:** For complete configuration reference including all available options, environment variables, and examples, see the reporter [README](../../qase-testng-reporter/README.md).

---

## Table of Contents

**Test Linking**
- [Adding QaseID](#adding-qaseid)
  - [Single ID](#single-id)
  - [Multiple IDs](#multiple-ids)

**Test Metadata**
- [Adding Title](#adding-title)
- [Adding Fields](#adding-fields)
  - [System Fields Reference](#system-fields)
  - [Example](#example)
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

Link your test methods to existing test cases in Qase using the `@QaseId` annotation.

### Single ID

To associate a single test case ID with a test method:

```java
package com.example.tests;

import io.qase.commons.annotation.QaseId;
import org.testng.annotations.Test;

public class AuthenticationTests {

    @Test
    @QaseId(123)
    public void testSuccessfulLogin() {
        // Test implementation
        System.out.println("Testing login functionality");
    }
}
```

### Multiple IDs

To link one test method to multiple test cases in Qase, use `@QaseIds`:

```java
package com.example.tests;

import io.qase.commons.annotation.QaseIds;
import org.testng.annotations.Test;

public class AuthenticationTests {

    @Test
    @QaseIds({123, 124, 125})
    public void testLoginVariations() {
        // Single test covering multiple scenarios
        System.out.println("Testing multiple login scenarios");
    }
}
```

This is useful when a single automated test validates multiple test cases defined in Qase.

---

## Adding Title

By default, Qase uses the test method name as the test title. Override this with `@QaseTitle`:

```java
package com.example.tests;

import io.qase.commons.annotation.QaseTitle;
import org.testng.annotations.Test;

public class AuthenticationTests {

    @Test
    @QaseTitle("User can log in with valid email and password")
    public void testLogin() {
        // Test implementation
        System.out.println("Executing login test");
    }
}
```

The custom title appears in Qase TestOps, making test results more readable.

---

## Adding Fields

Enhance test cases with additional metadata using the `@QaseFields` annotation. Fields provide context like severity, priority, and test layer.

### System Fields

The following system fields are supported:

| Field Name | Type | Description | Valid Values |
|------------|------|-------------|--------------|
| `description` | String | Detailed description of the test case | Any text (supports Markdown) |
| `preconditions` | String | Preconditions required before test execution | Any text (supports Markdown) |
| `postconditions` | String | Postconditions after test execution | Any text |
| `severity` | Enum | Severity level of the test case | `blocker`, `critical`, `major`, `normal`, `minor`, `trivial` |
| `priority` | Enum | Priority level of the test case | `high`, `medium`, `low` |
| `layer` | Enum | Test layer or type | `e2e`, `api`, `unit` |

### Example

```java
package com.example.tests;

import io.qase.commons.annotation.QaseFields;
import io.qase.commons.annotation.QaseId;
import io.qase.commons.models.annotation.Field;
import org.testng.annotations.Test;

public class AuthenticationTests {

    @Test
    @QaseId(123)
    @QaseFields({
        @Field(name = "description", value = "Verify user can log in with valid credentials"),
        @Field(name = "preconditions", value = "User account exists in the system"),
        @Field(name = "postconditions", value = "User is logged in and dashboard is displayed"),
        @Field(name = "severity", value = "critical"),
        @Field(name = "priority", value = "high"),
        @Field(name = "layer", value = "e2e")
    })
    public void testSuccessfulLogin() {
        // Test implementation
        System.out.println("Testing login with metadata");
    }
}
```

---

## Adding Suite

Organize tests into suites and sub-suites using the `@QaseSuite` annotation. Suites help structure test results in Qase.

### Simple Suite

Assign a test to a single suite:

```java
package com.example.tests;

import io.qase.commons.annotation.QaseSuite;
import org.testng.annotations.Test;

public class AuthenticationTests {

    @Test
    @QaseSuite("Authentication")
    public void testLogin() {
        // Test implementation
        System.out.println("Testing login in Authentication suite");
    }
}
```

### Nested Suites

Create hierarchical suite structures using the tab character (`\t`) as a separator:

```java
package com.example.tests;

import io.qase.commons.annotation.QaseSuite;
import org.testng.annotations.Test;

public class AuthenticationTests {

    @Test
    @QaseSuite("Authentication\tLogin")
    public void testLoginWithValidCredentials() {
        // Test implementation
        System.out.println("Testing in Authentication > Login sub-suite");
    }

    @Test
    @QaseSuite("Authentication\tLogout")
    public void testLogout() {
        // Test implementation
        System.out.println("Testing in Authentication > Logout sub-suite");
    }
}
```

The `\t` character separates parent suites from child suites, creating a nested hierarchy in Qase.

---

## Ignoring Tests

Exclude specific tests from being reported to Qase using the `@QaseIgnore` annotation. The test will still execute in TestNG, but its results won't be sent to Qase.

```java
package com.example.tests;

import io.qase.commons.annotation.QaseIgnore;
import org.testng.annotations.Test;

public class ExperimentalTests {

    @Test
    @QaseIgnore
    public void testExperimentalFeature() {
        // Test executes but is not reported to Qase
        System.out.println("This test runs but doesn't report to Qase");
    }

    @Test
    public void testStableFeature() {
        // This test is reported normally
        System.out.println("This test is reported to Qase");
    }
}
```

Use `@QaseIgnore` for:
- Work-in-progress tests
- Experimental tests not ready for reporting
- Tests that should run locally but not report to Qase

---

## Working with Attachments

Attach files, screenshots, logs, and other artifacts to test results using the `Qase.attach()` method. Attachments help provide context and evidence for test results.

### Attach File from Path

Attach one or multiple files using file paths:

```java
package com.example.tests;

import io.qase.testng.Qase;
import org.testng.annotations.Test;

public class AttachmentTests {

    @Test
    public void testWithSingleAttachment() {
        // Test implementation
        System.out.println("Executing test");

        // Attach single file
        Qase.attach("logs/test-output.txt");
    }

    @Test
    public void testWithMultipleAttachments() {
        // Test implementation
        System.out.println("Executing test");

        // Attach multiple files
        Qase.attach("logs/test-output.txt", "screenshots/result.png", "data/report.json");
    }
}
```

### Attach Content from Code

Attach content generated in your test without writing to a file:

```java
package com.example.tests;

import io.qase.testng.Qase;
import org.testng.annotations.Test;

public class AttachmentTests {

    @Test
    public void testWithTextContent() {
        // Generate report content
        String reportContent = "Test execution report\nStatus: Passed\nDuration: 2.5s";

        // Attach string content
        Qase.attach("execution-report.txt", reportContent, "text/plain");
    }

    @Test
    public void testWithBinaryContent() {
        // Generate screenshot or binary data
        byte[] screenshotData = captureScreenshot();

        // Attach binary content
        Qase.attach("screenshot.png", screenshotData, "image/png");
    }

    private byte[] captureScreenshot() {
        // Placeholder for screenshot capture
        return new byte[0];
    }
}
```

### Attach to Specific Step

When `Qase.attach()` is called inside a `@Step` method, the attachment is associated with that specific step:

```java
package com.example.tests;

import io.qase.commons.annotation.Step;
import io.qase.testng.Qase;
import org.testng.annotations.Test;

public class StepAttachmentTests {

    @Test
    public void testLoginWithStepAttachments() {
        openLoginPage();
        enterCredentials("user@example.com", "password123");
        submitLoginForm();
    }

    @Step("Open login page")
    private void openLoginPage() {
        System.out.println("Opening login page");
        // Screenshot attached to "Open login page" step
        Qase.attach("login-page.png", captureScreenshot(), "image/png");
    }

    @Step("Enter credentials")
    private void enterCredentials(String email, String password) {
        System.out.println("Entering credentials: " + email);
        // This attachment goes to "Enter credentials" step
        Qase.attach("credentials-log.txt", "Email: " + email, "text/plain");
    }

    @Step("Submit login form")
    private void submitLoginForm() {
        System.out.println("Submitting form");
    }

    private byte[] captureScreenshot() {
        return new byte[0];
    }
}
```

### Supported MIME Types

Common MIME types are automatically detected by file extension:

| File Extension | MIME Type | Description |
|----------------|-----------|-------------|
| `.png` | `image/png` | PNG images |
| `.jpg`, `.jpeg` | `image/jpeg` | JPEG images |
| `.gif` | `image/gif` | GIF images |
| `.txt` | `text/plain` | Plain text files |
| `.json` | `application/json` | JSON files |
| `.xml` | `application/xml` | XML files |
| `.html` | `text/html` | HTML files |
| `.pdf` | `application/pdf` | PDF documents |
| `.log` | `text/plain` | Log files |

> For more attachment strategies and best practices, see [Attachments Guide](ATTACHMENTS.md).

---

## Working with Steps

Break down complex tests into logical steps using the `@Step` annotation. Steps provide detailed execution flow in Qase test results.

### Using @Step Annotation

Define steps by annotating methods with `@Step`:

```java
package com.example.tests;

import io.qase.commons.annotation.Step;
import io.qase.testng.Qase;
import org.testng.annotations.Test;

public class StepTests {

    @Test
    public void testUserRegistration() {
        openRegistrationPage();
        fillRegistrationForm("John Doe", "john@example.com");
        submitForm();
        verifySuccessMessage();
    }

    @Step("Open registration page")
    private void openRegistrationPage() {
        System.out.println("Navigating to registration page");
        Qase.attach("registration-page.png", new byte[0], "image/png");
    }

    @Step("Fill registration form with name: {0} and email: {1}")
    private void fillRegistrationForm(String name, String email) {
        System.out.println("Filling form: " + name + ", " + email);
    }

    @Step("Submit registration form")
    private void submitForm() {
        System.out.println("Clicking submit button");
    }

    @Step("Verify success message is displayed")
    private void verifySuccessMessage() {
        System.out.println("Checking for success message");
    }
}
```

**Important:** Steps require AspectJ weaving to be configured in your project. See the [README](../../qase-testng-reporter/README.md) for AspectJ setup instructions.

### Nested Steps

Steps can call other steps, creating a hierarchical step structure:

```java
package com.example.tests;

import io.qase.commons.annotation.Step;
import org.testng.annotations.Test;

public class NestedStepTests {

    @Test
    public void testCheckoutFlow() {
        addItemsToCart();
        proceedToCheckout();
        completePayment();
    }

    @Step("Add items to cart")
    private void addItemsToCart() {
        selectProduct("Laptop");
        selectProduct("Mouse");
        System.out.println("Items added to cart");
    }

    @Step("Select product: {0}")
    private void selectProduct(String productName) {
        System.out.println("Selecting product: " + productName);
        addToCart();
    }

    @Step("Add to cart")
    private void addToCart() {
        System.out.println("Clicking 'Add to Cart' button");
    }

    @Step("Proceed to checkout")
    private void proceedToCheckout() {
        System.out.println("Going to checkout page");
    }

    @Step("Complete payment")
    private void completePayment() {
        enterPaymentDetails();
        confirmOrder();
    }

    @Step("Enter payment details")
    private void enterPaymentDetails() {
        System.out.println("Entering payment information");
    }

    @Step("Confirm order")
    private void confirmOrder() {
        System.out.println("Clicking confirm button");
    }
}
```

Nested steps appear as a tree structure in Qase, showing the complete test execution flow.

> For more details on step strategies and patterns, see [Steps Guide](STEPS.md).

---

## Working with Parameters

TestNG's parameterization features work seamlessly with Qase reporting. Each parameter combination creates a separate test result in Qase.

### Using @DataProvider

```java
package com.example.tests;

import io.qase.commons.annotation.QaseId;
import io.qase.commons.annotation.QaseTitle;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ParameterizedTests {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
            { "user1@example.com", "password123" },
            { "user2@example.com", "securePass456" },
            { "admin@example.com", "adminPass789" }
        };
    }

    @Test(dataProvider = "loginData")
    @QaseId(456)
    @QaseTitle("Login test with multiple credentials")
    public void testLoginWithDifferentUsers(String email, String password) {
        System.out.println("Testing login with: " + email);
        // Test implementation
        performLogin(email, password);
        verifyDashboard();
    }

    private void performLogin(String email, String password) {
        System.out.println("Logging in with credentials");
    }

    private void verifyDashboard() {
        System.out.println("Verifying dashboard is displayed");
    }
}
```

Each data row produces a separate test result in Qase. The reporter automatically captures parameter values and includes them in the test result details.

### DataProvider with Complex Objects

```java
package com.example.tests;

import io.qase.commons.annotation.QaseId;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ComplexParameterizedTests {

    @DataProvider(name = "userScenarios")
    public Object[][] userScenarios() {
        return new Object[][] {
            { "Standard User", "standard", true },
            { "Admin User", "admin", true },
            { "Guest User", "guest", false }
        };
    }

    @Test(dataProvider = "userScenarios")
    @QaseId(789)
    public void testUserPermissions(String userName, String role, boolean canEdit) {
        System.out.println("Testing permissions for: " + userName);
        System.out.println("Role: " + role + ", Can edit: " + canEdit);
        // Test implementation
    }
}
```

---

## Running Tests

Execute your TestNG tests with Qase reporting using Maven or Gradle.

### Basic Execution

#### Maven

```bash
mvn clean test
```

#### Gradle

```bash
./gradlew clean test
```

By default, tests run with the configuration specified in `qase.config.json` or other configuration sources.

### With Environment Variables

Override configuration using environment variables:

#### Maven

```bash
QASE_MODE=testops \
QASE_TESTOPS_PROJECT=DEMO \
QASE_TESTOPS_API_TOKEN=your_api_token_here \
mvn clean test
```

#### Gradle

```bash
QASE_MODE=testops \
QASE_TESTOPS_PROJECT=DEMO \
QASE_TESTOPS_API_TOKEN=your_api_token_here \
./gradlew clean test
```

### With System Properties

Pass configuration as system properties (Java system properties):

#### Maven

```bash
mvn clean test \
  -Dqase.mode=testops \
  -Dqase.testops.project=DEMO \
  -Dqase.testops.api.token=your_api_token_here
```

#### Gradle

```bash
./gradlew clean test \
  -Dqase.mode=testops \
  -Dqase.testops.project=DEMO \
  -Dqase.testops.api.token=your_api_token_here
```

### Targeting Specific Tests

Run individual test classes or methods:

#### Maven

```bash
# Run specific test class
mvn test -Dtest=AuthenticationTests

# Run specific test method
mvn test -Dtest=AuthenticationTests#testSuccessfulLogin

# Run multiple tests
mvn test -Dtest=AuthenticationTests,RegistrationTests
```

#### Gradle

```bash
# Run specific test class
./gradlew test --tests AuthenticationTests

# Run specific test method
./gradlew test --tests AuthenticationTests.testSuccessfulLogin

# Run tests matching pattern
./gradlew test --tests "*Authentication*"
```

### TestNG Suite Files

Run tests using TestNG XML suite files:

#### Maven

```bash
mvn test -DsuiteXmlFile=testng.xml
```

#### Gradle

```bash
./gradlew test -PsuiteXmlFile=testng.xml
```

**Example testng.xml:**

```xml
<!DOCTYPE suite SYSTEM "https://testng.org/testng-1.0.dtd">
<suite name="Qase Test Suite">
    <test name="Authentication Tests">
        <classes>
            <class name="com.example.tests.AuthenticationTests"/>
        </classes>
    </test>
</suite>
```

### Configuration Options

Common configuration options:

| Option | Description | Values |
|--------|-------------|--------|
| `mode` | Reporter mode | `testops`, `report`, `off` |
| `testops.project` | Qase project code | Project code (e.g., `DEMO`) |
| `testops.api.token` | API token for authentication | Your API token |
| `testops.run.id` | Existing run ID to append results | Run ID number |
| `testops.run.title` | Custom run title | Any string |
| `environment` | Environment slug | Environment identifier |
| `debug` | Enable debug logging | `true`, `false` |

These options can be set via environment variables (prefix with `QASE_`), system properties (prefix with `qase.`), or in `qase.config.json`.

---

## Complete Examples

### Full Test Class Example

This example demonstrates a complete test class combining multiple Qase features:

```java
package com.example.tests;

import io.qase.commons.annotation.*;
import io.qase.commons.models.annotation.Field;
import io.qase.testng.Qase;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTests {

    @Test
    @QaseId(123)
    @QaseTitle("Successful login with valid credentials")
    @QaseFields({
        @Field(name = "description", value = "Verify that a user can successfully log in with valid email and password"),
        @Field(name = "preconditions", value = "User account exists in the system"),
        @Field(name = "postconditions", value = "User is logged in and redirected to dashboard"),
        @Field(name = "severity", value = "critical"),
        @Field(name = "priority", value = "high"),
        @Field(name = "layer", value = "e2e")
    })
    @QaseSuite("Authentication\tLogin\tPositive")
    public void testSuccessfulLogin() {
        openLoginPage();
        enterCredentials("user@example.com", "ValidPassword123");
        clickLoginButton();
        verifyDashboardDisplayed();
    }

    @Test
    @QaseId(124)
    @QaseTitle("Login fails with invalid password")
    @QaseFields({
        @Field(name = "description", value = "Verify that login fails with incorrect password"),
        @Field(name = "severity", value = "major"),
        @Field(name = "priority", value = "high"),
        @Field(name = "layer", value = "e2e")
    })
    @QaseSuite("Authentication\tLogin\tNegative")
    public void testLoginWithInvalidPassword() {
        openLoginPage();
        enterCredentials("user@example.com", "WrongPassword");
        clickLoginButton();
        verifyErrorMessage("Invalid credentials");
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][] {
            { "", "password123", "Email is required" },
            { "invalid-email", "password123", "Invalid email format" },
            { "user@example.com", "", "Password is required" }
        };
    }

    @Test(dataProvider = "invalidCredentials")
    @QaseId(125)
    @QaseTitle("Login validation with invalid inputs")
    @QaseFields({
        @Field(name = "severity", value = "normal"),
        @Field(name = "priority", value = "medium"),
        @Field(name = "layer", value = "e2e")
    })
    @QaseSuite("Authentication\tLogin\tValidation")
    public void testLoginValidation(String email, String password, String expectedError) {
        openLoginPage();
        enterCredentials(email, password);
        clickLoginButton();
        verifyErrorMessage(expectedError);
    }

    @Step("Open login page")
    private void openLoginPage() {
        System.out.println("Navigating to login page");
        byte[] screenshot = captureScreenshot();
        Qase.attach("login-page.png", screenshot, "image/png");
    }

    @Step("Enter credentials - Email: {0}")
    private void enterCredentials(String email, String password) {
        System.out.println("Entering email: " + email);
        System.out.println("Entering password: " + password.replaceAll(".", "*"));
        Qase.attach("credentials.txt", "Email: " + email, "text/plain");
    }

    @Step("Click login button")
    private void clickLoginButton() {
        System.out.println("Clicking login button");
    }

    @Step("Verify dashboard is displayed")
    private void verifyDashboardDisplayed() {
        System.out.println("Verifying dashboard");
        boolean dashboardVisible = true; // Actual verification logic
        Assert.assertTrue(dashboardVisible, "Dashboard should be visible");
        byte[] screenshot = captureScreenshot();
        Qase.attach("dashboard.png", screenshot, "image/png");
    }

    @Step("Verify error message: {0}")
    private void verifyErrorMessage(String expectedMessage) {
        System.out.println("Verifying error: " + expectedMessage);
        String actualMessage = "Invalid credentials"; // Actual verification logic
        Assert.assertEquals(actualMessage, expectedMessage, "Error message should match");
    }

    private byte[] captureScreenshot() {
        // Placeholder for screenshot capture logic
        return new byte[0];
    }
}
```

### Project Structure Example

Recommended project structure for TestNG with Qase:

```
project-root/
├── src/
│   └── test/
│       └── java/
│           └── com/
│               └── example/
│                   └── tests/
│                       ├── LoginTests.java
│                       ├── RegistrationTests.java
│                       └── DashboardTests.java
├── testng.xml
├── qase.config.json
└── pom.xml (or build.gradle)
```

**qase.config.json:**

```json
{
  "mode": "testops",
  "fallback": "report",
  "debug": false,
  "testops": {
    "project": "DEMO",
    "api": {
      "token": "your_api_token_here"
    },
    "run": {
      "title": "TestNG Regression",
      "description": "Automated regression tests",
      "complete": true
    }
  },
  "report": {
    "driver": "local",
    "connection": {
      "local": {
        "path": "./build/qase-results",
        "format": "json"
      }
    }
  },
  "environment": "staging"
}
```

---

## Troubleshooting

### Tests Not Appearing in Qase

**Symptoms:** Tests execute successfully but don't appear in Qase TestOps.

**Solutions:**
1. Verify `mode` is set to `testops` in configuration
2. Check API token permissions (must have write access to the project)
3. Verify project code matches your Qase project
4. Enable debug logging: set `debug: true` in `qase.config.json`
5. Check console output for error messages during test execution

### Attachments Not Uploading

**Symptoms:** `Qase.attach()` calls don't result in attachments in Qase.

**Solutions:**
1. Verify file paths are correct and files exist
2. Check file size limits (default max size may be exceeded)
3. Enable debug logging to see attachment processing details
4. Verify MIME type is correctly specified for binary content
5. Ensure attachment is called within test method or step method

### Results Going to Wrong Test Cases

**Symptoms:** Test results are reported to incorrect test case IDs in Qase.

**Solutions:**
1. Verify `@QaseId` values match test case IDs in Qase
2. Check for duplicate `@QaseId` annotations across different tests
3. Verify project code configuration matches target project
4. Review test execution logs for ID mapping

### Steps Not Appearing in Test Results

**Symptoms:** `@Step` annotations don't produce step information in Qase.

**Solutions:**
1. Verify AspectJ weaver is configured in Maven/Gradle (see README for setup)
2. Check that step methods have `@Step` annotation
3. Ensure step methods are actually called during test execution
4. Verify AspectJ version compatibility with reporter version
5. Check for AspectJ warnings in build output

### DataProvider Tests Creating Duplicate Cases

**Symptoms:** Each parameterized test execution creates a new test case instead of linking to existing one.

**Solutions:**
1. Ensure `@QaseId` is present on the test method
2. Check auto-create settings in reporter configuration
3. Verify that test case ID exists in Qase before execution
4. Review `testops.run.complete` setting in configuration

### TestNG Listener Not Registered

**Symptoms:** Reporter doesn't start, no Qase-related output in logs.

**Solutions:**
1. Verify reporter dependency is in your `pom.xml` or `build.gradle`
2. Check that `META-INF/services` files are not corrupted in JAR
3. For manual registration, add listener to `testng.xml`:
```xml
<listeners>
    <listener class-name="io.qase.testng.QaseTestngListener"/>
</listeners>
```
4. Verify ServiceLoader mechanism is working (check for SPI files in classpath)
5. Enable TestNG verbose logging: `-verbose:10`

### Debug Mode

Enable debug logging for detailed troubleshooting:

**In qase.config.json:**
```json
{
  "debug": true
}
```

**Via environment variable:**
```bash
QASE_DEBUG=true mvn test
```

**Via system property:**
```bash
mvn test -Dqase.debug=true
```

Debug output includes:
- Configuration loading details
- Test case ID mapping
- Attachment processing
- API communication logs
- Step execution tracking

---

## See Also

- [README](../../qase-testng-reporter/README.md) — Configuration reference and setup instructions
- [Attachments Guide](ATTACHMENTS.md) — Advanced attachment strategies and patterns
- [Steps Guide](STEPS.md) — Detailed guide on working with steps
- [Upgrade Guide](UPGRADE.md) — Migration guide for upgrading from previous versions
- [Examples](../../examples/testng/) — Complete example projects with Maven and Gradle
- [Qase Documentation](https://docs.qase.io) — Official Qase TestOps documentation
