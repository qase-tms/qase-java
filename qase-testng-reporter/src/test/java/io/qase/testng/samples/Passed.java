package io.qase.testng.samples;

import io.qase.api.annotation.QaseId;
import org.testng.annotations.Test;


public class Passed {
    @Test
    @QaseId(123)
    public void passedTest() {

    }
}
