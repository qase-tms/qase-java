package org.example;

import io.qase.commons.annotation.*;
import io.qase.commons.models.annotation.Field;
import org.testng.annotations.Test;

public class SimpleTests {

    @Test
    @QaseId(201)
    public void testWithQaseId() {
        System.out.println("Test linked to case ID 201");
    }

    @Test
    @QaseIds({202, 203, 204})
    public void testWithMultipleQaseIds() {
        System.out.println("Test linked to case IDs 202, 203, and 204");
    }

    @Test
    @QaseId(205)
    @QaseTitle("Custom test title for reporting")
    public void testWithTitle() {
        System.out.println("Test with a custom title");
    }

    @Test
    @QaseId(206)
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
    @QaseId(207)
    @QaseSuite("Suite 1")
    public void testWithSuite() {
        System.out.println("Test assigned to Suite 1");
    }

    @Test
    @QaseId(208)
    @QaseSuite("Parent suite\tChild suite\tGrandchild suite")
    public void testWithNestedSuites() {
        System.out.println("Test assigned to nested suite hierarchy");
    }

    @Test
    @QaseId(209)
    @QaseIgnore
    public void testWithIgnore() {
        System.out.println("This test is excluded from Qase reporting");
    }

    @Test
    @QaseId(210)
    @QaseTitle("Combined annotations example")
    @QaseFields(value = {
            @Field(name = "description", value = "Demonstrates using multiple annotations together"),
            @Field(name = "severity", value = "normal"),
    })
    @QaseSuite("Regression\tSmoke")
    public void testWithCombinedAnnotations() {
        System.out.println("Test with multiple Qase annotations combined");
    }

    @Test
    @QaseId(211)
    public void testWithFailure() {
        System.out.println("This test will fail");
        throw new AssertionError("Expected failure to demonstrate failure reporting");
    }
}
