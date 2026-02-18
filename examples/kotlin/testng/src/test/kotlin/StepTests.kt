import io.qase.commons.annotation.QaseId
import io.qase.commons.annotation.QaseTitle
import io.qase.commons.annotation.Step
import org.testng.annotations.Test

class StepTests {

    // --- Level 1 steps ---

    @Step("Open the application")
    fun openApplication() {
        println("Opening the application")
        navigateToLoginPage()
    }

    // --- Level 2 steps ---

    @Step("Navigate to login page")
    fun navigateToLoginPage() {
        println("Navigating to login page")
        waitForPageLoad()
    }

    // --- Level 3 steps ---

    @Step("Wait for page to load")
    fun waitForPageLoad() {
        println("Waiting for page to load")
    }

    @Step("Enter credentials for {username}")
    fun enterCredentials(username: String) {
        println("Entering credentials for $username")
    }

    @Step("Submit the form")
    fun submitForm() {
        println("Submitting the form")
    }

    @Step("Verify welcome message for {username}")
    fun verifyWelcomeMessage(username: String) {
        println("Verifying welcome message for $username")
    }

    @Step("Perform action with {item} quantity {qty}")
    fun performAction(item: String, qty: Int) {
        println("Performing action with $item quantity $qty")
    }

    @Step("Step that fails")
    fun failingStep() {
        println("This step will fail")
        throw AssertionError("Step failure")
    }

    @Step("Step wrapping a failing step")
    fun outerFailingStep() {
        println("Outer step before failure")
        failingStep()
    }

    // --- Test methods ---

    @Test
    @QaseId(10)
    @QaseTitle("Login flow with 3+ nested steps")
    fun testNestedSteps() {
        openApplication()
        enterCredentials("admin")
        submitForm()
        verifyWelcomeMessage("admin")
    }

    @Test
    @QaseTitle("Steps with parameter interpolation")
    fun testStepParameterInterpolation() {
        enterCredentials("testuser")
        performAction("Widget", 5)
        performAction("Gadget", 10)
        verifyWelcomeMessage("testuser")
    }

    @Test(expectedExceptions = [AssertionError::class])
    @QaseTitle("Step failure propagation")
    fun testStepFailure() {
        openApplication()
        outerFailingStep()
    }
}
