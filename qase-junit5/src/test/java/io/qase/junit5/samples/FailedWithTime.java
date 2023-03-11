package io.qase.junit5.samples;

import io.qase.api.annotation.Qase;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class FailedWithTime {
    @Test
    @Qase(testId = 321)
    public void failedTest() throws InterruptedException {
        Thread.sleep(TimeUnit.SECONDS.toMillis(2));
        throw new AssertionError("Error message");
    }
}
