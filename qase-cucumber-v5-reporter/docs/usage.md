# Qase Integration in Cucumber 5

This guide provides instructions for integrating Qase with Cucumber 5, showing how to add Qase IDs, titles, fields,
suites, comments, and file attachments to your scenarios.

---

## How to Add QaseID to a Test

To associate a QaseID with a test scenario in Cucumber 5, use the `@QaseId` annotation. This annotation accepts a single
integer representing the test case ID in Qase.

### Example:

```gherkin
Feature: Simple tests
  Here are some simple tests

  @QaseId=1
  Scenario: With QaseID
    Then return true
```

---

## How to Add a Title to a Test

You can set a custom title for a scenario in Cucumber 5 using the `@QaseTitle` annotation. It accepts a string as the
title. If not provided, the scenario name will be used as the default title.

### Example:

```gherkin
Feature: Simple tests
  Here are some simple tests

  @QaseTitle=My_custom_title
  Scenario: With QaseID
    Then return true
```

---

## How to Add Fields to a Test

In Cucumber 5, you can enrich a test case with custom fields by using the `@QaseFields` annotation. Multiple fields can
be added to provide additional information about the test case.

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
  Scenario: With custom fields
    Then return true
```

---

## How to Add a Suite to a Test

You can organize your scenarios into suites and sub-suites in Cucumber 5 using the `@QaseSuite` annotation. This
annotation can receive a single suite name, and optionally sub-suite names, as strings.

### Example:

```gherkin
Feature: Simple tests
  Here are some simple tests

  @QaseSuite=Suite01
  Scenario: With single suite
    Then return true

  @QaseSuite=Suite01\tSubSuite01
  Scenario: With sub suites
    Then return true
```

---

## How to Ignore a Test

To prevent a scenario from being reported in Qase, use the `@QaseIgnore` annotation. The scenario will still be
executed, but the results will not be sent to Qase.

### Example:

```gherkin
Feature: Simple tests
  Here are some simple tests

  @QaseIgnore
  Scenario: With ignore
    Then return true
```

---

## How to Add a Comment to a Test

You can add a comment to a test result in Qase using the `Qase.comment` method. This comment will be attached to the
scenario's result in Qase.

### Example:

```java
package org.example;

import io.qase.cucumber5.Qase;
import io.cucumber.java.en.When;

public class Steps {
    @When("add comment")
    public void addMessage() {
        Qase.comment("Hello, Qase.io!");
    }
}
```

---

## How to Attach a File to a Test

In Cucumber 5, files can be attached to test results in Qase using the `Qase.attach` method. This method supports
attaching single or multiple files, and allows specifying a file name, comment, and type.

### Example:

```java
package org.example;

import io.qase.cucumber5.Qase;
import io.cucumber.java.en.When;

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

```
