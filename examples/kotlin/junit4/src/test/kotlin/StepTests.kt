import io.qase.commons.annotation.Step
import org.junit.Test

class StepTests {

    @Step("Step 1")
    fun step1() {
        step1_1()
        println("Step 1")
    }

    @Step("Step 1.1")
    fun step1_1() {
        println("Step 1.1")
    }

    @Step("Step 2")
    fun step2() {
        println("Step 2")
    }

    @Step("Step 3")
    fun step3() {
        println("Step 3")
    }

    @Step("Step 1 failed")
    fun step1_failed() {
        println("Step 1 failed")
        step1_1_failed()
    }

    @Step("Step 1.1 failed")
    fun step1_1_failed() {
        println("Step 1.1 failed")
        throw RuntimeException("Step 1.1 failed")
    }

    @Step("Step 2 failed")
    fun step2_failed() {
        println("Step 2 failed")
        throw RuntimeException("Step 2 failed")
    }


    @Test
    fun testWithSteps_success() {
        println("Test with steps success")
        step1()
        step2()
        step3()
    }

    @Test
    fun testWithSteps_failed() {
        println("Test with steps failed")
        step1()
        step2()
        step3()
        throw RuntimeException("Test with steps failed")
    }

    @Test
    fun testWithSteps_failed2() {
        println("Test with steps failed")
        step1()
        step2_failed()
        step3()
    }

    @Test
    fun testWithSteps_failed3() {
        println("Test with steps failed")
        step1_failed()
        step2()
        step3()
    }
}

