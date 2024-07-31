package io.qase.junit.samples;

import io.qase.api.annotation.QaseId;
import io.qase.junit.samples.steps.Steps;
import org.junit.Test;


public class WithSteps {
    @Test
    @QaseId(123)
    public void passedTest() {
        Steps steps = new Steps();
        steps.successStep();
        steps.failureStep();
    }
}
