# Tags Support Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add `@QaseTags` annotation and Gherkin `@QaseTags=` tag to assign tags to test cases when reporting results to Qase API v2.

**Architecture:** New `@QaseTags` annotation -> extracted into `TestResult.tags` field -> mapped to `ResultCreateFields.tags` as comma-separated string. Three extraction paths: reflection (JUnit5/TestNG), AnnotationReader (JUnit4), Cucumber tag parsing.

**Tech Stack:** Java 8, JUnit 5 (for unit tests), Maven

---

### Task 1: Create `@QaseTags` annotation and add `tags` field to domain model

**Files:**
- Create: `qase-java-commons/src/main/java/io/qase/commons/annotation/QaseTags.java`
- Modify: `qase-java-commons/src/main/java/io/qase/commons/models/domain/TestResult.java`

- [ ] **Step 1: Create the `@QaseTags` annotation**

Create file `qase-java-commons/src/main/java/io/qase/commons/annotation/QaseTags.java`:

```java
package io.qase.commons.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QaseTags {
    String[] value();
}
```

- [ ] **Step 2: Add `tags` field to `TestResult`**

In `qase-java-commons/src/main/java/io/qase/commons/models/domain/TestResult.java`, add the field declaration after `public Map<String, String> fields;` (line 16):

```java
public List<String> tags;
```

And in the constructor, after `this.fields = new HashMap<>();` (line 34), add:

```java
this.tags = new ArrayList<>();
```

- [ ] **Step 3: Verify compilation**

Run: `cd /Users/gda/Documents/github/qase-tms/qase-java && mvn compile -pl qase-java-commons -q`
Expected: BUILD SUCCESS

- [ ] **Step 4: Commit**

```bash
git add qase-java-commons/src/main/java/io/qase/commons/annotation/QaseTags.java qase-java-commons/src/main/java/io/qase/commons/models/domain/TestResult.java
git commit -m "feat: add @QaseTags annotation and tags field to TestResult"
```

---

### Task 2: Add tag extraction for method-based tests (JUnit5/TestNG)

**Files:**
- Modify: `qase-java-commons/src/main/java/io/qase/commons/utils/IntegrationUtils.java`
- Modify: `qase-java-commons/src/main/java/io/qase/commons/utils/TestResultBuilder.java`
- Modify: `qase-java-commons/src/test/java/io/qase/commons/utils/TestResultBuilderTest.java`

- [ ] **Step 1: Write failing tests for `fromMethod()` tag extraction**

In `qase-java-commons/src/test/java/io/qase/commons/utils/TestResultBuilderTest.java`, add the test helper method (after `methodWithFields()` around line 48):

```java
@QaseTags({"smoke", "regression"})
void methodWithTags() {
}
```

Add the import at the top (it's already covered by the wildcard `import io.qase.commons.annotation.*;`).

Then add test methods (after the existing `fromMethod_withQaseFields_setsFieldsMap` test around line 139):

```java
@Test
void fromMethod_withQaseTags_setsTagsList() throws Exception {
    Method method = TestResultBuilderTest.class.getDeclaredMethod("methodWithTags");
    TestResult result = TestResultBuilder.fromMethod(method, Collections.<String, String>emptyMap(), 0L);

    assertNotNull(result.tags);
    assertEquals(2, result.tags.size());
    assertTrue(result.tags.contains("smoke"));
    assertTrue(result.tags.contains("regression"));
}

@Test
void fromMethod_withoutQaseTags_returnsEmptyTagsList() throws Exception {
    Method method = TestResultBuilderTest.class.getDeclaredMethod("methodWithQaseId");
    TestResult result = TestResultBuilder.fromMethod(method, Collections.<String, String>emptyMap(), 0L);

    assertNotNull(result.tags);
    assertTrue(result.tags.isEmpty());
}
```

- [ ] **Step 2: Run tests to verify they fail**

Run: `cd /Users/gda/Documents/github/qase-tms/qase-java && mvn test -pl qase-java-commons -Dtest="TestResultBuilderTest#fromMethod_withQaseTags_setsTagsList+fromMethod_withoutQaseTags_returnsEmptyTagsList" -q`
Expected: FAIL (tags not extracted yet)

- [ ] **Step 3: Implement `getQaseTags()` in IntegrationUtils**

In `qase-java-commons/src/main/java/io/qase/commons/utils/IntegrationUtils.java`, add after the `getQaseSuite()` method (after line 102):

```java
public static List<String> getQaseTags(Method method) {
    List<String> tags = new ArrayList<>();
    if (method.isAnnotationPresent(QaseTags.class)) {
        QaseTags annotation = method.getDeclaredAnnotation(QaseTags.class);
        if (annotation != null) {
            Collections.addAll(tags, annotation.value());
        }
    }
    return tags;
}
```

Note: `QaseTags` is already covered by the existing `import io.qase.commons.annotation.*;` on line 3.

- [ ] **Step 4: Wire tag extraction into `fromMethod()`**

In `qase-java-commons/src/main/java/io/qase/commons/utils/TestResultBuilder.java`, in the `fromMethod()` method, after line 62 (`String suite = IntegrationUtils.getQaseSuite(method);`), add:

```java
List<String> tags = IntegrationUtils.getQaseTags(method);
```

And after line 69 (`result.fields = fields;`), add:

```java
result.tags = tags;
```

- [ ] **Step 5: Run tests to verify they pass**

Run: `cd /Users/gda/Documents/github/qase-tms/qase-java && mvn test -pl qase-java-commons -Dtest="TestResultBuilderTest#fromMethod_withQaseTags_setsTagsList+fromMethod_withoutQaseTags_returnsEmptyTagsList" -q`
Expected: PASS

- [ ] **Step 6: Commit**

```bash
git add qase-java-commons/src/main/java/io/qase/commons/utils/IntegrationUtils.java qase-java-commons/src/main/java/io/qase/commons/utils/TestResultBuilder.java qase-java-commons/src/test/java/io/qase/commons/utils/TestResultBuilderTest.java
git commit -m "feat: extract @QaseTags in fromMethod() for JUnit5/TestNG"
```

---

### Task 3: Add tag extraction for JUnit4 (AnnotationReader path)

**Files:**
- Modify: `qase-java-commons/src/main/java/io/qase/commons/utils/TestResultBuilder.java`
- Modify: `qase-java-commons/src/test/java/io/qase/commons/utils/TestResultBuilderTest.java`

- [ ] **Step 1: Write failing test for `fromAnnotationReader()` tag extraction**

In `TestResultBuilderTest.java`, add a helper to create a `QaseTags` annotation instance (after the `newQaseTitle()` helper around line 226):

```java
private static QaseTags newQaseTags(final String... values) {
    return new QaseTags() {
        @Override
        public String[] value() {
            return values;
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            return QaseTags.class;
        }
    };
}
```

Then add the test (after the existing `fromAnnotationReader_withNullParameters_doesNotThrow` test around line 301):

```java
@Test
void fromAnnotationReader_withQaseTags_setsTagsList() {
    Map<Class<?>, Annotation> map = new HashMap<>();
    map.put(QaseId.class, newQaseId(1L));
    map.put(QaseTags.class, newQaseTags("smoke", "regression"));
    AnnotationReader reader = new MapAnnotationReader(map);

    TestResult result = TestResultBuilder.fromAnnotationReader(reader, "com.example.MyTest", "testFoo",
            null, 0L);

    assertNotNull(result.tags);
    assertEquals(2, result.tags.size());
    assertTrue(result.tags.contains("smoke"));
    assertTrue(result.tags.contains("regression"));
}

@Test
void fromAnnotationReader_withoutQaseTags_returnsEmptyTagsList() {
    Map<Class<?>, Annotation> map = new HashMap<>();
    map.put(QaseId.class, newQaseId(1L));
    AnnotationReader reader = new MapAnnotationReader(map);

    TestResult result = TestResultBuilder.fromAnnotationReader(reader, "com.example.MyTest", "testFoo",
            null, 0L);

    assertNotNull(result.tags);
    assertTrue(result.tags.isEmpty());
}
```

- [ ] **Step 2: Run tests to verify they fail**

Run: `cd /Users/gda/Documents/github/qase-tms/qase-java && mvn test -pl qase-java-commons -Dtest="TestResultBuilderTest#fromAnnotationReader_withQaseTags_setsTagsList+fromAnnotationReader_withoutQaseTags_returnsEmptyTagsList" -q`
Expected: FAIL

- [ ] **Step 3: Implement tag extraction in `fromAnnotationReader()`**

In `TestResultBuilder.java`, in the `fromAnnotationReader()` method, after the fields extraction block (after line 136, after the closing `}`), add:

```java
// Tags: @QaseTags
List<String> tags = new ArrayList<>();
QaseTags qaseTags = reader.getAnnotation(QaseTags.class);
if (qaseTags != null) {
    Collections.addAll(tags, qaseTags.value());
}
```

And after `result.fields = fields;` (line 161), add:

```java
result.tags = tags;
```

Also add `import java.util.Collections;` at the top if not already present (check existing imports — the file currently imports from `java.util.*` selectively on lines 11-15). If `Collections` is not imported, add it. Actually, checking line 13: `import java.util.Collections;` — it IS already imported.

- [ ] **Step 4: Run tests to verify they pass**

Run: `cd /Users/gda/Documents/github/qase-tms/qase-java && mvn test -pl qase-java-commons -Dtest="TestResultBuilderTest#fromAnnotationReader_withQaseTags_setsTagsList+fromAnnotationReader_withoutQaseTags_returnsEmptyTagsList" -q`
Expected: PASS

- [ ] **Step 5: Commit**

```bash
git add qase-java-commons/src/main/java/io/qase/commons/utils/TestResultBuilder.java qase-java-commons/src/test/java/io/qase/commons/utils/TestResultBuilderTest.java
git commit -m "feat: extract @QaseTags in fromAnnotationReader() for JUnit4"
```

---

### Task 4: Add Cucumber tag parsing and extraction

**Files:**
- Modify: `qase-java-commons/src/main/java/io/qase/commons/utils/CucumberUtils.java`
- Modify: `qase-java-commons/src/main/java/io/qase/commons/utils/TestResultBuilder.java`
- Modify: `qase-java-commons/src/test/java/io/qase/commons/utils/TestResultBuilderTest.java`

- [ ] **Step 1: Write failing tests for `CucumberUtils.getCaseTags()`**

Create file `qase-java-commons/src/test/java/io/qase/commons/utils/CucumberUtilsTest.java`:

```java
package io.qase.commons.utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CucumberUtilsTest {

    @Test
    void getCaseTags_basicParsing() {
        List<String> tags = Arrays.asList("@QaseTags=smoke,regression");
        List<String> result = CucumberUtils.getCaseTags(tags);

        assertEquals(2, result.size());
        assertEquals("smoke", result.get(0));
        assertEquals("regression", result.get(1));
    }

    @Test
    void getCaseTags_caseInsensitive() {
        List<String> tags = Arrays.asList("@qasetags=Smoke");
        List<String> result = CucumberUtils.getCaseTags(tags);

        assertEquals(1, result.size());
        assertEquals("Smoke", result.get(0));
    }

    @Test
    void getCaseTags_trimming() {
        List<String> tags = Arrays.asList("@QaseTags=smoke , regression");
        List<String> result = CucumberUtils.getCaseTags(tags);

        assertEquals(2, result.size());
        assertEquals("smoke", result.get(0));
        assertEquals("regression", result.get(1));
    }

    @Test
    void getCaseTags_multipleTagsAccumulate() {
        List<String> tags = Arrays.asList("@QaseTags=smoke", "@QaseTags=regression");
        List<String> result = CucumberUtils.getCaseTags(tags);

        assertEquals(2, result.size());
        assertTrue(result.contains("smoke"));
        assertTrue(result.contains("regression"));
    }

    @Test
    void getCaseTags_noTags_returnsEmptyList() {
        List<String> tags = Arrays.asList("@QaseId=42", "@QaseTitle=MyTest");
        List<String> result = CucumberUtils.getCaseTags(tags);

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void getCaseTags_emptyInput_returnsEmptyList() {
        List<String> result = CucumberUtils.getCaseTags(Collections.<String>emptyList());

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
```

- [ ] **Step 2: Run tests to verify they fail**

Run: `cd /Users/gda/Documents/github/qase-tms/qase-java && mvn test -pl qase-java-commons -Dtest="CucumberUtilsTest" -q`
Expected: FAIL (method `getCaseTags` does not exist)

- [ ] **Step 3: Implement `getCaseTags()` in CucumberUtils**

In `qase-java-commons/src/main/java/io/qase/commons/utils/CucumberUtils.java`, add the constant after line 18 (`private static final String QASE_SUITE = "@QaseSuite";`):

```java
private static final String QASE_TAGS = "@QaseTags";
```

Then add the method after `getCaseFields()` (after line 87):

```java
public static List<String> getCaseTags(List<String> tags) {
    return tags.stream()
            .map(tag -> tag.split(DELIMITER, 2))
            .filter(split -> QASE_TAGS.equalsIgnoreCase(split[0]) && split.length == 2)
            .flatMap(split -> Arrays.stream(split[1].split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty()))
            .collect(Collectors.toList());
}
```

- [ ] **Step 4: Run CucumberUtils tests to verify they pass**

Run: `cd /Users/gda/Documents/github/qase-tms/qase-java && mvn test -pl qase-java-commons -Dtest="CucumberUtilsTest" -q`
Expected: PASS

- [ ] **Step 5: Write failing test for `fromCucumber()` tag extraction**

In `TestResultBuilderTest.java`, add after the existing `fromCucumber_buildsRelationsFromSuiteTagOrFallsBackToUriPathParts` test (around line 425):

```java
@Test
void fromCucumber_withQaseTagsTag_setsTagsList() {
    StubAdapter adapter = new StubAdapter(
            Arrays.asList("@QaseId=42", "@QaseTags=smoke,regression"),
            "My Scenario",
            Arrays.asList("features", "login")
    );

    TestResult result = TestResultBuilder.fromCucumber(adapter, Collections.<String, String>emptyMap(), 0L);

    assertNotNull(result.tags);
    assertEquals(2, result.tags.size());
    assertTrue(result.tags.contains("smoke"));
    assertTrue(result.tags.contains("regression"));
}

@Test
void fromCucumber_multipleQaseTagsTags_accumulate() {
    StubAdapter adapter = new StubAdapter(
            Arrays.asList("@QaseId=42", "@QaseTags=smoke", "@QaseTags=regression"),
            "My Scenario",
            Arrays.asList("features", "login")
    );

    TestResult result = TestResultBuilder.fromCucumber(adapter, Collections.<String, String>emptyMap(), 0L);

    assertNotNull(result.tags);
    assertEquals(2, result.tags.size());
    assertTrue(result.tags.contains("smoke"));
    assertTrue(result.tags.contains("regression"));
}

@Test
void fromCucumber_withoutQaseTagsTag_returnsEmptyTagsList() {
    StubAdapter adapter = new StubAdapter(
            Arrays.asList("@QaseId=42"),
            "My Scenario",
            Arrays.asList("features", "login")
    );

    TestResult result = TestResultBuilder.fromCucumber(adapter, Collections.<String, String>emptyMap(), 0L);

    assertNotNull(result.tags);
    assertTrue(result.tags.isEmpty());
}
```

- [ ] **Step 6: Run Cucumber tests to verify they fail**

Run: `cd /Users/gda/Documents/github/qase-tms/qase-java && mvn test -pl qase-java-commons -Dtest="TestResultBuilderTest#fromCucumber_withQaseTagsTag_setsTagsList+fromCucumber_multipleQaseTagsTags_accumulate+fromCucumber_withoutQaseTagsTag_returnsEmptyTagsList" -q`
Expected: FAIL

- [ ] **Step 7: Wire tag extraction into `fromCucumber()`**

In `TestResultBuilder.java`, in the `fromCucumber()` method, after line 201 (`String suite = CucumberUtils.getCaseSuite(tags);`), add:

```java
List<String> caseTags = CucumberUtils.getCaseTags(tags);
```

And after `result.fields = fields;` (line 208), add:

```java
result.tags = caseTags;
```

- [ ] **Step 8: Run all tests to verify they pass**

Run: `cd /Users/gda/Documents/github/qase-tms/qase-java && mvn test -pl qase-java-commons -q`
Expected: PASS (all tests in qase-java-commons)

- [ ] **Step 9: Commit**

```bash
git add qase-java-commons/src/main/java/io/qase/commons/utils/CucumberUtils.java qase-java-commons/src/main/java/io/qase/commons/utils/TestResultBuilder.java qase-java-commons/src/test/java/io/qase/commons/utils/CucumberUtilsTest.java qase-java-commons/src/test/java/io/qase/commons/utils/TestResultBuilderTest.java
git commit -m "feat: add Cucumber @QaseTags= tag parsing and fromCucumber() extraction"
```

---

### Task 5: Map tags to API v2 client in `ApiClientV2.convertResult()`

**Files:**
- Modify: `qase-java-commons/src/main/java/io/qase/commons/client/ApiClientV2.java`

- [ ] **Step 1: Add `"tags"` case to the fields switch and post-loop tags mapping**

In `qase-java-commons/src/main/java/io/qase/commons/client/ApiClientV2.java`, in the `convertResult()` method:

First, before the fields loop (before line 200 `ResultCreateFields fields = new ResultCreateFields();`), add:

```java
List<String> allTags = new ArrayList<>(result.tags);
```

Then in the switch-case block, before the `default:` case (before line 235), add:

```java
case "tags":
    String fieldTags = result.fields.get(key);
    if (fieldTags != null && !fieldTags.isEmpty()) {
        for (String t : fieldTags.split(",")) {
            String trimmed = t.trim();
            if (!trimmed.isEmpty()) {
                allTags.add(trimmed);
            }
        }
    }
    break;
```

Then after the fields loop closing brace (after line 238), add:

```java
if (!allTags.isEmpty()) {
    fields.setTags(allTags.stream().distinct().collect(Collectors.joining(",")));
}
```

- [ ] **Step 2: Verify compilation**

Run: `cd /Users/gda/Documents/github/qase-tms/qase-java && mvn compile -pl qase-java-commons -q`
Expected: BUILD SUCCESS

- [ ] **Step 3: Commit**

```bash
git add qase-java-commons/src/main/java/io/qase/commons/client/ApiClientV2.java
git commit -m "feat: map TestResult.tags to ResultCreateFields.tags in API v2 client"
```

---

### Task 6: Update example tests with `@QaseTags`

**Files:**
- Modify: `examples/junit5/junit5-maven/src/test/java/example/SimpleTests.java`
- Modify: `examples/junit4/junit4-maven/src/test/java/example/SimpleTests.java`
- Modify: `examples/testng/testng-maven/src/test/java/org/example/SimpleTests.java`
- Modify: `examples/cucumber5/cucumber5-maven/src/test/resources/features/simple-tests.feature`
- Modify: `examples/cucumber7/cucumber7-maven-junit5/src/test/resources/features/simple-tests.feature`

- [ ] **Step 1: Add `@QaseTags` to JUnit5 example**

In `examples/junit5/junit5-maven/src/test/java/example/SimpleTests.java`:

Add `@QaseTags({"smoke"})` to `testWithFields` (before line 30, after the `@QaseId(6)` line):

```java
    @Test
    @QaseId(6)
    @QaseTags({"smoke"})
    @QaseFields(value = {
```

Add `@QaseTags({"regression"})` to `testWithCombinedAnnotations` (before line 64, after the `@QaseId(100)` line):

```java
    @Test
    @QaseId(100)
    @QaseTags({"regression"})
    @QaseTitle("Combined annotations example")
```

- [ ] **Step 2: Add `@QaseTags` to JUnit4 example**

In `examples/junit4/junit4-maven/src/test/java/example/SimpleTests.java`:

Add `@QaseTags({"smoke"})` to `testWithFields` (after `@QaseId(306)` line 29):

```java
    @Test
    @QaseId(306)
    @QaseTags({"smoke"})
    @QaseFields(value = {
```

Add `@QaseTags({"regression"})` to `testWithCombinedAnnotations` (after `@QaseId(310)` line 62):

```java
    @Test
    @QaseId(310)
    @QaseTags({"regression"})
    @QaseTitle("Combined annotations example")
```

- [ ] **Step 3: Add `@QaseTags` to TestNG example**

In `examples/testng/testng-maven/src/test/java/org/example/SimpleTests.java`:

Add `@QaseTags({"smoke"})` to `testWithFields` (after `@QaseId(206)` line 29):

```java
    @Test
    @QaseId(206)
    @QaseTags({"smoke"})
    @QaseFields(value = {
```

Add `@QaseTags({"regression"})` to `testWithCombinedAnnotations` (after `@QaseId(210)` line 62):

```java
    @Test
    @QaseId(210)
    @QaseTags({"regression"})
    @QaseTitle("Combined annotations example")
```

- [ ] **Step 4: Add `@QaseTags=` to Cucumber 5 feature file**

In `examples/cucumber5/cucumber5-maven/src/test/resources/features/simple-tests.feature`:

Add `@QaseTags=smoke` to "Test with QaseFields" scenario (line 16):

```gherkin
  @QaseId=505 @QaseFields={"description":"Verifies_login","severity":"critical","priority":"high","layer":"e2e"} @QaseTags=smoke
  Scenario: Test with QaseFields
```

Add `@QaseTags=regression` to "Test with combined annotations" scenario (line 32):

```gherkin
  @QaseId=509 @QaseTitle=Combined_annotations @QaseTags=regression
  Scenario: Test with combined annotations
```

- [ ] **Step 5: Add `@QaseTags=` to Cucumber 7 feature file**

In `examples/cucumber7/cucumber7-maven-junit5/src/test/resources/features/simple-tests.feature`:

Add `@QaseTags=smoke` to "Test with QaseFields" scenario (line 16):

```gherkin
  @QaseId=405 @QaseFields={"description":"Verifies_login","severity":"critical","priority":"high","layer":"e2e"} @QaseTags=smoke
  Scenario: Test with QaseFields
```

Add `@QaseTags=regression` to "Test with combined annotations" scenario (line 32):

```gherkin
  @QaseId=409 @QaseTitle=Combined_annotations @QaseTags=regression
  Scenario: Test with combined annotations
```

- [ ] **Step 6: Commit**

```bash
git add examples/junit5/junit5-maven/src/test/java/example/SimpleTests.java examples/junit4/junit4-maven/src/test/java/example/SimpleTests.java examples/testng/testng-maven/src/test/java/org/example/SimpleTests.java examples/cucumber5/cucumber5-maven/src/test/resources/features/simple-tests.feature examples/cucumber7/cucumber7-maven-junit5/src/test/resources/features/simple-tests.feature
git commit -m "feat: add @QaseTags examples to all reporter example tests"
```

---

### Task 7: Update expected YAML files for integration tests

**Files:**
- Modify: `expected/junit5-examples.yaml`
- Modify: `expected/junit4-examples.yaml`
- Modify: `expected/testng-examples.yaml`
- Modify: `expected/cucumber5-examples.yaml`
- Modify: `expected/cucumber7-examples.yaml`

- [ ] **Step 1: Update JUnit5 expected YAML**

In `expected/junit5-examples.yaml`, find the `testWithFields` result (title: `testWithFields`, signature containing `simpletests::testwithfields`) and add `tags:` after the `fields:` block:

```yaml
- title: testWithFields
  signature: 6::example::simpletests::testwithfields
  testops_ids:
  - 6
  execution:
    status: passed
  fields:
    severity: critical
    description: Verifies login with valid credentials
    priority: high
    layer: unit
  tags:
  - smoke
  relations:
```

Find the `Combined annotations example` result and add `tags:` after the `fields:` block:

```yaml
- title: Combined annotations example
  signature: 100::example::simpletests::testwithcombinedannotations
  testops_ids:
  - 100
  execution:
    status: passed
  fields:
    severity: normal
    description: Demonstrates using multiple annotations together
  tags:
  - regression
  relations:
```

- [ ] **Step 2: Update JUnit4 expected YAML**

In `expected/junit4-examples.yaml`, find `testWithFields` (signature `306::example:simpletests::testwithfields`) and add after the `fields:` block:

```yaml
  tags:
  - smoke
```

Find `Combined annotations example` (signature `310::example:simpletests::testwithcombinedannotations`) and add after the `fields:` block:

```yaml
  tags:
  - regression
```

- [ ] **Step 3: Update TestNG expected YAML**

In `expected/testng-examples.yaml`, find `testWithFields` (signature `206::org:example::simpletests::testwithfields`) and add after the `fields:` block:

```yaml
  tags:
  - smoke
```

Find `Combined annotations example` (signature `210::org:example::simpletests::testwithcombinedannotations`) and add after the `fields:` block:

```yaml
  tags:
  - regression
```

- [ ] **Step 4: Update Cucumber 5 expected YAML**

In `expected/cucumber5-examples.yaml`, find `Test with QaseFields` (testops_ids 505) and add after the `fields:` block:

```yaml
  tags:
  - smoke
```

Find `Combined_annotations` (testops_ids 509) and add after the `execution:` block:

```yaml
  tags:
  - regression
```

- [ ] **Step 5: Update Cucumber 7 expected YAML**

In `expected/cucumber7-examples.yaml`, find `Test with QaseFields` (testops_ids 405) and add after the `fields:` block:

```yaml
  tags:
  - smoke
```

Find `Combined_annotations` (testops_ids 409) and add after the `execution:` block:

```yaml
  tags:
  - regression
```

- [ ] **Step 6: Commit**

```bash
git add expected/junit5-examples.yaml expected/junit4-examples.yaml expected/testng-examples.yaml expected/cucumber5-examples.yaml expected/cucumber7-examples.yaml
git commit -m "test: add tags to expected YAML files for integration tests"
```

---

### Task 8: Update documentation

**Files:**
- Modify: `changelog.md`
- Modify: `qase-junit5-reporter/docs/usage.md`
- Modify: `qase-junit5-reporter/README.md`
- Modify: `qase-junit4-reporter/README.md`
- Modify: `qase-testng-reporter/README.md`
- Modify: `qase-cucumber-v7-reporter/README.md`

- [ ] **Step 1: Add changelog entry**

In `changelog.md`, add at the very top (before line 1):

```markdown
# qase-java 4.1.50

## What's new

- Added `@QaseTags` annotation for assigning tags to test cases when reporting results
  - Java annotation: `@QaseTags({"smoke", "regression"})` on test methods
  - Cucumber Gherkin tag: `@QaseTags=smoke,regression` on scenarios
  - Tags can also be set via `@QaseFields(@Field(name = "tags", value = "smoke,regression"))`
  - Tags are sent as a comma-separated string in the `tags` field of the API result

```

- [ ] **Step 2: Add Tags section to JUnit5 usage guide**

In `qase-junit5-reporter/docs/usage.md`, after the "Adding Fields" section (after line 161, after the closing `---`), add:

```markdown

## Adding Tags

Assign tags to test cases using the `@QaseTags` annotation. Tags are sent to Qase as metadata on the test result.

```java
package com.example.tests;

import io.qase.commons.annotation.QaseTags;
import org.junit.jupiter.api.Test;

public class SimpleTests {

    @Test
    @QaseTags({"smoke", "regression"})
    public void testWithTags() {
        System.out.println("Test with tags");
    }
}
```

You can also set tags using `@QaseFields`:

```java
@QaseFields({
    @Field(name = "tags", value = "smoke,regression")
})
```

---
```

Also update the Table of Contents (after line 18, after the `- [Adding Suite]` section) to add:

```markdown
- [Adding Tags](#adding-tags)
```

- [ ] **Step 3: Add Tags to JUnit5 README features list**

In `qase-junit5-reporter/README.md`, after line 13 (`- Report test results with rich metadata (fields, attachments, steps)`), add:

```markdown
- Assign tags to test cases via `@QaseTags` annotation
```

- [ ] **Step 4: Add Tags to JUnit4 README features list**

In `qase-junit4-reporter/README.md`, after line 13 (same position), add:

```markdown
- Assign tags to test cases via `@QaseTags` annotation
```

- [ ] **Step 5: Add Tags to TestNG README features list**

In `qase-testng-reporter/README.md`, after line 13, add:

```markdown
- Assign tags to test cases via `@QaseTags` annotation
```

- [ ] **Step 6: Add Tags to Cucumber 7 README features list**

In `qase-cucumber-v7-reporter/README.md`, after line 13, add:

```markdown
- Assign tags to test cases via `@QaseTags=tag1,tag2` Gherkin tag
```

- [ ] **Step 7: Commit**

```bash
git add changelog.md qase-junit5-reporter/docs/usage.md qase-junit5-reporter/README.md qase-junit4-reporter/README.md qase-testng-reporter/README.md qase-cucumber-v7-reporter/README.md
git commit -m "docs: add @QaseTags documentation to changelog and reporter READMEs"
```

---

### Task 9: Bump version to 4.1.50

**Files:**
- Modify: all 12 `pom.xml` files

- [ ] **Step 1: Bump version in all pom.xml files**

Run this command to replace the version in all pom.xml files:

```bash
cd /Users/gda/Documents/github/qase-tms/qase-java && find . -name 'pom.xml' -exec sed -i '' 's/<version>4.1.49<\/version>/<version>4.1.50<\/version>/g' {} +
```

- [ ] **Step 2: Verify the version bump**

Run: `grep -r '<version>4.1.50</version>' --include='pom.xml' . | wc -l`
Expected: `12` (parent + 11 modules)

Run: `grep -r '<version>4.1.49</version>' --include='pom.xml' . | wc -l`
Expected: `0`

- [ ] **Step 3: Verify full build compiles**

Run: `cd /Users/gda/Documents/github/qase-tms/qase-java && mvn compile -q`
Expected: BUILD SUCCESS

- [ ] **Step 4: Run all unit tests**

Run: `cd /Users/gda/Documents/github/qase-tms/qase-java && mvn test -pl qase-java-commons -q`
Expected: BUILD SUCCESS, all tests pass

- [ ] **Step 5: Commit**

```bash
git add -A '*.pom.xml' || git add $(find . -name 'pom.xml')
git commit -m "chore: bump version to 4.1.50"
```
