package example;

import io.qase.commons.annotation.*;
import io.qase.commons.models.annotation.Field;
import org.junit.Test;

public class SimpleTests {

    @Test
    @QaseId(301)
    public void testWithQaseId() {
        System.out.println("Test linked to case ID 301");
    }

    @Test
    @QaseIds({302, 303, 304})
    public void testWithMultipleQaseIds() {
        System.out.println("Test linked to case IDs 302, 303, and 304");
    }

    @Test
    @QaseId(305)
    @QaseTitle("Custom test title for reporting")
    public void testWithTitle() {
        System.out.println("Test with a custom title");
    }

    @Test
    @QaseId(306)
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
    @QaseId(307)
    @QaseSuite("Suite 1")
    public void testWithSuite() {
        System.out.println("Test assigned to Suite 1");
    }

    @Test
    @QaseId(308)
    @QaseSuite("Parent suite\tChild suite\tGrandchild suite")
    public void testWithNestedSuites() {
        System.out.println("Test assigned to nested suite hierarchy");
    }

    @Test
    @QaseId(309)
    @QaseIgnore
    public void testWithIgnore() {
        System.out.println("This test is excluded from Qase reporting");
    }

    @Test
    @QaseId(310)
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
    @QaseId(311)
    public void testWithFailure() {
        System.out.println("This test will fail");
        throw new AssertionError("Expected failure to demonstrate failure reporting");
    }
}
