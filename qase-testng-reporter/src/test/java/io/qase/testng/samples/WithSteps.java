package io.qase.testng.samples;

import io.qase.commons.annotation.QaseId;
import io.qase.testng.samples.steps.Steps;
import org.testng.annotations.Test;


public class WithSteps {
    @Test
    @QaseId(123)
    public void passedTest() {
        Steps steps = new Steps();
        steps.successStep();
        steps.failureStep();
    }
}
