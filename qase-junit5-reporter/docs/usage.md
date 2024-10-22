# Qase Integration in JUnit5

This guide demonstrates how to integrate Qase with JUnit5, providing instructions on how to add Qase IDs, titles,
fields, suites, comments, and file attachments to your test cases.

---

## How to Add QaseID to a Test

You can associate a QaseID with a test in JUnit5 by using the `@QaseId` annotation. This annotation accepts a single
integer representing the test case ID in Qase.

### Example:

```java
package org.example;

import io.qase.commons.annotation.QaseId;
import org.junit.jupiter.api.Test;

public class SimpleTests {

    @Test
    @QaseId(1)
    public void test() {
        System.out.println("Example test");
    }
}
```

---

## How to Add a Title to a Test

To set a custom title for a test in JUnit5, use the `@QaseTitle` annotation. This annotation accepts a string as the
title. If you don't specify a title, the method name of the test will be used as the default title.

### Example:

```java
package org.example;

import io.qase.commons.annotation.QaseTitle;
import org.junit.jupiter.api.Test;

public class SimpleTests {

    @Test
    @QaseTitle("Example test")
    public void test() {
        System.out.println("Example test");
    }
}
```

---

## How to Add Fields to a Test

You can enrich a test case with additional metadata by using the `@QaseFields` annotation in JUnit5. This annotation
allows adding multiple fields that describe the test case.

### System Fields:

- `description` — Description of the test case.
- `preconditions` — Preconditions for the test case.
- `postconditions` — Postconditions for the test case.
- `severity` — Severity of the test case (e.g., `critical`, `major`).
- `priority` — Priority of the test case (e.g., `high`, `low`).
- `layer` — Test layer (e.g., `UI`, `API`).

### Example:

```java
package org.example;

import io.qase.commons.annotation.QaseFields;
import io.qase.commons.models.annotation.Field;
import org.junit.jupiter.api.Test;

public class SimpleTests {

    @Test
    @QaseFields({
            @Field(name = "description", value = "Description of the test case"),
            @Field(name = "preconditions", value = "Preconditions of the test case"),
            @Field(name = "postconditions", value = "Postconditions of the test case"),
            @Field(name = "severity", value = "critical"),
            @Field(name = "priority", value = "high"),
            @Field(name = "layer", value = "API")
    })
    public void test() {
        System.out.println("Example test");
    }
}
```

---

## How to Add a Suite to a Test

To categorize your test into a suite or sub-suite, use the `@QaseSuite` annotation in JUnit5. This annotation can
receive a suite and optionally sub-suites as strings.

### Example:

```java
package org.example;

import io.qase.commons.annotation.QaseSuite;
import org.junit.jupiter.api.Test;

public class SimpleTests {

    @Test
    @QaseSuite("Suite")
    public void testSuite() {
        System.out.println("Example test");
    }

    @Test
    @QaseSuite("Suite\tSubSuite")
    public void testSubSuite() {
        System.out.println("Example test");
    }
}
```

---

## How to Ignore a Test

To exclude a test from being reported in Qase, use the `@QaseIgnore` annotation in JUnit5. The test will still be
executed, but its results will not be sent to Qase.

### Example:

```java
package org.example;

import io.qase.commons.annotation.QaseIgnore;
import org.junit.jupiter.api.Test;

public class SimpleTests {

    @Test
    @QaseIgnore
    public void ignoredTest() {
        System.out.println("Example test");
    }
}
```

---

## How to Add a Comment to a Test

You can attach a comment to a test result in Qase using the `Qase.comment` method. This comment will be visible in the
test result within Qase.

### Example:

```java
package org.example;

import io.qase.junit5.Qase;
import org.junit.jupiter.api.Test;

public class SimpleTests {

    @Test
    public void testWithComment() {
        System.out.println("Example test");
        Qase.comment("This is a comment for the test result.");
    }
}
```

---

## How to Attach Files to a Test

You can attach files to a test result in Qase using the `Qase.attach` method. It supports attaching one or multiple
files. You can also specify a file name, a comment, and a file type (MIME type).

### Example:

```java
package org.example;

import io.qase.junit5.Qase;
import org.junit.jupiter.api.Test;

public class SimpleTests {

    @Test
    public void testWithAttachments() {
        System.out.println("Example test");
        Qase.attach("path/to/file.txt");  // Attach a single file
        Qase.attach("path/to/file.txt", "path/to/file1.txt");  // Attach multiple files
        Qase.attach("file.txt", "Attached with comment", "text/plain");  // Attach with comment and type
    }
}
```
