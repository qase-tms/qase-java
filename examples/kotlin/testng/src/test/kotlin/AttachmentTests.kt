import io.qase.commons.annotation.QaseTitle
import io.qase.commons.annotation.Step
import io.qase.testng.Qase
import org.testng.annotations.Test
import java.nio.charset.StandardCharsets

class AttachmentTests {

    @Test
    @QaseTitle("Add a comment to test results")
    fun testWithComment() {
        Qase.comment("This is a comment added during test execution")
        println("Test with comment")
    }

    @Test
    @QaseTitle("Attach a file by path")
    fun testWithFileAttachment() {
        val resource = javaClass.classLoader.getResource("test-attachment.txt")
        resource?.let {
            Qase.attach(it.path)
        }
        println("Test with file attachment")
    }

    @Test
    @QaseTitle("Attach string content")
    fun testWithStringAttachment() {
        Qase.attach("log-output.txt", "2024-01-15 INFO: Test started\n2024-01-15 INFO: Test completed", "text/plain")
        println("Test with string content attachment")
    }

    @Test
    @QaseTitle("Attach byte array content")
    fun testWithByteArrayAttachment() {
        val jsonContent = "{\"status\": \"passed\", \"duration\": 1500}"
        val bytes = jsonContent.toByteArray(StandardCharsets.UTF_8)
        Qase.attach("result.json", bytes, "application/json")
        println("Test with byte array attachment")
    }

    @Step("Step that creates an attachment")
    fun stepWithAttachment() {
        Qase.attach("step-log.txt", "Log from inside a step", "text/plain")
        println("Step with attachment")
    }

    @Test
    @QaseTitle("Attachment inside a step")
    fun testWithAttachmentInStep() {
        println("Test demonstrating attachment inside a step")
        stepWithAttachment()
    }

    @Test
    @QaseTitle("Multiple attachments in one test")
    fun testWithMultipleAttachments() {
        Qase.comment("Test with multiple attachments")
        Qase.attach("notes.txt", "First attachment content", "text/plain")
        val data = "<html><body>Report</body></html>".toByteArray(StandardCharsets.UTF_8)
        Qase.attach("report.html", data, "text/html")
        println("Test with multiple attachments")
    }
}
