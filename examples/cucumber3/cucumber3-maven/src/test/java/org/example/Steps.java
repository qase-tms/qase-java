package org.example;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.qase.cucumber3.Qase;
import org.testng.Assert;

import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Steps {

    // --- Simple steps ---

    @Then("the test passes")
    public void theTestPasses() {
        Assert.assertTrue(true);
    }

    // --- Attachment steps ---

    @When("a comment is added")
    public void addComment() {
        Qase.comment("This is a comment added during test execution");
    }

    @When("a file is attached")
    public void attachFile() {
        URL resource = getClass().getClassLoader().getResource("test-attachment.txt");
        if (resource != null) {
            Qase.attach(resource.getPath());
        }
    }

    @When("string content is attached")
    public void attachStringContent() {
        Qase.attach("log-output.txt", "2024-01-15 INFO: Test started\n2024-01-15 INFO: Test completed", "text/plain");
    }

    @When("byte array content is attached")
    public void attachByteArrayContent() {
        String jsonContent = "{\"status\": \"passed\", \"duration\": 1500}";
        byte[] bytes = jsonContent.getBytes(StandardCharsets.UTF_8);
        Qase.attach("result.json", bytes, "application/json");
    }

    @When("a nested step adds an attachment")
    public void nestedStepWithAttachment() {
        outerStep();
    }

    private void outerStep() {
        innerStepWithAttachment();
    }

    private void innerStepWithAttachment() {
        Qase.attach("nested-log.txt", "Log from inside a nested step", "text/plain");
    }

    // --- Parameterized steps ---

    @Given("a user with username {string}")
    public void userWithUsername(String username) {
        System.out.println("User: " + username);
    }

    @When("the user attempts to login")
    public void userAttemptsLogin() {
        System.out.println("Attempting login...");
    }

    @Then("the login result is {string}")
    public void loginResult(String result) {
        System.out.println("Login result: " + result);
    }

    @Given("an item {string} with quantity {int}")
    public void itemWithQuantity(String item, int quantity) {
        System.out.println("Item: " + item + ", Quantity: " + quantity);
    }

    @When("the item is processed")
    public void itemProcessed() {
        System.out.println("Processing item...");
    }

    @Then("the processing completes")
    public void processingCompletes() {
        System.out.println("Processing complete.");
    }
}
