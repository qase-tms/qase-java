package io.qase.junit5.samples;

import io.qase.commons.annotation.QaseId;
import io.qase.commons.annotation.QaseIgnore;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FailedBeforeAll {
    @BeforeAll
    static void setUp() {
        throw new RuntimeException("Setup failed: database unavailable");
    }

    @Test
    @QaseId(100)
    public void firstTest() {
    }

    @Test
    @QaseId(200)
    public void secondTest() {
    }

    @Test
    @QaseIgnore
    public void ignoredTest() {
    }
}
