package io.qase.testng.samples;

import io.qase.api.annotation.CaseId;
import org.testng.annotations.Test;


public class Passed {
    @Test
    @CaseId(123)
    public void passedTest() {

    }
}
