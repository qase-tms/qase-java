package io.qase.junit.samples;


import io.qase.commons.annotation.QaseId;
import org.junit.Test;

public class Failed {
    @Test
    @QaseId(321)
    public void failedTest() {
        throw new AssertionError("Error message");
    }
}
