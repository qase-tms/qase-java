package io.qase.junit5.samples;


import io.qase.api.annotation.CaseId;
import org.junit.jupiter.api.Test;

public class Passed {
    @Test
    @CaseId(123)
    public void passedTest() {

    }
}
