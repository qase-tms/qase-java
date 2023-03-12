package io.qase.junit5.samples;


import io.qase.api.annotation.Qase;
import org.junit.jupiter.api.Test;

public class Failed {
    @Test
    @Qase(testId = 321)
    public void failedTest() {
        throw new AssertionError("Error message");
    }
}
