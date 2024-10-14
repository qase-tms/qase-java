package io.qase.testng.samples;

import io.qase.commons.annotation.QaseId;
import org.testng.annotations.Test;


public class Failed {
    @Test
    @QaseId(321)
    public void failedTest() {
        throw new AssertionError("Error message");
    }
}
