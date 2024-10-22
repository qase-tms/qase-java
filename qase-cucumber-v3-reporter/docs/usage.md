# Integrating Qase with Cucumber 3

This guide provides instructions for integrating Qase with Cucumber 3, showing how to add Qase IDs, titles, fields,
suites, comments, and file attachments to your scenarios.

---

## 1. Adding QaseID to a Test

You can link a test to a specific Qase test case using the `@QaseId` annotation. This annotation accepts a single
integer ID that represents the test case.

### Example:

```gherkin
Feature: Simple tests
  Here are some simple tests

  @QaseId=1
  Scenario: Test with QaseID
    Then return true
```

---

## 2. Adding a Title to a Test

To provide a custom title for a test, use the `@QaseTitle` annotation. If this annotation is not specified, the title
will default to the name of the test method.

### Example:

```gherkin
Feature: Simple tests
  Here are some simple tests

  @QaseTitle=My_custom_title
  Scenario: Test with Custom Title
    Then return true
```

---

## 3. Adding Fields to a Test

You can include additional metadata in your test using the `@QaseFields` annotation, allowing for multiple fields to be
specified in JSON format.

### System Fields:

- `description` — Description of the test case.
- `preconditions` — Preconditions for the test case.
- `postconditions` — Postconditions for the test case.
- `severity` — Severity of the test case (e.g., `critical`, `major`).
- `priority` — Priority of the test case (e.g., `high`, `low`).
- `layer` — Test layer (e.g., `UI`, `API`).

### Example:

```gherkin
Feature: Simple tests
  Here are some simple tests

  @QaseFields={"description":"Some_description","severity":"major"}
  Scenario: Test with Custom Fields
    Then return true
```

---

## 4. Adding a Suite to a Test

You can organize your tests into suites using the `@QaseSuite` annotation. This annotation can accept a single suite
name or multiple sub-suite names as a string.

### Example:

```gherkin
Feature: Simple tests
  Here are some simple tests

  @QaseSuite=Suite01
  Scenario: Test in a Single Suite
    Then return true

  @QaseSuite=Suite01\tSubSuite01
  Scenario: Test in Sub Suites
    Then return true
```

---

## 5. Ignoring a Test

To skip a test in Qase while still executing it, use the `@QaseIgnore` annotation. The test will run, but results will
not be sent to Qase.

### Example:

```gherkin
Feature: Simple tests
  Here are some simple tests

  @QaseIgnore
  Scenario: Ignored Test
    Then return true
```

---

## 6. Adding a Comment to a Test

You can add comments to your test results in Qase using the `Qase.comment` method. This method accepts a single string
as the comment.

### Example:

```java
package org.example;

import io.qase.cucumber3.Qase;
import cucumber.api.java.en.When;

public class Steps {
    @When("add comment")
    public void addComment() {
        Qase.comment("Hello, Qase.io!");
    }
}
```

---

## 7. Attaching a File to a Test

You can attach files to your test results using the `Qase.attach` method. This method accepts a single file or multiple
files in the form of an array of strings. You can also specify the file name, comment, and type.

### Example:

```java
package org.example;

import io.qase.cucumber3.Qase;
import cucumber.api.java.en.When;

public class Steps {

    @When("add attachments from file")
    public void addAttachments() {
        Qase.attach("/Users/gda/Downloads/second.txt");
    }

    @When("add attachments from content")
    public void addAttachmentsContent() {
        Qase.attach("file.txt", "Content", "text/plain");
    }
}
```
