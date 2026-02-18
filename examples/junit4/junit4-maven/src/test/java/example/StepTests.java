package example;

import io.qase.commons.annotation.QaseId;
import io.qase.commons.annotation.QaseTitle;
import io.qase.commons.annotation.Step;
import org.junit.Test;

public class StepTests {

    // --- Level 1 steps ---

    @Step("Open the application")
    public void openApplication() {
        System.out.println("Opening the application");
        navigateToLoginPage();
    }

    // --- Level 2 steps ---

    @Step("Navigate to login page")
    public void navigateToLoginPage() {
        System.out.println("Navigating to login page");
        waitForPageLoad();
    }

    // --- Level 3 steps ---

    @Step("Wait for page to load")
    public void waitForPageLoad() {
        System.out.println("Waiting for page to load");
    }

    @Step("Enter credentials for {username}")
    public void enterCredentials(String username) {
        System.out.println("Entering credentials for " + username);
    }

    @Step("Submit the form")
    public void submitForm() {
        System.out.println("Submitting the form");
    }

    @Step("Verify welcome message for {username}")
    public void verifyWelcomeMessage(String username) {
        System.out.println("Verifying welcome message for " + username);
    }

    @Step("Perform action with {item} quantity {qty}")
    public void performAction(String item, int qty) {
        System.out.println("Performing action with " + item + " quantity " + qty);
    }

    @Step("Step that fails")
    public void failingStep() {
        System.out.println("This step will fail");
        throw new AssertionError("Step failure");
    }

    @Step("Step wrapping a failing step")
    public void outerFailingStep() {
        System.out.println("Outer step before failure");
        failingStep();
    }

    // --- Test methods ---

    @Test
    @QaseId(10)
    @QaseTitle("Login flow with 3+ nested steps")
    public void testNestedSteps() {
        openApplication();
        enterCredentials("admin");
        submitForm();
        verifyWelcomeMessage("admin");
    }

    @Test
    @QaseTitle("Steps with parameter interpolation")
    public void testStepParameterInterpolation() {
        enterCredentials("testuser");
        performAction("Widget", 5);
        performAction("Gadget", 10);
        verifyWelcomeMessage("testuser");
    }

    @Test
    @QaseTitle("Step failure propagation")
    public void testStepFailure() {
        openApplication();
        outerFailingStep();
    }
}
