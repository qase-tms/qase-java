package io.qase.testng.samples;

import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;


public class Failed {
    @Test
    @CaseId(321)
    public void failedTest() {
        throw new AssertionError("Error message");
    }
}
