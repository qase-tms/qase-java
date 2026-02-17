# Upgrade Guide: Cucumber 3 Reporter

This guide covers migration from Qase Java Reporter v3 to v4 for the Cucumber 3 reporter.

---

## Version History

| Version Range | Key Changes |
|---|---|
| v4.0.0 - v4.1.32 (current) | New architecture, v2 API, qase.config.json, new annotations |
| v3.x (v3.2.1 last) | Legacy architecture, v1 API, env-only configuration |

---

## Breaking Changes

1. **Maven artifact ID renamed**: `qase-cucumber3-jvm` → `qase-cucumber-v3-reporter`
2. **Configuration completely changed**: Environment-variable-only → `qase.config.json` + env vars + system properties (priority order)
3. **Annotation package renamed**: `io.qase.api.annotation.*` → `io.qase.commons.annotation.*`
4. **Attachment API replaced**: `Attachments.addAttachmentsToCurrentContext(File...)` → `Qase.attach(...)` static methods
5. **New `Qase` utility class**: `import io.qase.cucumber3.Qase` for attachments and comments
6. **Deprecated annotations renamed**: `@CaseId`/`@CaseTitle` (still supported) → prefer `@QaseId`/`@QaseTitle`
7. **New annotations added**: `@QaseFields`, `@QaseIgnore`, `@QaseSuite`, `@QaseIds`
8. **Guice DI removed**: No more custom Guice modules or `*Module.java` / `*ApiConfigurer.java` classes
9. **Logging changed**: SLF4J/Logback removed, replaced with internal logger (since v4.1.1)
10. **API version**: Always v2 API (`useV2` option removed in v4.1.0)
11. **Nested steps supported**: v3 had flat steps only, v4 supports parent-child step nesting
12. **Bulk mode changed**: `QASE_USE_BULK` env var removed, always batched (configurable batch size)

---

## Migration Steps

### Step 1: Update Maven/Gradle Dependency

**Maven Before (v3):**
```xml
<dependency>
    <groupId>io.qase</groupId>
    <artifactId>qase-cucumber3-jvm</artifactId>
    <version>3.2.1</version>
    <scope>test</scope>
</dependency>
```

**Maven After (v4):**
```xml
<dependency>
    <groupId>io.qase</groupId>
    <artifactId>qase-cucumber-v3-reporter</artifactId>
    <version>4.1.32</version>
    <scope>test</scope>
</dependency>
```

**Gradle Before (v3):**
```groovy
testImplementation 'io.qase:qase-cucumber3-jvm:3.2.1'
```

**Gradle After (v4):**
```groovy
testImplementation 'io.qase:qase-cucumber-v3-reporter:4.1.32'
```

### Step 2: Update Configuration

**Before (v3 - environment variables only):**
```bash
QASE_ENABLE=true
QASE_API_TOKEN=your_token
QASE_PROJECT_CODE=DEMO
QASE_RUN_ID=123
QASE_URL=https://api.qase.io/v1
QASE_USE_BULK=true
QASE_RUN_NAME=My Test Run
```

**After (v4 - qase.config.json + env vars):**
```json
{
  "mode": "testops",
  "fallback": "report",
  "debug": false,
  "testops": {
    "project": "DEMO",
    "api": {
      "token": "your_token",
      "host": "https://api.qase.io/v2"
    },
    "run": {
      "title": "My Test Run",
      "complete": true
    }
  }
}
```

**Environment variable mapping:**

| v3 Variable | v4 Variable | Notes |
|---|---|---|
| `QASE_ENABLE=true` | `QASE_MODE=testops` | Value changed |
| `QASE_API_TOKEN` | `QASE_TESTOPS_API_TOKEN` | Renamed |
| `QASE_PROJECT_CODE` | `QASE_TESTOPS_PROJECT` | Renamed |
| `QASE_RUN_ID` | `QASE_TESTOPS_RUN_ID` | Renamed |
| `QASE_RUN_NAME` | `QASE_TESTOPS_RUN_TITLE` | Renamed |
| `QASE_URL` | `QASE_TESTOPS_API_HOST` | Renamed |
| `QASE_USE_BULK` | *(removed)* | Always batched in v4 |

### Step 3: Update Imports

**Import mapping:**

| v3 Import | v4 Import |
|---|---|
| `import io.qase.api.annotation.Step;` | `import io.qase.commons.annotation.Step;` |
| `import io.qase.api.StepStorage;` | `import io.qase.commons.StepStorage;` |
| `import io.qase.api.services.Attachments;` | Use `io.qase.cucumber3.Qase` instead |

**Note:** Cucumber uses Gherkin tags for test metadata (`@QaseId=123` in feature files), not Java annotations. Imports are only needed if using `@Step` annotation in helper methods.

### Step 4: Update Attachment Code

**Before (v3):**
```java
import io.qase.api.services.Attachments;
import java.io.File;

// v3: Attach files
Attachments.addAttachmentsToCurrentContext(new File("screenshot.png"));
```

**After (v4):**
```java
import io.qase.cucumber3.Qase;

// v4: Attach files
Qase.attach("screenshot.png");

// v4: Attach content from memory
Qase.attach("report.txt", "Content here", "text/plain");

// v4: Attach binary content
Qase.attach("image.png", imageBytes, "image/png");
```

---

## Framework-Specific Migration Notes

### Cucumber Event Listener Changes

- **v3:** `QaseEventListener implements Formatter` (Cucumber 3 API: `cucumber.api.event.*`)
- **v4:** `QaseEventListener implements ConcurrentEventListener` (Cucumber 3 API: `io.cucumber.plugin.event.*`)

### New Features in v4

**BDD Keywords in Step Titles (since v4.1.5):**
- v4 adds Given/When/Then/And keywords as prefixes in step titles
- Makes step reports more readable and BDD-aligned

**Data Table Capture (since v4.1.4):**
- Data tables in Gherkin steps are automatically captured
- Stored in step's `inputData` field in test results

### Gherkin Tag Format

**QaseId tag format (same in v3 and v4):**
```gherkin
@QaseId=42
Scenario: User can login with valid credentials
  Given the user is on the login page
  When the user enters valid credentials
  Then the user should see the dashboard
```

### Metadata via Gherkin Tags vs Java Annotations

- Cucumber metadata is managed via Gherkin tags in feature files
- Java annotations (`@QaseId`, `@QaseTitle`) are **not** used in Cucumber tests
- `@Step` annotation can be used in helper methods (requires AspectJ), but Gherkin steps are reported automatically

---

## Before/After Complete Example

### Feature File Example

**Feature file (same syntax in v3 and v4):**
```gherkin
Feature: User Authentication

  @QaseId=42
  Scenario: Successful login
    Given the user is on the login page
    When the user enters username "admin" and password "secret"
    Then the user should see the dashboard
    And the welcome message should display "Welcome, admin"
```

### Step Definition Example

**Before (v3):**
```java
package com.example.steps;

import io.qase.api.services.Attachments;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import java.io.File;

public class LoginSteps {

    @Given("the user is on the login page")
    public void openLoginPage() {
        // Navigate to login page
    }

    @When("the user enters username {string} and password {string}")
    public void enterCredentials(String username, String password) {
        // Enter credentials
    }

    @Then("the user should see the dashboard")
    public void verifyDashboard() {
        // Verify dashboard
        Attachments.addAttachmentsToCurrentContext(new File("screenshot.png"));
    }

    @Then("the welcome message should display {string}")
    public void verifyWelcomeMessage(String message) {
        // Verify message
    }
}
```

**After (v4):**
```java
package com.example.steps;

import io.qase.cucumber3.Qase;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class LoginSteps {

    @Given("the user is on the login page")
    public void openLoginPage() {
        // Navigate to login page
    }

    @When("the user enters username {string} and password {string}")
    public void enterCredentials(String username, String password) {
        // Enter credentials
        Qase.comment("Logging in as: " + username);
    }

    @Then("the user should see the dashboard")
    public void verifyDashboard() {
        // Verify dashboard
        Qase.attach("screenshot.png");
    }

    @Then("the welcome message should display {string}")
    public void verifyWelcomeMessage(String message) {
        // Verify message
        Qase.attach("message.txt", "Expected: " + message, "text/plain");
    }
}
```

---

## Verification Checklist

- [ ] Maven/Gradle dependency updated from `qase-cucumber3-jvm` to `qase-cucumber-v3-reporter` v4.1.32
- [ ] Old `qase-cucumber3-jvm` dependency removed
- [ ] `qase.config.json` created in project root (or env vars updated)
- [ ] `Attachments.addAttachmentsToCurrentContext()` replaced with `Qase.attach()`
- [ ] Old `io.qase.api.*` imports replaced with `io.qase.commons.*` (if using @Step)
- [ ] `QaseEventListener` plugin registration verified
- [ ] Scenarios run and results appear in Qase
- [ ] BDD keywords appear in step titles (Given/When/Then)
- [ ] Data tables are captured correctly (if using data tables)

---

## Troubleshooting

### ClassNotFoundException after upgrade

**Symptom:** `java.lang.ClassNotFoundException: io.qase.api.services.Attachments`

**Solution:**
- Ensure old v3 dependency is completely removed from pom.xml/build.gradle
- Run `mvn dependency:tree` or `./gradlew dependencies` to verify no v3 artifacts remain
- Clear Maven local repository cache if needed: `rm -rf ~/.m2/repository/io/qase/`

### Tests not reporting to Qase after upgrade

**Symptom:** Scenarios run successfully but no results appear in Qase

**Solution:**
- Verify `QASE_MODE=testops` (not `QASE_ENABLE=true`)
- Check API token uses v2 API host (https://api.qase.io/v2)
- Enable debug: `"debug": true` in qase.config.json
- Verify `QaseEventListener` is registered as a Cucumber plugin

### Configuration not being read

**Symptom:** Reporter doesn't use settings from qase.config.json

**Solution:**
- `qase.config.json` must be in the project root directory (where pom.xml/build.gradle is)
- Priority order: system properties > env vars > qase.config.json
- Verify JSON syntax is valid (use a JSON validator)

### Attachment upload fails

**Symptom:** `NoSuchMethodError` or attachments don't appear in Qase

**Solution:**
- v4 uses `Qase.attach()` static methods instead of `Attachments` class
- Ensure `import io.qase.cucumber3.Qase;` is used
- Check file paths are correct and files exist

### Steps not appearing in test results

**Symptom:** Gherkin steps execute but don't appear in Qase

**Solution:**
- Gherkin steps are reported automatically - no AspectJ required
- Check that scenarios have `@QaseId` tags to link them to test cases
- Enable debug mode to see if steps are being captured
- Verify Cucumber version compatibility (Cucumber 3.x)

### Step titles missing BDD keywords

**Symptom:** Step titles don't include Given/When/Then keywords

**Solution:**
- BDD keywords were added in v4.1.5
- Ensure you're using qase-cucumber-v3-reporter version 4.1.5 or later
- This is expected behavior in v4.0.x - v4.1.4

---

## See Also

- [Usage Guide](usage.md) - Complete feature reference
- [Attachments Guide](ATTACHMENTS.md) - Detailed attachment documentation
- [Steps Guide](STEPS.md) - How Gherkin steps are reported
- [Changelog](../../changelog.md) - Full version history
