package io.qase.junit.samples;

import io.qase.commons.annotation.QaseId;
import io.qase.junit.samples.steps.Steps;
import org.junit.Test;

import java.util.concurrent.TimeUnit;


public class Multiple {
    @Test
    @QaseId(123)
    public void failedWithStepsTest() {
        Steps steps = new Steps();
        steps.successStep();
        steps.failureStep();
    }

    @Test
    @QaseId(456)
    public void passedWithStepsTest() {
        Steps steps = new Steps();
        steps.successStep();
    }

    @Test
    @QaseId(321)
    public void failedTest() throws InterruptedException {
        Thread.sleep(TimeUnit.SECONDS.toMillis(2));
        throw new AssertionError("Error message");
    }
}
