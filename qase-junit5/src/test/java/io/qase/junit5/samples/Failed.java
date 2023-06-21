package io.qase.junit5.samples;


import io.qase.api.annotation.QaseId;
import org.junit.jupiter.api.Test;

public class Failed {
    @Test
    @QaseId(321)
    public void failedTest() {
        throw new AssertionError("Error message");
    }
}
