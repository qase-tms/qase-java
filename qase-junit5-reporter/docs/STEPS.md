# Test Steps in JUnit 5

This guide covers how to define and report test steps for detailed execution tracking in Qase using the JUnit 5 reporter.

---

## Overview

Test steps provide granular visibility into test execution. Each step is reported separately in Qase, showing:

- **Step name** (from @Step annotation value)
- **Step status** (passed/failed — automatic)
- **Step duration** (automatic timing)
- **Attachments** within the step
- **Error details** on failure
- **Nested sub-steps** (child steps within parent steps)

Steps are defined by annotating methods with `@Step`. The mechanism works through AspectJ runtime interception — the `io.qase.commons.aspects.StepsAspects` class handles this via `@Before` and `@AfterReturning`/`@AfterThrowing` advice, recording when each step starts, ends, and whether it threw an exception.

**Import:**
```java
import io.qase.commons.annotation.Step;
```

---

## Defining Steps

### Using @Step Annotation

Annotate methods with `@Step` to report them as test steps. The annotation value becomes the step name in Qase.

```java
package com.example.tests;

import io.qase.commons.annotation.Step;
import org.junit.jupiter.api.Test;

public class StepTests {
    @Test
    public void testLoginFlow() {
        openLoginPage();
        enterCredentials("user@example.com", "password123");
        clickLoginButton();
        verifyDashboard();
    }

    @Step("Open login page")
    private void openLoginPage() {
        // Navigate to login page
    }

    @Step("Enter credentials for {email}")
    private void enterCredentials(String email, String password) {
        // Fill in email and password fields
    }

    @Step("Click login button")
    private void clickLoginButton() {
        // Click the submit button
    }

    @Step("Verify dashboard is displayed")
    private void verifyDashboard() {
        // Assert dashboard elements are visible
    }
}
```

### Parameter Interpolation

The `@Step` annotation supports parameter interpolation using `{paramName}` syntax. The `StepsAspects` class replaces `{parameterName}` placeholders with actual argument values at runtime using AspectJ's `MethodSignature.getParameterNames()`.

```java
@Step("Add item '{itemName}' with quantity {quantity}")
private void addItem(String itemName, int quantity) {
    // Step title in Qase will show: "Add item 'Widget' with quantity 5"
}

@Step("Verify total is {expectedTotal}")
private void verifyTotal(double expectedTotal) {
    // Works with all primitive types, arrays, and objects (via String.valueOf())
}
```

**Supported types:**
- All primitive types (`int`, `double`, `boolean`, etc.)
- Wrapper types (`Integer`, `Double`, `Boolean`, etc.)
- `String`
- Arrays (via `Arrays.toString()`)
- Objects (via `String.valueOf()`)

Parameter names must match exactly: `@Step("Login as {username}")` requires a method parameter named `username`.

---

## Nested Steps

Steps can call other steps, creating a hierarchy of nested steps. Nesting happens automatically — any `@Step` method called from within another `@Step` method creates a child step.

```java
package com.example.tests;

import io.qase.commons.annotation.Step;
import org.junit.jupiter.api.Test;

public class NestedStepTests {
    @Test
    public void testCompleteCheckout() {
        authenticateUser();
        completeCheckout();
        verifyOrderConfirmation();
    }

    // Level 1: Top-level step
    @Step("Authenticate user")
    private void authenticateUser() {
        navigateToLogin();     // Level 2
        submitCredentials();   // Level 2
    }

    // Level 2: Child of "Authenticate user"
    @Step("Navigate to login page")
    private void navigateToLogin() {
        // implementation
    }

    // Level 2: Child of "Authenticate user"
    @Step("Submit login credentials")
    private void submitCredentials() {
        enterEmail();          // Level 3
        enterPassword();       // Level 3
        clickSubmit();         // Level 3
    }

    // Level 3: Child of "Submit login credentials"
    @Step("Enter email address")
    private void enterEmail() {
        // implementation
    }

    // Level 3: Child of "Submit login credentials"
    @Step("Enter password")
    private void enterPassword() {
        // implementation
    }

    // Level 3: Child of "Submit login credentials"
    @Step("Click submit button")
    private void clickSubmit() {
        // implementation
    }

    @Step("Complete checkout process")
    private void completeCheckout() {
        addItemToCart();
        proceedToPayment();
    }

    @Step("Add item to cart")
    private void addItemToCart() { }

    @Step("Proceed to payment")
    private void proceedToPayment() { }

    @Step("Verify order confirmation")
    private void verifyOrderConfirmation() { }
}
```

**How it appears in Qase:**

```text
- Authenticate user                    [PASSED]
  - Navigate to login page             [PASSED]
  - Submit login credentials           [PASSED]
    - Enter email address              [PASSED]
    - Enter password                   [PASSED]
    - Click submit button              [PASSED]
- Complete checkout process            [PASSED]
  - Add item to cart                   [PASSED]
  - Proceed to payment                 [PASSED]
- Verify order confirmation            [PASSED]
```

**Mechanism:** The `StepStorage` class uses `ThreadLocal` to track the current step. When `startStep()` is called while a step is already in progress, the new step's `parentId` is set to the current step's ID, and the new step is added to the current step's `steps` list, creating the hierarchy.

---

## Steps with Attachments

Calling `Qase.attach()` inside a `@Step` method automatically routes the attachment to that step.

```java
package com.example.tests;

import io.qase.junit5.Qase;
import io.qase.commons.annotation.Step;
import org.junit.jupiter.api.Test;

public class StepAttachmentTests {
    @Test
    public void testWithStepAttachments() {
        performAction();
        verifyResult();
    }

    @Step("Perform action on page")
    private void performAction() {
        // Do something...
        // Attachment goes to THIS step
        byte[] screenshot = captureScreenshot();
        Qase.attach("after-action.png", screenshot, "image/png");
    }

    @Step("Verify expected result")
    private void verifyResult() {
        String actualHtml = getPageSource();
        // Attachment goes to THIS step
        Qase.attach("page-source.html", actualHtml, "text/html");
    }

    private byte[] captureScreenshot() {
        return new byte[0]; // Placeholder
    }

    private String getPageSource() {
        return "<html>...</html>";
    }
}
```

**Note:** The same `Qase.attach()` method is used everywhere. It automatically detects whether a step is in progress via `StepStorage.isStepInProgress()` and routes the attachment accordingly. If called outside a step, the attachment goes to the overall test result.

---

## Step Status

Step status is determined automatically based on method execution:

| Method Execution | Step Status |
|---|---|
| Returns normally | `PASSED` |
| Throws any exception | `FAILED` |

There is no manual status control via annotation. Status is determined by whether the method throws an exception. If an assertion fails (throws `AssertionError`), the step is marked `FAILED`. If the method completes normally, it's `PASSED`.

```java
@Step("Validate response code")
private void validateResponseCode(int actual, int expected) {
    // If this assertion passes -> step status: PASSED
    // If this assertion fails -> step status: FAILED
    assertEquals(expected, actual);
}
```

**Note about additional statuses:** The `StepResultStatus` enum includes `PASSED`, `FAILED`, `SKIPPED`, `BLOCKED`, and `UNTESTED`, but for `@Step` annotation only `PASSED` and `FAILED` are used. The other statuses apply to Cucumber step mapping.

---

## AspectJ Setup

**CRITICAL:** AspectJ weaver must be configured as a javaagent for `@Step` annotations to work. Without it, `@Step` annotations are silently ignored — methods execute normally but no steps are recorded.

### Maven Configuration

Add AspectJ dependencies and configure the Surefire plugin:

```xml
<properties>
    <aspectj.version>1.9.22</aspectj.version>
</properties>

<dependencies>
    <!-- AspectJ Weaver (required for @Step annotation) -->
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjweaver</artifactId>
        <version>${aspectj.version}</version>
        <scope>test</scope>
    </dependency>
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjrt</artifactId>
        <version>${aspectj.version}</version>
        <scope>provided</scope>
    </dependency>
</dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-surefire-plugin</artifactId>
            <configuration>
                <argLine>
                    -javaagent:"${user.home}/.m2/repository/org/aspectj/aspectjweaver/${aspectj.version}/aspectjweaver-${aspectj.version}.jar"
                </argLine>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### Gradle Configuration

Add AspectJ dependencies and configure the test task:

```groovy
dependencies {
    testImplementation 'org.aspectj:aspectjweaver:1.9.22'
    testImplementation 'org.aspectj:aspectjrt:1.9.22'
}

test {
    jvmArgs "-javaagent:${configurations.testRuntimeClasspath.find { it.name.contains('aspectjweaver') }}"
}
```

---

## Best Practices

1. **Keep steps atomic** — each step should represent a single logical action. Avoid combining multiple distinct actions in one `@Step` method.

2. **Use descriptive step names** — step titles should read like actions: "Enter username", "Click submit button", "Verify order total is {expectedTotal}". Avoid vague names like "Do step 1" or "Check result".

3. **Use parameter interpolation** — include dynamic values in step titles via `{paramName}` for better readability in Qase reports.

4. **Limit nesting to 2-3 levels** — deeper nesting makes reports harder to read. If you need more than 3 levels, consider flattening or restructuring your test flow.

5. **Attach evidence in steps** — call `Qase.attach()` inside `@Step` methods to associate screenshots, logs, or data with specific steps rather than the overall test case.

6. **Handle shared steps carefully** — if the same `@Step` method is used across multiple test classes, ensure it remains stateless. `StepStorage` is thread-local, so parallel execution is safe.

7. **Avoid calling @Step methods from non-test contexts** — `@Step` methods should only be called during test execution (from `@Test` methods or other `@Step` methods). Calling them from setup/teardown or utility methods outside test context may produce unexpected results.

---

## Troubleshooting

### Steps Not Appearing in Qase

**Possible causes:**

1. **AspectJ weaver is NOT configured** — this is the most common cause. Verify `aspectjweaver` JAR is on the javaagent path.
2. Check Maven/Gradle configuration matches the setup section above.
3. Verify `@Step` annotation is imported from `io.qase.commons.annotation.Step` (not a custom annotation).
4. Enable debug logging: `"debug": true` in `qase.config.json`.

**Solutions:**

- Verify the AspectJ javaagent argument in your build configuration
- Check test execution logs for AspectJ weaver initialization messages
- Enable debug mode to see step recording details

### Nested Steps Appear Flat

**Possible causes:**

1. Ensure inner `@Step` methods are called from within outer `@Step` methods (not from the `@Test` method directly).
2. Verify AspectJ is intercepting all `@Step` methods (private methods need aspectjweaver, not compile-time weaving).

**Solutions:**

- Review your test structure to ensure proper nesting
- Verify all steps are executed within the expected hierarchy

### Step Parameters Not Interpolated

**Possible causes:**

1. Parameter names must match exactly: `@Step("Login as {username}")` requires method parameter named `username`.
2. AspectJ must have access to parameter name metadata — ensure compilation preserves parameter names (`-parameters` flag) or use matching by position.

**Solutions:**

- Check parameter names in method signatures match placeholders in `@Step` annotation
- Add `-parameters` compiler flag if using Java 8+
- Use explicit parameter naming in your build configuration

### JUnit 5 Extension Not Detected

**Possible causes:**

- Auto-detection of extensions is not enabled

**Solutions:**

1. Ensure `junit-platform.properties` exists in `src/test/resources/` with:
   ```properties
   junit.jupiter.extensions.autodetection.enabled=true
```text
2. Verify both SPI files exist in the reporter JAR (automatic with dependency).
3. Restart your IDE or clean rebuild the project.

### Step Duration Shows 0ms

Very fast step execution may show 0ms. This is expected for trivial operations.

---

## See Also

- [Usage Guide](usage.md) — Overview of all reporter features
- [Attachments Guide](ATTACHMENTS.md) — Attaching files and content to test results
- [Upgrade Guide](UPGRADE.md) — Migration from v3 to v4
