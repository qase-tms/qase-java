package io.qase.testng.samples.steps;

import io.qase.api.annotation.Step;

public class Steps {
    @Step("success step")
    public void successStep() {

    }

    @Step("failure step")
    public void failureStep() {
        throw new AssertionError();
    }
}
