package org.example;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qase.cucumber5.Qase;
import org.testng.Assert;

public class Steps {
    @Then("return true")
    public void returnTrue() {
        Assert.assertTrue(true);
    }

    @Then("return false")
    public void returnFalse() {
        Assert.assertTrue(false);
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
