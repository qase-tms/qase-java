# Qase Integration in TestNG

This guide demonstrates how to integrate Qase with TestNG, providing instructions on how to add Qase IDs, titles,
fields, suites, comments, and file attachments to your test cases.

---

## Adding QaseID to a Test

To associate a QaseID with a test in TestNG, use the `@QaseId` annotation. This annotation accepts a single integer
representing the test's ID in Qase.

### Example:

```java
package org.example;

import io.qase.commons.annotation.QaseId;
import org.testng.annotations.Test;

public class SimpleTests {

    @Test
    @QaseId(1)
    public void test() {
        System.out.println("Example test");
    }
}
```

---

## Adding a Title to a Test

You can provide a title for your test using the `@QaseTitle` annotation. The annotation accepts a string, which will be
used as the test's title in Qase. If no title is provided, the test method name will be used by default.

### Example:

```java
package org.example;

import io.qase.commons.annotation.QaseTitle;
import org.testng.annotations.Test;

public class SimpleTests {

    @Test
    @QaseTitle("Example test")
    public void test() {
        System.out.println("Example test");
    }
}
```

---

## Adding Fields to a Test

The `@QaseFields` annotation allows you to add additional metadata to a test case. You can specify multiple fields to
enhance test case information in Qase.

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
import org.testng.annotations.Test;

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

## Adding a Suite to a Test

To assign a suite or sub-suite to a test, use the `@QaseSuite` annotation. It can receive a suite name, and optionally a
sub-suite, both as strings.

### Example:

```java
package org.example;

import io.qase.commons.annotation.QaseSuite;
import org.testng.annotations.Test;

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

## Ignoring a Test in Qase

To exclude a test from being reported to Qase (while still executing the test in TestNG), use the `@QaseIgnore`
annotation. The test will run, but its result will not be sent to Qase.

### Example:

```java
package org.example;

import io.qase.commons.annotation.QaseIgnore;
import org.testng.annotations.Test;

public class SimpleTests {

    @Test
    @QaseIgnore
    public void ignoredTest() {
        System.out.println("Example test");
    }
}
```

---

## Adding a Comment to a Test

You can attach comments to the test results in Qase using the `Qase.comment` method. The comment will be displayed
alongside the test execution details in Qase.

### Example:

```java
package org.example;

import io.qase.testng.Qase;
import org.testng.annotations.Test;

public class SimpleTests {

    @Test
    public void testWithComment() {
        System.out.println("Example test");
        Qase.comment("This is a comment for the test result.");
    }
}
```

---

## Attaching Files to a Test

To attach files to a test result, use the `Qase.attach` method. This method supports attaching one or multiple files,
along with optional file names, comments, and file types.

### Example:

```java
package org.example;

import io.qase.testng.Qase;
import org.testng.annotations.Test;

public class SimpleTests {

    @Test
    public void testWithAttachments() {
        System.out.println("Example test");
        Qase.attach("path/to/file.txt");  // Attach a single file
        Qase.attach("path/to/file.txt", "path/to/file1.txt");  // Attach multiple files
        Qase.attach("file.txt", "Attached with comment", "text/plain");  // Attach file with comment and type
    }
}
```
