package io.qase.junit5.samples;

import io.qase.api.annotation.CaseId;
import io.qase.junit5.samples.Steps.Steps;
import org.junit.jupiter.api.Test;

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
