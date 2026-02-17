# Test Steps in Cucumber 3

This guide covers how test steps are reported to Qase when using the Cucumber 3 reporter. Cucumber's Gherkin steps are automatically captured — no additional annotations required.

---

## Overview

Cucumber 3 automatically reports every Given/When/Then step in your feature files as a test step in Qase:

- **No @Step annotation needed** — The `QaseEventListener` handles step reporting via Cucumber's event system
- **BDD keywords included** — Step titles include the keyword: "Given the user is logged in", "When the user clicks checkout"
- **Automatic status mapping** — Cucumber step status (PASSED/FAILED/SKIPPED) maps to Qase step status
- **Data tables captured** — Data table content is captured in step data (since v4.1.4)
- **BDD keywords preserved** — Keywords (Given/When/Then/And/But) are included in step titles (since v4.1.5)

---

## Automatic Step Reporting

Every Gherkin step in your feature file becomes a test step in Qase. No annotations or special configuration required.

### Feature File Example

```gherkin
Feature: User Login

  @QaseId=42
  Scenario: Successful login with valid credentials
    Given the user is on the login page
    When the user enters "admin@example.com" and "password123"
    And the user clicks the login button
    Then the user should see the dashboard
    And the welcome message should say "Hello, Admin"
```

### Step Definitions

```java
package com.example.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;

public class LoginSteps {
    @Given("the user is on the login page")
    public void userOnLoginPage() {
        // Navigate to login page
    }

    @When("the user enters {string} and {string}")
    public void userEntersCredentials(String email, String password) {
        // Enter credentials
    }

    @And("the user clicks the login button")
    public void clickLoginButton() {
        // Click submit
    }

    @Then("the user should see the dashboard")
    public void verifyDashboard() {
        // Assert dashboard visible
    }

    @And("the welcome message should say {string}")
    public void verifyWelcomeMessage(String expectedMessage) {
        // Assert welcome message
    }
}
```

### What Appears in Qase

```
Steps:
- Given the user is on the login page                            [PASSED]
- When the user enters "admin@example.com" and "password123"     [PASSED]
- And the user clicks the login button                           [PASSED]
- Then the user should see the dashboard                         [PASSED]
- And the welcome message should say "Hello, Admin"              [PASSED]
```

**Note:** Each Gherkin step becomes a separate step in the Qase test result. The BDD keyword (Given, When, Then, And, But) is preserved in the step title.

---

## Adding Comments in Steps

Use `Qase.comment()` to add contextual comments to the current test case. Comments provide additional context visible in the Qase test result:

```java
package com.example.steps;

import io.qase.cucumber3.Qase;
import io.cucumber.java.en.When;

public class ActionSteps {
    @When("the user performs the search for {string}")
    public void performSearch(String query) {
        Qase.comment("Searching for: " + query);
        // Perform search action
        int resultCount = executeSearch(query);
        Qase.comment("Found " + resultCount + " results");
    }

    private int executeSearch(String query) {
        // Your search logic
        return 0;
    }
}
```

Comments are added to the test case and visible in the test result details in Qase.

---

## Adding Attachments in Steps

Use `Qase.attach()` within step definitions to attach files, screenshots, or data to specific Gherkin steps:

```java
package com.example.steps;

import io.qase.cucumber3.Qase;
import io.cucumber.java.en.Then;

public class VerificationSteps {
    @Then("the page should display the correct content")
    public void verifyPageContent() {
        byte[] screenshot = captureScreenshot();
        Qase.attach("page-state.png", screenshot, "image/png");

        String pageHtml = getPageSource();
        Qase.attach("page-source.html", pageHtml, "text/html");
    }

    private byte[] captureScreenshot() {
        // Your screenshot capture logic
        return new byte[0];
    }

    private String getPageSource() {
        // Your page source retrieval logic
        return "";
    }
}
```

**Note:** When `Qase.attach()` is called inside a Cucumber step definition, the attachment is associated with that specific Gherkin step. For more details on attachments, see [Attachments Guide](ATTACHMENTS.md).

---

## Data Tables in Steps

Cucumber data tables are automatically captured in Qase step data (available since v4.1.4 for Cucumber v5-v7):

### Feature with Data Table

```gherkin
Feature: User Management

  @QaseId=55
  Scenario: Create users from table
    Given the following users exist:
      | name     | email              | role    |
      | Alice    | alice@example.com  | admin   |
      | Bob      | bob@example.com    | user    |
      | Charlie  | charlie@example.com| viewer  |
    When each user logs in
    Then each user should see their role-specific dashboard
```

### Step Definition with Data Table

```java
package com.example.steps;

import io.cucumber.java.en.Given;
import io.cucumber.datatable.DataTable;
import java.util.List;
import java.util.Map;

public class UserSteps {
    @Given("the following users exist:")
    public void createUsers(DataTable dataTable) {
        List<Map<String, String>> users = dataTable.asMaps();
        for (Map<String, String> user : users) {
            String name = user.get("name");
            String email = user.get("email");
            String role = user.get("role");
            // Create user...
        }
    }
}
```

**Note:** The data table content is automatically captured in the step's `inputData` field in Qase, providing visibility into the test data used.

Data table capture is available in Cucumber v5, v6, and v7 reporters. Cucumber v3 and v4 reporters do not capture data tables.

---

## Step Status Mapping

Cucumber step status is mapped to Qase step status as follows:

| Cucumber Status | Qase Step Status | Description |
|---|---|---|
| PASSED | PASSED | Step definition executed without exception |
| FAILED | FAILED | Step definition threw an exception |
| PENDING | BLOCKED | Step definition has PendingException |
| UNDEFINED | BLOCKED | No matching step definition found |
| AMBIGUOUS | BLOCKED | Multiple matching step definitions found |
| SKIPPED | BLOCKED | Step was skipped (previous step failed) |

Step status in Cucumber is determined by the step definition execution. If the step definition throws an exception (including assertion failures), the step is marked FAILED. If it completes normally, it's PASSED.

---

## Using @Step in Cucumber (Advanced)

While automatic Gherkin step reporting is sufficient for most use cases, you can use the `@Step` annotation for helper methods called from step definitions. This creates hierarchical sub-steps:

```java
package com.example.steps;

import io.qase.cucumber3.Qase;
import io.qase.commons.annotation.Step;
import io.cucumber.java.en.When;

public class ComplexSteps {
    @When("the user completes the multi-step process")
    public void completeProcess() {
        // Gherkin step is auto-reported
        fillForm();    // Additional @Step sub-steps
        submitForm();  // Additional @Step sub-steps
    }

    @Step("Fill out the form fields")
    private void fillForm() {
        // This creates a child step under the Gherkin step
    }

    @Step("Submit the form")
    private void submitForm() {
        // This creates a child step under the Gherkin step
    }
}
```

**Important:** Using `@Step` annotation in Cucumber requires AspectJ weaver configuration (same as JUnit/TestNG). However, AspectJ is NOT required for automatic Gherkin step reporting — only for `@Step` annotation on helper methods.

For most Cucumber tests, automatic Gherkin step reporting is sufficient and does not require AspectJ.

---

## Best Practices

### 1. Let Gherkin drive step structure

Write clear, descriptive Gherkin steps. The step titles in Qase come directly from your feature files:

```gherkin
# Good: Clear, descriptive step text
Given the user is logged in with admin privileges
When the user navigates to the settings page
Then the advanced settings section should be visible

# Avoid: Vague or generic step text
Given setup
When action
Then verify
```

### 2. Use Qase.comment() for context

Add comments in step definitions to explain what's happening or record intermediate values:

```java
@When("the user updates profile with {string}")
public void updateProfile(String newName) {
    Qase.comment("Updating profile to: " + newName);
    // Update logic
    Qase.comment("Profile updated successfully");
}
```

### 3. Attach evidence in verification steps

Add screenshots or data in `@Then` step definitions to document the verification result:

```java
@Then("the dashboard displays user statistics")
public void verifyDashboard() {
    Qase.attach("dashboard.png", captureScreenshot(), "image/png");
    // Verification logic
}
```

### 4. Use @After hooks for failure captures

Capture screenshots or logs in `@After` hooks to attach evidence when scenarios fail:

```java
@After
public void afterScenario(Scenario scenario) {
    if (scenario.isFailed()) {
        Qase.attach("failure-screenshot.png", captureScreenshot(), "image/png");
        Qase.attach("console.log", getBrowserLogs(), "text/plain");
    }
}
```

### 5. Keep step definitions focused

Each step definition should do one thing. Cucumber enforces this naturally through Gherkin syntax:

```java
// Good: Focused step definition
@When("the user clicks the login button")
public void clickLoginButton() {
    // Single action
}

// Avoid: Combining multiple actions
@When("the user enters credentials and logs in")
public void enterCredentialsAndLogin() {
    // Multiple actions - should be separate steps
}
```

### 6. Use data tables for parameterized data

Data tables are automatically captured in Qase step data, providing test data visibility:

```gherkin
Given the following products are in stock:
  | name     | quantity | price |
  | Widget A | 50       | 9.99  |
  | Widget B | 100      | 14.99 |
```

### 7. Avoid @Step annotation unless necessary

Automatic Gherkin step reporting covers most needs. Only use `@Step` for helper method sub-steps when you need finer-grained reporting:

```java
// Most cases: Automatic Gherkin step reporting is sufficient
@When("the user completes checkout")
public void completeCheckout() {
    // Implementation
}

// Advanced: Use @Step for complex helper methods
@When("the user completes checkout")
public void completeCheckout() {
    enterShippingAddress();  // @Step method
    selectPaymentMethod();   // @Step method
    confirmOrder();          // @Step method
}
```

---

## Troubleshooting

### Steps Not Appearing in Qase

**Problem:** Gherkin steps are not showing up in test results.

**Solution:**
1. Verify `QaseEventListener` is registered as a Cucumber plugin
2. For Maven: Add `--plugin io.qase.cucumber3.QaseEventListener` in `@CucumberOptions` or Surefire configuration
3. Enable debug logging: `"debug": true` in qase.config.json
4. Check that `qase.mode` is set to `testops` (not `off`)

### Step Titles Missing BDD Keywords

**Problem:** Step titles in Qase don't include Given/When/Then keywords.

**Solution:**
- BDD keyword prefix (Given/When/Then/And/But) was added in v4.1.5. Ensure you're using v4.1.5 or later.
- Earlier versions show only the step text without the keyword.
- Check your reporter version in pom.xml or build.gradle.

### Data Tables Not Captured

**Problem:** Data table content is not visible in Qase step data.

**Solution:**
- Data table capture was added in v4.1.4. Ensure you're using v4.1.4 or later.
- Only available in Cucumber v5, v6, and v7 reporters. Not supported in v3 and v4.
- Verify your Cucumber version matches the reporter version.

### @Step Sub-Steps Not Appearing

**Problem:** Helper methods with @Step annotation are not creating sub-steps.

**Solution:**
- `@Step` annotation requires AspectJ weaver. Verify aspectjweaver is configured as javaagent:
  ```bash
  -javaagent:path/to/aspectjweaver.jar
  ```
- Check your Maven Surefire or Gradle test configuration includes the AspectJ weaver.
- Note: Automatic Gherkin step reporting does NOT require AspectJ — only @Step annotation does.

### Wrong Step Status

**Problem:** Step status in Qase doesn't match expected value.

**Solution:**
- Cucumber maps PENDING/UNDEFINED/AMBIGUOUS/SKIPPED to BLOCKED in Qase. This is by design.
- If a step is UNDEFINED (no matching step definition), it's marked BLOCKED, not FAILED.
- If a previous step fails, subsequent steps are SKIPPED and mapped to BLOCKED.
- Only steps with matching definitions that throw exceptions are marked FAILED.

### Steps Missing from Test Results

**Problem:** Some steps appear in feature files but not in Qase results.

**Solution:**
1. Check if the scenario was skipped or filtered out by tags
2. Verify all steps have matching step definitions
3. Enable debug logging to see which steps are being processed
4. Check if the test execution reached those steps (earlier step failure causes skip)

---

## See Also

- [Usage Guide](usage.md) — Overview of all reporter features
- [Attachments Guide](ATTACHMENTS.md) — Attaching files and content to test results
- [Upgrade Guide](UPGRADE.md) — Migration from v3 to v4
