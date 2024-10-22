# How add QaseID to a test

You can add QaseID to a test in Cucumber 4 by using the `@QaseId` annotation. This annotation can receive a single ID in
the
form of an integer.

Example:

```gherkin
Feature: Simple tests
  Here are some simple tests

  @QaseId=1
  Scenario: With QaseID
    Then return true
```

# How add Title to a test

You can add a title to a test in Cucumber 4 by using the `@QaseTitle` annotation. This annotation can receive a single
title
in the form of a string. If you don't use this annotation then the title will be the name of the test method.

Example:

```gherkin
Feature: Simple tests
  Here are some simple tests

  @QaseTitle=My_custom_title
  Scenario: With QaseID
    Then return true
```

# How add Fields to a test

You can add fields to a test in Cucumber 4 by using the `@QaseFields` annotation. You can add multiple fields to a test.

List of system fields:

- `description` - description of the test case
- `preconditions` - preconditions of the test case
- `postconditions` - postconditions of the test case
- `severity` - severity of the test case
- `priority` - priority of the test case
- `layer` - layer of the test case

Example:

```gherkin
Feature: Simple tests
  Here are some simple tests

  @QaseFields={"description":"Some_description","severity":"major"}
  Scenario: With custom fields
    Then return true
```

# How add Suite to a test

You can add a suite to a test in Cucumber 4 by using the `@QaseSuite` annotation. This annotation can receive a single
suite
and a multiple sub-suite in the form of a string.

Example:

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

# How ignore a test

You can ignore a test in Cucumber 4 by using the `@QaseIgnore` annotation. This annotation will ignore the test in Qase.
The
test will be executed, but the results will not be sent to Qase.

Example:

```gherkin
Feature: Simple tests
  Here are some simple tests

  @QaseIgnore
  Scenario: With ignore
    Then return true
```

# How add a comment to a test

You can add a comment to a test in Cucumber 4 by using the `Qase.comment` method. This method can receive a single
comment
in
the form of a string. This comment will be added to the test result in Qase.

Example:

```java
package org.example;

import io.qase.cucumber4.Qase;
import cucumber.api.java.en.When;

public class Steps {
    @When("add comment")
    public void addMessage() {
        Qase.comment("Hello, Qase.io!");
    }
}
```

# How attach a file to a test

You can attach a file to a test in Cucumber 4 by using the `Qase.attach` method. This method can receive a single file
or a
multiple files in the form of an array of strings. Also, you can specify a name of the file, comment and type of the
file. These files will be attached to the test result in Qase.

Example:

```java
package org.example;

import io.qase.cucumber4.Qase;
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
