package io.qase.testng.tests;

import io.qase.testng.CaseId;
import org.testng.annotations.Test;


public class Failed {
    @Test
    @CaseId(321)
    public void failedTest() {
        throw new AssertionError();
    }
}
