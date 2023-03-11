package io.qase.testng.samples;

import io.qase.api.annotation.Qase;
import org.testng.annotations.Test;


public class Passed {
    @Test
    @Qase(testId = 123)
    public void passedTest() {

    }
}
