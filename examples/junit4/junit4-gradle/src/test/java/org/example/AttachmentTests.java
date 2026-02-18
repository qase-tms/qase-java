package org.example;

import io.qase.commons.annotation.QaseTitle;
import io.qase.commons.annotation.Step;
import io.qase.junit4.Qase;
import org.junit.Test;

import java.net.URL;
import java.nio.charset.StandardCharsets;

public class AttachmentTests {

    @Test
    @QaseTitle("Add a comment to test results")
    public void testWithComment() {
        Qase.comment("This is a comment added during test execution");
        System.out.println("Test with comment");
    }

    @Test
    @QaseTitle("Attach a file by path")
    public void testWithFileAttachment() {
        URL resource = getClass().getClassLoader().getResource("test-attachment.txt");
        if (resource != null) {
            Qase.attach(resource.getPath());
        }
        System.out.println("Test with file attachment");
    }

    @Test
    @QaseTitle("Attach string content")
    public void testWithStringAttachment() {
        Qase.attach("log-output.txt", "2024-01-15 INFO: Test started\n2024-01-15 INFO: Test completed", "text/plain");
        System.out.println("Test with string content attachment");
    }

    @Test
    @QaseTitle("Attach byte array content")
    public void testWithByteArrayAttachment() {
        String jsonContent = "{\"status\": \"passed\", \"duration\": 1500}";
        byte[] bytes = jsonContent.getBytes(StandardCharsets.UTF_8);
        Qase.attach("result.json", bytes, "application/json");
        System.out.println("Test with byte array attachment");
    }

    @Step("Step that creates an attachment")
    public void stepWithAttachment() {
        Qase.attach("step-log.txt", "Log from inside a step", "text/plain");
        System.out.println("Step with attachment");
    }

    @Test
    @QaseTitle("Attachment inside a step")
    public void testWithAttachmentInStep() {
        System.out.println("Test demonstrating attachment inside a step");
        stepWithAttachment();
    }

    @Test
    @QaseTitle("Multiple attachments in one test")
    public void testWithMultipleAttachments() {
        Qase.comment("Test with multiple attachments");
        Qase.attach("notes.txt", "First attachment content", "text/plain");
        byte[] data = "<html><body>Report</body></html>".getBytes(StandardCharsets.UTF_8);
        Qase.attach("report.html", data, "text/html");
        System.out.println("Test with multiple attachments");
    }
}
