package io.qase.junit.samples;

import io.qase.api.annotation.CaseId;
import io.qase.junit.samples.steps.Steps;
import org.junit.Test;

import java.util.concurrent.TimeUnit;


public class Multiple {
    @Test
    @CaseId(123)
    public void failedWithStepsTest() {
        Steps steps = new Steps();
        steps.successStep();
        steps.failureStep();
    }

    @Test
    @CaseId(456)
    public void passedWithStepsTest() {
        Steps steps = new Steps();
        steps.successStep();
    }

    @Test
    @CaseId(321)
    public void failedTest() throws InterruptedException {
        Thread.sleep(TimeUnit.SECONDS.toMillis(2));
        throw new AssertionError("Error message");
    }
}
