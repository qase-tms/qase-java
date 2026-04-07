# Tags Support for Qase Java Reporters

## Overview

Add support for assigning tags to test cases via annotations (`@QaseTags`) and Gherkin tags (`@QaseTags:tag1,tag2`). Tags are sent as a comma-separated string in the `tags` field of `ResultCreateFields` in the Qase API v2.

Reference implementation: [qase-csharp PR #53](https://github.com/qase-tms/qase-csharp/pull/53)

## API Format

- Field: `ResultCreateFields.tags`
- Type: `String` (nullable)
- JSON key: `"tags"`
- Value format: comma-separated tag titles, e.g. `"smoke,regression"`

## Design

### 1. Annotation: `@QaseTags`

**File:** `qase-java-commons/src/main/java/io/qase/commons/annotation/QaseTags.java`

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface QaseTags {
    String[] value();
}
```

- Method-level only (consistent with all other Qase annotations in this project)
- Accepts one or more string tags: `@QaseTags({"smoke", "regression"})` or `@QaseTags("smoke")`
- Not `@Repeatable` (no existing annotation uses this pattern)

### 2. Domain Model: `TestResult.tags`

**File:** `qase-java-commons/src/main/java/io/qase/commons/models/domain/TestResult.java`

Add field:
```java
public List<String> tags;
```

Initialize in constructor:
```java
this.tags = new ArrayList<>();
```

### 3. Tag Extraction: `IntegrationUtils.getQaseTags()`

**File:** `qase-java-commons/src/main/java/io/qase/commons/utils/IntegrationUtils.java`

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

### 4. TestResultBuilder — All Three Entry Points

**File:** `qase-java-commons/src/main/java/io/qase/commons/utils/TestResultBuilder.java`

#### 4a. `fromMethod()` (JUnit5, TestNG)

After extracting fields, add:
```java
List<String> tags = IntegrationUtils.getQaseTags(method);
// ...
result.tags = tags;
```

#### 4b. `fromAnnotationReader()` (JUnit4)

After fields extraction block, add:
```java
List<String> tags = new ArrayList<>();
QaseTags qaseTags = reader.getAnnotation(QaseTags.class);
if (qaseTags != null) {
    Collections.addAll(tags, qaseTags.value());
}
// ...
result.tags = tags;
```

#### 4c. `fromCucumber()` (Cucumber v3-v7)

After fields extraction, add:
```java
List<String> cucumberTags = CucumberUtils.getCaseTags(tags);
// ...
result.tags = cucumberTags;
```

### 5. Cucumber Tag Parsing: `CucumberUtils.getCaseTags()`

**File:** `qase-java-commons/src/main/java/io/qase/commons/utils/CucumberUtils.java`

```java
private static final String QASE_TAGS_PREFIX = "@QaseTags";

public static List<String> getCaseTags(List<String> tags) {
    return tags.stream()
            .filter(tag -> {
                String prefix = tag.split("[:=]", 2)[0];
                return prefix.equalsIgnoreCase(QASE_TAGS_PREFIX);
            })
            .flatMap(tag -> {
                String[] parts = tag.split("[:=]", 2);
                if (parts.length == 2 && !parts[1].isEmpty()) {
                    return Arrays.stream(parts[1].split(","))
                            .map(String::trim)
                            .filter(s -> !s.isEmpty());
                }
                return Stream.empty();
            })
            .collect(Collectors.toList());
}
```

- Format: `@QaseTags:smoke,regression` (colon delimiter) or `@QaseTags=smoke,regression` (equals delimiter)
- Case-insensitive prefix matching
- Each tag is trimmed
- Multiple `@QaseTags` tags accumulate (flatMap collects from all matching tags)

### 6. API Mapping: `ApiClientV2.convertResult()`

**File:** `qase-java-commons/src/main/java/io/qase/commons/client/ApiClientV2.java`

In the `convertResult()` method, after the fields loop:

```java
// Collect tags from result.tags
List<String> allTags = new ArrayList<>(result.tags);

// Also collect tags from fields map (if user used @QaseFields(@Field(name="tags", value="smoke,regression")))
// This is handled by intercepting "tags" key in the switch-case below

// In the switch-case, add:
case "tags":
    // Collect comma-separated tags from fields map into allTags
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

// After the fields loop:
if (!allTags.isEmpty()) {
    String tagsString = allTags.stream()
            .distinct()
            .collect(Collectors.joining(","));
    fields.setTags(tagsString);
}
```

### 7. Examples

#### 7a. JUnit5 Example (`examples/junit5/junit5-maven/src/test/java/example/SimpleTests.java`)

Add `@QaseTags` to existing tests:
- `testWithFields` (ID=6): add `@QaseTags({"smoke"})`
- `testWithCombinedAnnotations` (ID=100): add `@QaseTags({"regression"})`

#### 7b. JUnit4 Example

Add `@QaseTags` to corresponding tests following same pattern.

#### 7c. TestNG Example

Add `@QaseTags` to corresponding tests following same pattern.

#### 7d. Cucumber Examples

Add Gherkin tags to feature files:
- `@QaseTags:smoke` on relevant scenarios
- `@QaseTags:regression` on relevant scenarios

### 8. Expected YAML Files

Update all expected YAML files to include `tags:` field:

- Tests with `@QaseTags({"smoke"})`: add `tags: [smoke]`
- Tests with `@QaseTags({"regression"})`: add `tags: [regression]`
- Cucumber scenarios with `@QaseTags:smoke`: add `tags: [smoke]`

### 9. Unit Tests

**File:** `qase-java-commons/src/test/java/io/qase/commons/utils/TestResultBuilderTest.java`

Add test methods for:

1. **`fromMethod` with `@QaseTags`**: method annotated with `@QaseTags({"smoke", "regression"})` produces `result.tags` containing both values
2. **`fromMethod` without `@QaseTags`**: `result.tags` is empty list (not null)
3. **`fromAnnotationReader` with `QaseTags`**: reader returns QaseTags annotation, tags are populated
4. **`fromCucumber` with `@QaseTags:smoke,regression`**: tags are parsed and populated
5. **`fromCucumber` with multiple `@QaseTags`**: `@QaseTags:tag1` + `@QaseTags:tag2` accumulate

**File:** `qase-java-commons/src/test/java/io/qase/commons/utils/CucumberUtilsTest.java` (new or extend existing)

Add test methods for:

1. **Basic parsing**: `@QaseTags:smoke,regression` -> `["smoke", "regression"]`
2. **Case-insensitive**: `@qasetags:Smoke` -> `["Smoke"]`
3. **Trimming**: `@QaseTags:smoke , regression` -> `["smoke", "regression"]`
4. **Accumulation**: multiple `@QaseTags` tags produce combined list
5. **Empty/missing**: no `@QaseTags` tags -> empty list
6. **Equals delimiter**: `@QaseTags=smoke` -> `["smoke"]`

### 10. Documentation

Update the following files:

1. **`changelog.md`**: Add entry for version 4.1.50 describing Tags support
2. **`qase-junit5-reporter/docs/usage.md`**: Add "Adding Tags" section with examples
3. **`qase-junit5-reporter/README.md`**: Add Tags to features list and usage example
4. **`qase-junit4-reporter/README.md`**: Add mention of `@QaseTags`
5. **`qase-testng-reporter/README.md`**: Add mention of `@QaseTags`
6. **`qase-cucumber-v7-reporter/README.md`**: Add mention of `@QaseTags:` Gherkin tag
7. **Other cucumber reporter READMEs**: Add brief mention

### 11. Version Bump

Bump version `4.1.49` -> `4.1.50` in all `pom.xml` files (parent + all modules).

## Files to Create

| File | Description |
|------|-------------|
| `qase-java-commons/src/main/java/io/qase/commons/annotation/QaseTags.java` | New annotation |

## Files to Modify

| File | Change |
|------|--------|
| `qase-java-commons/.../models/domain/TestResult.java` | Add `tags` field |
| `qase-java-commons/.../utils/IntegrationUtils.java` | Add `getQaseTags()` |
| `qase-java-commons/.../utils/TestResultBuilder.java` | Extract tags in all 3 entry points |
| `qase-java-commons/.../utils/CucumberUtils.java` | Add `getCaseTags()` |
| `qase-java-commons/.../client/ApiClientV2.java` | Map tags to API fields |
| `qase-java-commons/.../utils/TestResultBuilderTest.java` | Add tag-related tests |
| `examples/junit5/junit5-maven/.../SimpleTests.java` | Add `@QaseTags` examples |
| `examples/junit4/junit4-maven/.../SimpleTests.java` | Add `@QaseTags` examples |
| `examples/testng/testng-maven/.../SimpleTests.java` | Add `@QaseTags` examples |
| `examples/cucumber5/.../simple-tests.feature` | Add `@QaseTags:` tags |
| `examples/cucumber7/.../simple-tests.feature` | Add `@QaseTags:` tags |
| `expected/junit5-examples.yaml` | Add `tags:` field |
| `expected/junit4-examples.yaml` | Add `tags:` field |
| `expected/testng-examples.yaml` | Add `tags:` field |
| `expected/cucumber5-examples.yaml` | Add `tags:` field |
| `expected/cucumber7-examples.yaml` | Add `tags:` field |
| `changelog.md` | Add 4.1.50 entry |
| `qase-junit5-reporter/docs/usage.md` | Add Tags section |
| `qase-junit5-reporter/README.md` | Add Tags mention |
| `qase-junit4-reporter/README.md` | Add Tags mention |
| `qase-testng-reporter/README.md` | Add Tags mention |
| `qase-cucumber-v7-reporter/README.md` | Add Tags mention |
| All `pom.xml` files | Version bump to 4.1.50 |

## Out of Scope

- Class-level annotation support (all existing annotations are method-level only)
- `@Repeatable` annotation pattern (not used in this project)
- Configuration-based tags (tags are always specified per-test via annotations/Gherkin tags)
