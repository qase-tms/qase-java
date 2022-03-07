package io.qase.testng.samples;

import io.qase.api.annotation.CaseId;
import io.qase.testng.samples.steps.Steps;
import org.testng.annotations.Test;


public class WithSteps {
    @Test
    @CaseId(123)
    public void passedTest() {
        Steps steps = new Steps();
        steps.successStep();
        steps.failureStep();
    }
}
