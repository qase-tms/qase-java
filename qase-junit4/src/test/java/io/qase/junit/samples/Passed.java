package io.qase.junit.samples;


import io.qase.api.annotation.Qase;
import org.junit.Test;

public class Passed {
    @Test
    @Qase(testId = 123)
    public void passedTest() {

    }
}
