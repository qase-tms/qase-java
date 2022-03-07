package io.qase.junit.samples;

import io.qase.api.annotation.CaseTitle;
import io.qase.junit.samples.steps.Steps;
import org.junit.Test;


public class NewCase {
    @Test
    @CaseTitle("Case Title")
    public void passedTest() {
        Steps steps = new Steps();
        steps.successStep();
        steps.failureStep();
    }
}
