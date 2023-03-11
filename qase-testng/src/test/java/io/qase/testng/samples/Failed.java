package io.qase.testng.samples;

import io.qase.api.annotation.Qase;
import org.testng.annotations.Test;


public class Failed {
    @Test
    @Qase(testId = 321)
    public void failedTest() {
        throw new AssertionError("Error message");
    }
}
