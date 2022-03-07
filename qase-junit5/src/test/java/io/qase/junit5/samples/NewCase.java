package io.qase.junit5.samples;

import io.qase.api.annotation.CaseTitle;
import io.qase.junit5.samples.Steps.Steps;
import org.junit.jupiter.api.Test;


public class NewCase {
    @Test
    @CaseTitle("Case Title")
    public void passedTest() {
        Steps steps = new Steps();
        steps.successStep();
        steps.failureStep();
    }
}
