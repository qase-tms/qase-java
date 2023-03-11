package io.qase.testng.samples;

import io.qase.api.annotation.Qase;
import io.qase.testng.samples.steps.Steps;
import org.testng.annotations.Test;


public class WithStepsSuccess {
    @Test
    @Qase(testId = 41332)
    public void passedTest() {
        Steps steps = new Steps();
        steps.successStep();
    }
}
