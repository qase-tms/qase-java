package org.example;

import io.qase.commons.annotation.*;
import io.qase.commons.models.annotation.Field;
import org.junit.jupiter.api.Test;

public class SimpleTests {

    @Test
    public void testWithoutAnnotation_success() {
        System.out.println("Test without annotation success");
    }

    @Test
    public void testWithoutAnnotation_filed() {
        System.out.println("Test without annotation failed");
        throw new RuntimeException("Test without annotation failed");
    }

    @Test
    @QaseId(1)
    public void testWithQaseId_success() {
        System.out.println("Test with QaseId");
    }

    @Test
    @QaseId(2)
    public void testWithQaseId_failed() {
        System.out.println("Test with QaseId");
        throw new RuntimeException("Test with QaseId failed");
    }

    @Test
    @QaseTitle("Test with title success")
    public void testWithTitle_success() {
        System.out.println("Test with title success");
    }

    @Test
    @QaseTitle("Test with title failed")
    public void testWithTitle_failed() {
        System.out.println("Test with title failed");
        throw new RuntimeException("Test with title failed");
    }


    @Test
    @QaseFields(value = {
            @Field(name = "description", value = "Description of test success"),
            @Field(name = "severity", value = "major"),
    })
    public void testWithFields_success() {
        System.out.println("Test with fields success");
    }

    @Test
    @QaseFields(value = {
            @Field(name = "description", value = "Description of test failed"),
            @Field(name = "severity", value = "major"),
    })
    public void testWithFields_failed() {
        System.out.println("Test with fields failed");
        throw new RuntimeException("Test with fields failed");
    }

    @Test
    @QaseIgnore
    public void testWithIgnore_success() {
        System.out.println("Test with ignore success");
    }

    @Test
    @QaseIgnore
    public void testWithIgnore_failed() {
        System.out.println("Test with ignore failed");
        throw new RuntimeException("Test with ignore failed");
    }

    @Test
    @QaseSuite("Suite 1")
    public void testWithSuite_success() {
        System.out.println("Test with suite success");
    }

    @Test
    @QaseSuite("Suite 1")
    public void testWithSuite_failed() {
        System.out.println("Test with suite failed");
        throw new RuntimeException("Test with suite failed");
    }

    @Test
    @QaseSuite("Suite 2\tSub suite")
    public void testWithMultipleSuites_success() {
        System.out.println("Test with multiple suites success");
    }

    @Test
    @QaseSuite("Suite 2\tSub suite")
    public void testWithMultipleSuites_failed() {
        System.out.println("Test with multiple suites failed");
        throw new RuntimeException("Test with multiple suites failed");
    }

    @Step("Step 1")
    private void step01() {
        step02();
        System.out.println("Step 1");
    }

    @Step("Step 2")
    private void step02() {
        System.out.println("Step 2");
    }
}
