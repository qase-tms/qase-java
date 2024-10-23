package org.example;

import io.qase.commons.annotation.Step;
import org.junit.Test;

public class StepTests {

    @Step("Step 1")
    public void step1() {
        step1_1();
        try {
            Thread.sleep(2 * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Step 1");
    }

    @Step("Step 1.1")
    public void step1_1() {
        System.out.println("Step 1.1");
    }

    @Step("Step 2")
    public void step2() {
        System.out.println("Step 2");
    }

    @Step("Step 3")
    public void step3() {
        System.out.println("Step 3");
    }

    @Step("Step 1 failed")
    public void step1_failed() {
        System.out.println("Step 1 failed");
        step1_1_failed();
    }

    @Step("Step 1.1 failed")
    public void step1_1_failed() {
        System.out.println("Step 1.1 failed");
        throw new RuntimeException("Step 1.1 failed");
    }

    @Step("Step 2 failed")
    public void step2_failed() {
        System.out.println("Step 2 failed");
        throw new RuntimeException("Step 2 failed");
    }


    @Test
    public void testWithSteps_success() {
        System.out.println("Test with steps success");
        step1();
        step2();
        step3();
    }

    @Test
    public void testWithSteps_failed() {
        System.out.println("Test with steps failed");
        step1();
        step2();
        step3();
        throw new RuntimeException("Test with steps failed");
    }

    @Test
    public void testWithSteps_failed2() {
        System.out.println("Test with steps failed");
        step1();
        step2_failed();
        step3();
    }

    @Test
    public void testWithSteps_failed3() {
        System.out.println("Test with steps failed");
        step1_failed();
        step2();
        step3();
    }
}
