package io.qase.junit.samples;


import io.qase.api.annotation.CaseId;
import org.junit.Test;

public class Failed {
    @Test
    @CaseId(321)
    public void failedTest() {
        throw new AssertionError("Error message");
    }
}
