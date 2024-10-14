package io.qase.junit5.samples;

import io.qase.commons.annotation.QaseId;
import io.qase.junit5.samples.Steps.Steps;
import org.junit.jupiter.api.Test;


public class WithSteps {
    @Test
    @QaseId(123)
    public void withStepsFailedTest() {
        Steps steps = new Steps();
        steps.successStep();
        steps.failureStep();
    }
}
