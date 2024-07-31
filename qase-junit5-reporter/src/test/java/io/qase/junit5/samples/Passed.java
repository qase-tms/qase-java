package io.qase.junit5.samples;


import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.Test;

public class Passed {
    @Test
    @QaseId(123)
    public void passedTest() {

    }
}
