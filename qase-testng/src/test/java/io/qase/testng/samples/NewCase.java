package io.qase.testng.samples;

import io.qase.api.annotation.CaseTitle;
import io.qase.testng.samples.steps.Steps;
import org.testng.annotations.Test;


public class NewCase {
    @Test
    @CaseTitle("Case Title")
    public void passedTest() {
        Steps steps = new Steps();
        steps.successStep();
        steps.failureStep();
    }
}
