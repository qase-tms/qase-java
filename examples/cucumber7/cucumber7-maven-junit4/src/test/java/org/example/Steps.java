package org.example;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.qase.cucumber7.Qase;
import org.junit.Assert;

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

    @Given("I have a parameter {word}")
    public void iHaveAParameter(String parameter) {
        System.out.println("Parameter: " + parameter);
    }

    @When("I do something with the parameter")
    public void iDoSomethingWithTheParameter(DataTable dataTable) {
        System.out.println("Doing something with the parameter...");
    }

    @Then("I should see the result")
    public void iShouldSeeTheResult() {
        System.out.println("Result is displayed.");
    }
}
