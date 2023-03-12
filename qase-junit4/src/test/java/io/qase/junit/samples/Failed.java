package io.qase.junit.samples;


import io.qase.api.annotation.Qase;
import org.junit.Test;

public class Failed {
    @Test
    @Qase(testId = 321)
    public void failedTest() {
        throw new AssertionError("Error message");
    }
}
