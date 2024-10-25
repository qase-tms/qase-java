import io.qase.commons.annotation.Step
import io.qase.testng.Qase
import kotlin.test.Test

class MethodTests {

    @Test
    fun testWithComment_success() {
        Qase.comment("Test with comment success")
        println("Test with comment success")
    }

    @Test
    fun testWithComment_failed() {
        Qase.comment("Test with comment failed")
        println("Test with comment failed")
        throw RuntimeException("Test with comment failed")
    }

    @Test
    fun testWithFileAttachment_success() {
        Qase.attach("/Users/gda/Downloads/result.json")
        println("Test with attachment success")
    }

    @Test
    fun testWithFileAttachment_failed() {
        Qase.attach("/Users/gda/Downloads/result.json")
        println("Test with attachment failed")
        throw RuntimeException("Test with attachment failed")
    }

    @Test
    fun testWithContentAttachment_success() {
        Qase.attach("file1.txt", "Content of file", "text/plain")
        println("Test with link attachment success")
    }

    @Test
    fun testWithContentAttachment_failed() {
        Qase.attach("file1.txt", "Content of file", "text/plain")
        println("Test with link attachment failed")
        throw RuntimeException("Test with link attachment failed")
    }

    @Step("Step with attachment")
    fun stepWithAttachment() {
        Qase.attach("file.txt", "Content of file", "text/plain")
        println("Step with attachment")
    }

    @Test
    fun testWithStepAttachment_success() {
        println("Test with step attachment success")
        stepWithAttachment()
    }

    @Test
    fun testWithStepAttachment_failed() {
        println("Test with step attachment failed")
        stepWithAttachment()
        throw RuntimeException("Test with step attachment failed")
    }
}
