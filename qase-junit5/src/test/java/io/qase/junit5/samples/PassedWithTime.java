package io.qase.junit5.samples;


import io.qase.api.annotation.CaseId;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;


public class PassedWithTime {
    @Test
    @CaseId(123)
    public void passedTest() throws InterruptedException {
        Thread.sleep(TimeUnit.SECONDS.toMillis(3));
    }
}
