package io.qase.junit.samples;

import io.qase.api.annotation.Qase;
import io.qase.junit.samples.steps.Steps;
import org.junit.Test;


public class WithSteps {
    @Test
    @Qase(testId = 123)
    public void passedTest() {
        Steps steps = new Steps();
        steps.successStep();
        steps.failureStep();
    }
}
