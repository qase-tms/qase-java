package org.example;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qase.cucumber7.Qase;
import org.junit.jupiter.api.Assertions;

public class Steps {
    @Then("return true")
    public void returnTrue() {
        Assertions.assertTrue(true);
    }

    @Then("return false")
    public void returnFalse() {
        Assertions.assertTrue(false);
    }

    @When("add comment")
    public void addMessage() {
        Qase.comment("Hello, Qase.io!");
    }

    @When("add attachments from file")
    public void addAttachments() {
        Qase.attach("/Users/gda/Downloads/second.txt");
    }

    @When("add attachments from content")
    public void addAttachmentsContent() {
        Qase.attach("file.txt", "Content", "text/plain");
    }
}
