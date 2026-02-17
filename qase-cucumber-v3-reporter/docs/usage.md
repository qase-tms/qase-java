# Qase Integration in Cucumber 3

This guide provides comprehensive instructions for integrating Qase with Cucumber 3 using Gherkin tags and the Qase Java API.

> **Configuration:** For complete configuration reference including all available options, environment variables, and examples, see the reporter [README](../../qase-cucumber-v3-reporter/README.md).

## Table of Contents

**Test Linking**
- [Adding QaseID](#adding-qaseid)
  - [Single ID](#single-id)
  - [Multiple IDs](#multiple-ids)

**Test Metadata**
- [Adding Title](#adding-title)
- [Adding Fields](#adding-fields)
  - [System Fields Reference](#system-fields-reference)
  - [Example](#field-example)
- [Adding Suite](#adding-suite)
  - [Simple Suite](#simple-suite)
  - [Nested Suites](#nested-suites)

**Test Control**
- [Ignoring Tests](#ignoring-tests)

**Enhanced Reporting**
- [Working with Attachments](#working-with-attachments)
  - [Attach File from Path](#attach-file-from-path)
  - [Attach Content from Code](#attach-content-from-code)
  - [Supported MIME Types](#supported-mime-types)
- [Working with Steps](#working-with-steps)
  - [Automatic Step Reporting](#automatic-step-reporting)
  - [Adding Comments in Steps](#adding-comments-in-steps)
  - [Adding Attachments in Steps](#adding-attachments-in-steps)
- [Working with Parameters](#working-with-parameters)

**Execution**
- [Running Tests](#running-tests)
  - [Basic Execution](#basic-execution)
  - [With Environment Variables](#with-environment-variables)
  - [With System Properties](#with-system-properties)
  - [Filtering by Tags](#filtering-by-tags)

**Reference**
- [Complete Examples](#complete-examples)
- [Troubleshooting](#troubleshooting)
- [See Also](#see-also)

---

## Adding QaseID

Link your Cucumber scenarios to existing test cases in Qase using the `@QaseId` tag.

### Single ID

To link a scenario to a single test case, add the `@QaseId` tag with the test case ID:

```gherkin
Feature: Authentication

  @QaseId=123
  Scenario: Successful login
    Given the user is on the login page
    When the user enters valid credentials
    Then the user sees the dashboard
```

### Multiple IDs

To link a scenario to multiple test cases, add multiple `@QaseId` tags:

```gherkin
Feature: Authentication

  @QaseId=1
  @QaseId=2
  Scenario: Login with multiple test cases
    Given the user is on the login page
    When the user enters valid credentials
    Then the user sees the dashboard
```

You can also place multiple tags on the same line:

```gherkin
Feature: Authentication

  @QaseId=1 @QaseId=2
  Scenario: Login with multiple test cases
    Given the user is on the login page
    When the user enters valid credentials
    Then the user sees the dashboard
```

---

## Adding Title

Override the default scenario name with a custom title using the `@QaseTitle` tag:

```gherkin
Feature: Authentication

  @QaseTitle=Successful_login_with_valid_credentials
  Scenario: Login test
    Given the user is on the login page
    When the user enters valid credentials
    Then the user sees the dashboard
```

**Note:** Underscores in the tag value will be converted to spaces in Qase (e.g., `Successful_login_with_valid_credentials` becomes "Successful login with valid credentials").

---

## Adding Fields

Enrich your test cases with additional metadata using the `@QaseFields` tag with JSON format.

### System Fields Reference

The following system fields are available:

| Field            | Description                                  | Example Values                     |
|------------------|----------------------------------------------|------------------------------------|
| `description`    | Detailed test case description               | Any text                           |
| `preconditions`  | Setup requirements before test execution     | Any text                           |
| `postconditions` | Cleanup steps after test execution           | Any text                           |
| `severity`       | Test case severity level                     | `critical`, `major`, `minor`, `trivial` |
| `priority`       | Test case priority                           | `high`, `medium`, `low`            |
| `layer`          | Test layer classification                    | `UI`, `API`, `Unit`, `Integration` |

### Field Example

```gherkin
Feature: Authentication

  @QaseFields={"description":"Verify_login_flow","severity":"critical","priority":"high"}
  Scenario: Login with fields
    Given the user is on the login page
    When the user enters valid credentials
    Then the user sees the dashboard
```

**Note:** Use valid JSON format in the tag value. Underscores in string values will be converted to spaces.

---

## Adding Suite

Organize your scenarios into suites using the `@QaseSuite` tag.

### Simple Suite

For a single suite level:

```gherkin
Feature: Authentication

  @QaseSuite=Authentication
  Scenario: Login test
    Given the user is on the login page
    When the user enters valid credentials
    Then the user sees the dashboard
```

### Nested Suites

For nested suite hierarchies, use tab-separated values:

```gherkin
Feature: Authentication

  @QaseSuite=Authentication\tLogin
  Scenario: Nested suite test
    Given the user is on the login page
    When the user enters valid credentials
    Then the user sees the dashboard
```

This creates a hierarchy: `Authentication > Login`

---

## Ignoring Tests

Exclude a scenario from being reported to Qase using the `@QaseIgnore` tag:

```gherkin
Feature: Authentication

  @QaseIgnore
  Scenario: Ignored test
    Given the user is on the login page
    When the user enters valid credentials
    Then the user sees the dashboard
```

**Note:** The scenario will still execute, but its results will not be sent to Qase.

---

## Working with Attachments

Attach files and content to your test results using the `Qase.attach()` method in your step definitions.

### Attach File from Path

Attach a file from your filesystem:

```java
package com.example.steps;

import io.qase.cucumber3.Qase;
import io.cucumber.java.en.When;

public class AttachmentSteps {

    @When("add screenshot")
    public void addScreenshot() {
        Qase.attach("/path/to/screenshot.png");
    }
}
```

### Attach Content from Code

Attach content generated in your test code:

```java
package com.example.steps;

import io.qase.cucumber3.Qase;
import io.cucumber.java.en.When;

public class AttachmentSteps {

    @When("add log content")
    public void addLogContent() {
        Qase.attach("test-log.txt", "Log content here", "text/plain");
    }

    @When("add byte array content")
    public void addByteArrayContent() {
        byte[] data = "Binary content".getBytes();
        Qase.attach("data.bin", data, "application/octet-stream");
    }
}
```

### Supported MIME Types

Common MIME types for attachments:

| File Type | MIME Type                    | Example File      |
|-----------|------------------------------|-------------------|
| Text      | `text/plain`                 | `log.txt`         |
| JSON      | `application/json`           | `response.json`   |
| XML       | `application/xml`            | `request.xml`     |
| HTML      | `text/html`                  | `report.html`     |
| CSV       | `text/csv`                   | `data.csv`        |
| PNG       | `image/png`                  | `screenshot.png`  |
| JPEG      | `image/jpeg`                 | `photo.jpg`       |
| GIF       | `image/gif`                  | `animation.gif`   |
| PDF       | `application/pdf`            | `report.pdf`      |
| ZIP       | `application/zip`            | `archive.zip`     |

> For more details, see [Attachments Guide](ATTACHMENTS.md).

---

## Working with Steps

Cucumber automatically reports Gherkin steps to Qase. You can enhance step reporting with comments and attachments.

### Automatic Step Reporting

Every Gherkin Given/When/Then step in your feature file is automatically reported to Qase as a test step. No annotations or special configuration required.

**Example feature file:**

```gherkin
Feature: Shopping Cart

  Scenario: Add item to cart
    Given the user is on the product page
    When the user clicks "Add to Cart"
    And the user opens the shopping cart
    Then the cart contains 1 item
```

**In Qase, you will see these steps:**
1. Given the user is on the product page
2. When the user clicks "Add to Cart"
3. And the user opens the shopping cart
4. Then the cart contains 1 item

### Adding Comments in Steps

Add contextual comments to steps using `Qase.comment()` in your step definitions:

```java
package com.example.steps;

import io.qase.cucumber3.Qase;
import io.cucumber.java.en.When;

public class LoginSteps {

    @When("the user logs in with {string}")
    public void loginWith(String username) {
        Qase.comment("Attempting login with: " + username);
        // test implementation
    }

    @When("the user enters {string} and {string}")
    public void enterCredentials(String email, String password) {
        Qase.comment("Email: " + email);
        // test implementation
    }
}
```

### Adding Attachments in Steps

Attach files or content within a step using `Qase.attach()`:

```java
package com.example.steps;

import io.qase.cucumber3.Qase;
import io.cucumber.java.en.Then;

public class ValidationSteps {

    @Then("the login result is {string}")
    public void verifyLoginResult(String result) {
        Qase.comment("Expected result: " + result);
        Qase.attach("screenshot.png", captureScreenshot(), "image/png");
        // test implementation
    }

    private byte[] captureScreenshot() {
        // screenshot capture logic
        return new byte[0];
    }
}
```

The attachment will be associated with the current Gherkin step in Qase.

> For more details, see [Steps Guide](STEPS.md).

---

## Working with Parameters

Use Cucumber's `Scenario Outline` with `Examples` to run parameterized tests.

**Example:**

```gherkin
Feature: Authentication

  @QaseId=10
  Scenario Outline: Login with different credentials
    Given the user is on the login page
    When the user enters "<email>" and "<password>"
    Then the login result is "<result>"

    Examples:
      | email              | password | result  |
      | user1@example.com  | pass1    | success |
      | user2@example.com  | wrong    | failure |
      | invalid            | pass3    | failure |
```

**Behavior:** Each row in the Examples table creates a separate test result in Qase. In this example, three test results will be created:
- Test with email=user1@example.com, password=pass1, result=success
- Test with email=user2@example.com, password=wrong, result=failure
- Test with email=invalid, password=pass3, result=failure

The parameters are automatically included in the test result metadata in Qase.

---

## Running Tests

Execute your Cucumber tests with Qase reporting using Maven or Gradle.

### Basic Execution

**Maven:**
```bash
mvn clean test
```

**Gradle:**
```bash
./gradlew clean test
```

### With Environment Variables

Set configuration via environment variables:

```bash
QASE_MODE=testops \
QASE_TESTOPS_PROJECT=DEMO \
QASE_TESTOPS_API_TOKEN=your_token \
mvn clean test
```

```bash
QASE_MODE=testops \
QASE_TESTOPS_PROJECT=DEMO \
QASE_TESTOPS_API_TOKEN=your_token \
./gradlew clean test
```

### With System Properties

Pass configuration as system properties:

**Maven:**
```bash
mvn clean test \
  -Dqase.mode=testops \
  -Dqase.testops.project=DEMO \
  -Dqase.testops.api.token=your_token
```

**Gradle:**
```bash
./gradlew clean test \
  -Dqase.mode=testops \
  -Dqase.testops.project=DEMO \
  -Dqase.testops.api.token=your_token
```

### Filtering by Tags

Cucumber supports filtering scenarios by tags. This is useful for running specific test subsets:

**Run scenarios with specific QaseId:**
```bash
mvn clean test -Dcucumber.filter.tags="@QaseId=1"
```

**Run scenarios in a specific suite:**
```bash
mvn clean test -Dcucumber.filter.tags="@QaseSuite=Authentication"
```

**Exclude ignored scenarios:**
```bash
mvn clean test -Dcucumber.filter.tags="not @QaseIgnore"
```

**Combine multiple tag filters:**
```bash
mvn clean test -Dcucumber.filter.tags="@QaseSuite=Authentication and not @QaseIgnore"
```

**Alternative:** You can also use `qase.config.json` in your project root to configure settings. See the [README](../../qase-cucumber-v3-reporter/README.md) for details.

---

## Complete Examples

### Feature File Example

```gherkin
Feature: User Management

  @QaseId=101
  @QaseTitle=Create_new_user_account
  @QaseFields={"severity":"critical","priority":"high","layer":"API"}
  @QaseSuite=UserManagement\tRegistration
  Scenario: Create user successfully
    Given the API is available
    When the admin creates a new user with email "user@example.com"
    Then the user is created successfully
    And the user receives a welcome email

  @QaseId=102
  @QaseSuite=UserManagement\tRegistration
  Scenario: Validate email format
    Given the API is available
    When the admin creates a new user with email "invalid-email"
    Then the request fails with error "Invalid email format"

  @QaseId=103
  Scenario Outline: Login with different user types
    Given the API is available
    When the user logs in with "<email>" and "<password>"
    Then the login result is "<result>"

    Examples:
      | email              | password | result  |
      | admin@example.com  | admin123 | success |
      | user@example.com   | user123  | success |
      | guest@example.com  | wrong    | failure |
```

### Step Definitions Example

```java
package com.example.steps;

import io.qase.cucumber3.Qase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import org.junit.jupiter.api.Assertions;

public class UserManagementSteps {

    @Given("the API is available")
    public void apiIsAvailable() {
        Qase.comment("Checking API health endpoint");
        // API health check implementation
    }

    @When("the admin creates a new user with email {string}")
    public void createUser(String email) {
        Qase.comment("Creating user with email: " + email);
        // User creation logic
        Qase.attach("request.json",
                    "{\"email\":\"" + email + "\"}",
                    "application/json");
    }

    @Then("the user is created successfully")
    public void userCreated() {
        Qase.comment("Verifying user creation");
        // Verification logic
        Assertions.assertTrue(true);
    }

    @Then("the user receives a welcome email")
    public void welcomeEmailSent() {
        Qase.comment("Checking email queue");
        // Email verification logic
    }

    @When("the user logs in with {string} and {string}")
    public void loginWith(String email, String password) {
        Qase.comment("Login attempt for: " + email);
        // Login implementation
    }

    @Then("the login result is {string}")
    public void verifyLoginResult(String result) {
        Qase.comment("Expected result: " + result);
        // Result verification
    }

    @Then("the request fails with error {string}")
    public void requestFails(String error) {
        Qase.comment("Validating error message: " + error);
        // Error validation logic
    }
}
```

### Project Structure Example

```text
my-cucumber-project/
├── qase.config.json
├── pom.xml (or build.gradle)
└── src/
    └── test/
        ├── java/
        │   └── com/
        │       └── example/
        │           └── steps/
        │               └── UserManagementSteps.java
        └── resources/
            └── features/
                └── user-management.feature
```

---

## Troubleshooting

### Tests not appearing in Qase

**Problem:** Test results are not visible in Qase TestOps.

**Solution:**
1. Verify `qase.mode` is set to `testops` (not `off`)
2. Check that `qase.testops.project` matches your project code exactly
3. Verify `qase.testops.api.token` is valid and has write permissions
4. Enable debug logging to see API requests:
   ```bash
   mvn clean test -Dqase.debug=true
   ```

### Attachments not uploading

**Problem:** Files are not appearing in test results.

**Solution:**
1. Verify file path is absolute and file exists
2. Check file size (large files may take longer to upload)
3. Ensure MIME type is correct
4. Enable debug logging to see upload status:
   ```bash
   mvn clean test -Dqase.debug=true
   ```

### Results going to wrong test cases

**Problem:** Test results are linked to incorrect test cases.

**Solution:**
1. Verify `@QaseId` tag values match test case IDs in Qase
2. Check for duplicate `@QaseId` tags
3. Ensure tag format is correct: `@QaseId=123` (not `@QaseId(123)`)

### Steps not appearing in results

**Problem:** Gherkin steps are not showing up in test results.

**Solution:**
1. Verify Cucumber plugin is configured in your test runner
2. Check that AspectJ weaver is configured (required for step reporting)
3. See the [examples directory](../../examples/cucumber3/) for complete Maven/Gradle configuration

### Tag syntax errors

**Problem:** Tags are not recognized or causing parse errors.

**Solution:**
1. Use correct tag format: `@QaseId=123` (not `@QaseId(123)` - that's Java annotation syntax)
2. For `@QaseTitle`, use underscores instead of spaces: `@QaseTitle=My_Title`
3. For `@QaseSuite`, use `\t` for nested suites: `@QaseSuite=Suite\tSubSuite`
4. Ensure tags are placed directly above the `Scenario` line (not above `Feature`)

### QaseFields JSON parsing errors

**Problem:** `@QaseFields` tag causes parsing errors.

**Solution:**
1. Ensure JSON is valid: `@QaseFields={"key":"value"}`
2. Use double quotes for JSON keys and values
3. Use underscores instead of spaces in string values: `"severity":"critical"` not `"severity":"very critical"`
4. Test JSON validity in a validator before adding to tag

### Scenario Outline parameters not reported

**Problem:** Parameterized test results are missing or not showing parameter values.

**Solution:**
1. Verify Cucumber version compatibility (v3.x required)
2. Check that `Examples` table is properly formatted
3. Ensure step definitions use parameter placeholders: `{string}`, `{int}`, etc.
4. Verify AspectJ weaver is configured correctly

---

## See Also

- [README](../../qase-cucumber-v3-reporter/README.md) — Configuration reference and setup guide
- [Attachments Guide](ATTACHMENTS.md) — Detailed attachment handling documentation
- [Steps Guide](STEPS.md) — Comprehensive step reporting guide
- [Upgrade Guide](UPGRADE.md) — Migration guide for upgrading between versions
- [Examples](../../examples/cucumber3/) — Complete example projects with Maven and Gradle
