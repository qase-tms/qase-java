package io.qase.testng.samples;

import io.qase.api.annotation.QaseTitle;
import io.qase.testng.samples.steps.Steps;
import org.testng.annotations.Test;


public class NewCase {
    @Test
    @QaseTitle("Case Title")
    public void passedTest() {
        Steps steps = new Steps();
        steps.successStep();
        steps.failureStep();
    }
}
