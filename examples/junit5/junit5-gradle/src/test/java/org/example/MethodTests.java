package org.example;

import io.qase.commons.annotation.Step;
import org.junit.jupiter.api.Test;
import io.qase.junit5.Qase;

public class MethodTests {

    @Test
    public void testWithComment_success() {
        Qase.comment("Test with comment success");
        System.out.println("Test with comment success");
    }

    @Test
    public void testWithComment_failed() {
        Qase.comment("Test with comment failed");
        System.out.println("Test with comment failed");
        throw new RuntimeException("Test with comment failed");
    }

    @Test
    public void testWithFileAttachment_success() {
        Qase.attach("/Users/gda/Downloads/result.json");
        System.out.println("Test with attachment success");
    }

    @Test
    public void testWithFileAttachment_failed() {
        Qase.attach("/Users/gda/Downloads/result.json");
        System.out.println("Test with attachment failed");
        throw new RuntimeException("Test with attachment failed");
    }

    @Test
    public void testWithContentAttachment_success() {
        Qase.attach("file1.txt", "Content of file", "text/plain");
        System.out.println("Test with link attachment success");
    }

    @Test
    public void testWithContentAttachment_failed() {
        Qase.attach("file1.txt", "Content of file", "text/plain");
        System.out.println("Test with link attachment failed");
        throw new RuntimeException("Test with link attachment failed");
    }

    @Step("Step with attachment")
    public void stepWithAttachment() {
        Qase.attach("file.txt", "Content of file", "text/plain");
        System.out.println("Step with attachment");
    }

    @Test
    public void testWithStepAttachment_success() {
        System.out.println("Test with step attachment success");
        stepWithAttachment();
    }

    @Test
    public void testWithStepAttachment_failed() {
        System.out.println("Test with step attachment failed");
        stepWithAttachment();
        throw new RuntimeException("Test with step attachment failed");
    }
}
