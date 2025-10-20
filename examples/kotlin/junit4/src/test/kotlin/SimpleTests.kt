import io.qase.commons.annotation.*
import io.qase.commons.models.annotation.Field
import org.junit.Test

class SimpleTests {

    @Test
    fun testWithoutAnnotation_success() {
        println("Test without annotation success")
    }

    @Test
    fun testWithoutAnnotation_filed() {
        println("Test without annotation failed")
        throw RuntimeException("Test without annotation failed")
    }

    @Test
    @QaseId(1)
    fun testWithQaseId_success() {
        println("Test with QaseId")
    }

    @Test
    @QaseId(2)
    fun testWithQaseId_failed() {
        println("Test with QaseId")
        throw RuntimeException("Test with QaseId failed")
    }

    @Test
    @QaseTitle("Test with title success")
    fun testWithTitle_success() {
        println("Test with title success")
    }

    @Test
    @QaseTitle("Test with title failed")
    fun testWithTitle_failed() {
        println("Test with title failed")
        throw RuntimeException("Test with title failed")
    }


    @Test
    @QaseFields(
        value = [Field(name = "description", value = "Description of test success"), Field(
            name = "severity",
            value = "major"
        )]
    )
    fun testWithFields_success() {
        println("Test with fields success")
    }

    @Test
    @QaseFields(
        value = [Field(name = "description", value = "Description of test failed"), Field(
            name = "severity",
            value = "major"
        )]
    )
    fun testWithFields_failed() {
        println("Test with fields failed")
        throw RuntimeException("Test with fields failed")
    }

    @Test
    @QaseIgnore
    fun testWithIgnore_success() {
        println("Test with ignore success")
    }

    @Test
    @QaseIgnore
    fun testWithIgnore_failed() {
        println("Test with ignore failed")
        throw RuntimeException("Test with ignore failed")
    }

    @Test
    @QaseSuite("Suite 1")
    fun testWithSuite_success() {
        println("Test with suite success")
    }

    @Test
    @QaseSuite("Suite 1")
    fun testWithSuite_failed() {
        println("Test with suite failed")
        throw RuntimeException("Test with suite failed")
    }

    @Test
    @QaseSuite("Suite 2\tSub suite")
    fun testWithMultipleSuites_success() {
        println("Test with multiple suites success")
    }

    @Test
    @QaseSuite("Suite 2\tSub suite")
    fun testWithMultipleSuites_failed() {
        println("Test with multiple suites failed")
        throw RuntimeException("Test with multiple suites failed")
    }

    @Step("Step 1")
    private fun step01() {
        step02()
        println("Step 1")
    }

    @Step("Step 2")
    private fun step02() {
        println("Step 2")
    }
}

