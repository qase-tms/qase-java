package io.qase.junit5.samples;


import io.qase.api.annotation.Qase;
import org.junit.jupiter.api.Test;

public class Passed {
    @Test
    @Qase(testId = 123)
    public void passedTest() {

    }
}
