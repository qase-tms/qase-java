package io.qase.testng.samples;

import io.qase.api.annotation.QaseId;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class FailedWithTime {
    @Test
    @QaseId(321)
    public void failedTest() throws InterruptedException {
        Thread.sleep(TimeUnit.SECONDS.toMillis(2));
        throw new AssertionError("Error message");
    }
}
