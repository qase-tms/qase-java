import io.qase.commons.annotation.*
import io.qase.commons.models.annotation.Field
import org.junit.jupiter.api.Test

class SimpleTests {

    @Test
    fun testWithoutAnnotation() {
        println("Test without any Qase annotation")
    }

    @Test
    @QaseId(1)
    fun testWithQaseId() {
        println("Test linked to case ID 1")
    }

    @Test
    @QaseIds(intArrayOf(1, 2, 3))
    fun testWithMultipleQaseIds() {
        println("Test linked to case IDs 1, 2, and 3")
    }

    @Test
    @QaseTitle("Custom test title for reporting")
    fun testWithTitle() {
        println("Test with a custom title")
    }

    @Test
    @QaseFields(
        value = [
            Field(name = "description", value = "Verifies login with valid credentials"),
            Field(name = "severity", value = "critical"),
            Field(name = "priority", value = "high"),
            Field(name = "layer", value = "unit")
        ]
    )
    fun testWithFields() {
        println("Test with custom fields")
    }

    @Test
    @QaseSuite("Suite 1")
    fun testWithSuite() {
        println("Test assigned to Suite 1")
    }

    @Test
    @QaseSuite("Parent suite\tChild suite\tGrandchild suite")
    fun testWithNestedSuites() {
        println("Test assigned to nested suite hierarchy")
    }

    @Test
    @QaseIgnore
    fun testWithIgnore() {
        println("This test is excluded from Qase reporting")
    }

    @Test
    @QaseId(100)
    @QaseTitle("Combined annotations example")
    @QaseFields(
        value = [
            Field(name = "description", value = "Demonstrates using multiple annotations together"),
            Field(name = "severity", value = "normal")
        ]
    )
    @QaseSuite("Regression\tSmoke")
    fun testWithCombinedAnnotations() {
        println("Test with multiple Qase annotations combined")
    }

    @Test
    fun testWithFailure() {
        println("This test will fail")
        throw AssertionError("Expected failure to demonstrate failure reporting")
    }
}
