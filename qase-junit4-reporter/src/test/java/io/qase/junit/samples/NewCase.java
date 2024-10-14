package io.qase.junit.samples;

import io.qase.commons.annotation.QaseTitle;
import io.qase.junit.samples.steps.Steps;
import org.junit.Test;


public class NewCase {
    @Test
    @QaseTitle("Case Title")
    public void passedTest() {
        Steps steps = new Steps();
        steps.successStep();
        steps.failureStep();
    }
}
