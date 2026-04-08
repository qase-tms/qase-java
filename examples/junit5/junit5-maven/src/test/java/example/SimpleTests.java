package example;

import io.qase.commons.annotation.*;
import io.qase.commons.models.annotation.Field;
import org.junit.jupiter.api.Test;

public class SimpleTests {

    @Test
    @QaseId(1)
    public void testWithQaseId() {
        System.out.println("Test linked to case ID 1");
    }

    @Test
    @QaseIds({2, 3, 4})
    public void testWithMultipleQaseIds() {
        System.out.println("Test linked to case IDs 2, 3, and 4");
    }

    @Test
    @QaseId(5)
    @QaseTitle("Custom test title for reporting")
    public void testWithTitle() {
        System.out.println("Test with a custom title");
    }

    @Test
    @QaseId(6)
    @QaseTags({"smoke"})
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
    @QaseId(7)
    @QaseSuite("Suite 1")
    public void testWithSuite() {
        System.out.println("Test assigned to Suite 1");
    }

    @Test
    @QaseId(8)
    @QaseSuite("Parent suite\tChild suite\tGrandchild suite")
    public void testWithNestedSuites() {
        System.out.println("Test assigned to nested suite hierarchy");
    }

    @Test
    @QaseId(9)
    @QaseIgnore
    public void testWithIgnore() {
        System.out.println("This test is excluded from Qase reporting");
    }

    @Test
    @QaseId(100)
    @QaseTags({"regression"})
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
    @QaseId(101)
    public void testWithFailure() {
        System.out.println("This test will fail");
        throw new AssertionError("Expected failure to demonstrate failure reporting");
    }
}
