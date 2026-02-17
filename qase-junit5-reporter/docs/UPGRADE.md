# Upgrade Guide: JUnit 5 Reporter

This guide covers migration from Qase Java Reporter v3 to v4 for the JUnit 5 reporter.

---

## Version History

| Version Range | Key Changes |
|---|---|
| v4.0.0 - v4.1.32 (current) | New architecture, v2 API, qase.config.json, new annotations |
| v3.x (v3.2.1 last) | Legacy architecture, v1 API, env-only configuration |

---

## Breaking Changes

1. **Maven artifact ID renamed**: `qase-junit5` → `qase-junit5-reporter`
2. **Configuration completely changed**: Environment-variable-only → `qase.config.json` + env vars + system properties (priority order)
3. **Annotation package renamed**: `io.qase.api.annotation.*` → `io.qase.commons.annotation.*`
4. **Attachment API replaced**: `Attachments.addAttachmentsToCurrentContext(File...)` → `Qase.attach(...)` static methods
5. **New `Qase` utility class**: `import io.qase.junit5.Qase` for attachments and comments
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
    <artifactId>qase-junit5</artifactId>
    <version>3.2.1</version>
    <scope>test</scope>
</dependency>
```

**Maven After (v4):**
```xml
<dependency>
    <groupId>io.qase</groupId>
    <artifactId>qase-junit5-reporter</artifactId>
    <version>4.1.32</version>
    <scope>test</scope>
</dependency>
```

**Gradle Before (v3):**
```groovy
testImplementation 'io.qase:qase-junit5:3.2.1'
```

**Gradle After (v4):**
```groovy
testImplementation 'io.qase:qase-junit5-reporter:4.1.32'
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
| `import io.qase.api.annotation.QaseId;` | `import io.qase.commons.annotation.QaseId;` |
| `import io.qase.api.annotation.QaseTitle;` | `import io.qase.commons.annotation.QaseTitle;` |
| `import io.qase.api.annotation.Step;` | `import io.qase.commons.annotation.Step;` |
| `import io.qase.api.annotation.CaseId;` | `import io.qase.commons.annotation.CaseId;` *(deprecated, use @QaseId)* |
| `import io.qase.api.annotation.CaseTitle;` | `import io.qase.commons.annotation.CaseTitle;` *(deprecated, use @QaseTitle)* |
| `import io.qase.api.StepStorage;` | `import io.qase.commons.StepStorage;` |
| `import io.qase.api.services.Attachments;` | Use `io.qase.junit5.Qase` instead |

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
import io.qase.junit5.Qase;

// v4: Attach files
Qase.attach("screenshot.png");

// v4: Attach content from memory
Qase.attach("report.txt", "Content here", "text/plain");

// v4: Attach binary content
Qase.attach("image.png", imageBytes, "image/png");
```

---

## Framework-Specific Migration Notes

### JUnit 5 Extension Mechanism Changes

- **v3:** Class was `QaseExtension implements TestExecutionListener`
- **v4:** Class is `QaseListener implements TestExecutionListener, Extension, BeforeAllCallback, AfterAllCallback, InvocationInterceptor, TestWatcher`

### JUnit 5 Configuration Requirements

**junit-platform.properties (v4 requirement):**

Create `src/test/resources/junit-platform.properties` with:
```properties
junit.jupiter.extensions.autodetection.enabled=true
```

### Service Provider Interface (SPI) Files

v4 registers both:
- `org.junit.jupiter.api.extension.Extension`
- `org.junit.platform.launcher.TestExecutionListener`

v3 only registered `TestExecutionListener`. This is handled automatically by the reporter JAR.

---

## Before/After Complete Example

**Before (v3):**
```java
package com.example.tests;

import io.qase.api.annotation.QaseId;
import io.qase.api.annotation.Step;
import io.qase.api.services.Attachments;
import org.junit.jupiter.api.Test;
import java.io.File;

public class LoginTests {

    @Test
    @QaseId(42)
    public void testSuccessfulLogin() {
        openLoginPage();
        enterCredentials();
        clickLoginButton();
        verifyDashboard();
    }

    @Step("Open login page")
    private void openLoginPage() {
        // Open page
    }

    @Step("Enter credentials")
    private void enterCredentials() {
        // Enter username and password
    }

    @Step("Click login button")
    private void clickLoginButton() {
        // Click button
        Attachments.addAttachmentsToCurrentContext(new File("screenshot.png"));
    }

    @Step("Verify dashboard")
    private void verifyDashboard() {
        // Verify
    }
}
```

**After (v4):**
```java
package com.example.tests;

import io.qase.commons.annotation.QaseId;
import io.qase.commons.annotation.Step;
import io.qase.junit5.Qase;
import org.junit.jupiter.api.Test;

public class LoginTests {

    @Test
    @QaseId(42)
    public void testSuccessfulLogin() {
        openLoginPage();
        enterCredentials();
        clickLoginButton();
        verifyDashboard();
    }

    @Step("Open login page")
    private void openLoginPage() {
        // Open page
    }

    @Step("Enter credentials")
    private void enterCredentials() {
        // Enter username and password
    }

    @Step("Click login button")
    private void clickLoginButton() {
        // Click button
        Qase.attach("screenshot.png");
    }

    @Step("Verify dashboard")
    private void verifyDashboard() {
        // Verify
        Qase.comment("Dashboard loaded successfully");
    }
}
```

---

## Verification Checklist

- [ ] Maven/Gradle dependency updated to `qase-junit5-reporter` v4.1.32
- [ ] Old `qase-junit5` dependency removed
- [ ] `qase.config.json` created in project root (or env vars updated)
- [ ] All imports updated from `io.qase.api.*` to `io.qase.commons.*`
- [ ] Attachment code updated from `Attachments.addAttachmentsToCurrentContext()` to `Qase.attach()`
- [ ] Old Guice module configurations removed (if any)
- [ ] `@CaseId` replaced with `@QaseId` (recommended, not required)
- [ ] `@CaseTitle` replaced with `@QaseTitle` (recommended, not required)
- [ ] `junit-platform.properties` created with autodetection enabled
- [ ] Tests run successfully with v4 reporter
- [ ] Test results appear correctly in Qase TestOps

---

## Troubleshooting

### ClassNotFoundException after upgrade

**Symptom:** `java.lang.ClassNotFoundException: io.qase.api.annotation.QaseId`

**Solution:**
- Ensure old v3 dependency is completely removed from pom.xml/build.gradle
- Run `mvn dependency:tree` or `./gradlew dependencies` to verify no v3 artifacts remain
- Clear Maven local repository cache if needed: `rm -rf ~/.m2/repository/io/qase/`

### Tests not reporting to Qase after upgrade

**Symptom:** Tests run successfully but no results appear in Qase

**Solution:**
- Verify `QASE_MODE=testops` (not `QASE_ENABLE=true`)
- Check API token uses v2 API host (https://api.qase.io/v2)
- Enable debug: `"debug": true` in qase.config.json
- Check JUnit 5 platform properties: ensure `junit.jupiter.extensions.autodetection.enabled=true`

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
- Ensure `import io.qase.junit5.Qase;` is used
- Check file paths are correct and files exist

### Steps not appearing in test results

**Symptom:** @Step methods execute but don't show as steps in Qase

**Solution:**
- Verify AspectJ weaver is configured (see [Steps Guide](STEPS.md))
- Ensure `-javaagent` argument is in surefire plugin configuration
- Check that aspectjweaver JAR version matches aspectjrt dependency

---

## See Also

- [Usage Guide](usage.md) - Complete feature reference
- [Attachments Guide](ATTACHMENTS.md) - Detailed attachment documentation
- [Steps Guide](STEPS.md) - Step reporting guide
- [Changelog](../../changelog.md) - Full version history
