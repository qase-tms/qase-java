package io.qase.junit5.samples;

import io.qase.api.annotation.CaseId;
import io.qase.junit5.samples.Steps.Steps;
import org.junit.jupiter.api.Test;


public class WithSteps {
    @Test
    @CaseId(123)
    public void passedTest() {
        Steps steps = new Steps();
        steps.successStep();
        steps.failureStep();
    }
}
