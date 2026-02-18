package org.example;

import io.qase.commons.annotation.*;
import io.qase.commons.models.annotation.Field;
import org.testng.annotations.Test;

public class SimpleTests {

    @Test
    public void testWithoutAnnotation() {
        System.out.println("Test without any Qase annotation");
    }

    @Test
    @QaseId(1)
    public void testWithQaseId() {
        System.out.println("Test linked to case ID 1");
    }

    @Test
    @QaseIds({1, 2, 3})
    public void testWithMultipleQaseIds() {
        System.out.println("Test linked to case IDs 1, 2, and 3");
    }

    @Test
    @QaseTitle("Custom test title for reporting")
    public void testWithTitle() {
        System.out.println("Test with a custom title");
    }

    @Test
    @QaseFields(value = {
            @Field(name = "description", value = "Verifies login with valid credentials"),
            @Field(name = "severity", value = "critical"),
            @Field(name = "priority", value = "high"),
            @Field(name = "layer", value = "unit"),
    })
    public void testWithFields() {
        System.out.println("Test with custom fields");
    }

    @Test
    @QaseSuite("Suite 1")
    public void testWithSuite() {
        System.out.println("Test assigned to Suite 1");
    }

    @Test
    @QaseSuite("Parent suite\tChild suite\tGrandchild suite")
    public void testWithNestedSuites() {
        System.out.println("Test assigned to nested suite hierarchy");
    }

    @Test
    @QaseIgnore
    public void testWithIgnore() {
        System.out.println("This test is excluded from Qase reporting");
    }

    @Test
    @QaseId(100)
    @QaseTitle("Combined annotations example")
    @QaseFields(value = {
            @Field(name = "description", value = "Demonstrates using multiple annotations together"),
            @Field(name = "severity", value = "normal"),
    })
    @QaseSuite("Regression\tSmoke")
    public void testWithCombinedAnnotations() {
        System.out.println("Test with multiple Qase annotations combined");
    }

    @Test(expectedExceptions = AssertionError.class)
    public void testWithFailure() {
        System.out.println("This test will fail");
        throw new AssertionError("Expected failure to demonstrate failure reporting");
    }
}
