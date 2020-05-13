package io.qase.junit5.samples;


import io.qase.api.annotation.CaseId;
import org.junit.jupiter.api.Test;

public class Failed {
    @Test
    @CaseId(321)
    public void failedTest() {
        throw new AssertionError("Error message");
    }
}
