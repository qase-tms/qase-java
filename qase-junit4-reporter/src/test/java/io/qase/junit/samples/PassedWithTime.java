package io.qase.junit.samples;


import io.qase.commons.annotation.QaseId;
import org.junit.Test;

import java.util.concurrent.TimeUnit;


public class PassedWithTime {
    @Test
    @QaseId(123)
    public void passedTest() throws InterruptedException {
        Thread.sleep(TimeUnit.SECONDS.toMillis(3));
    }
}
