package io.qase.commons.config;

import io.qase.commons.logger.Logger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for config validation hardening (CONF-01 through CONF-04).
 *
 * CONF-01: Non-numeric integer env var values use default instead of crashing.
 * CONF-02: Empty/blank string token or project code is rejected same as null.
 * CONF-03: Batch size of 0 or negative is rejected, default 200 used.
 * CONF-04: All invalid config warnings include the parameter name and default value.
 */
class ConfigFactoryConfigValidationTest {

    // --- CONF-01: Non-numeric integer parsing ---

    /**
     * Non-numeric value for QASE_TESTOPS_RUN_ID must not throw -- config uses default 0.
     */
    @Test
    void nonNumericRunIdUsesDefault() {
        System.setProperty("QASE_TESTOPS_RUN_ID", "abc");
        try {
            QaseConfig config = ConfigFactory.loadConfig();
            assertEquals(0, config.testops.run.id);
        } finally {
            System.clearProperty("QASE_TESTOPS_RUN_ID");
        }
    }

    /**
     * Non-numeric value for QASE_TESTOPS_PLAN_ID must not throw -- config uses default 0.
     */
    @Test
    void nonNumericPlanIdUsesDefault() {
        System.setProperty("QASE_TESTOPS_PLAN_ID", "xyz");
        try {
            QaseConfig config = ConfigFactory.loadConfig();
            assertEquals(0, config.testops.plan.id);
        } finally {
            System.clearProperty("QASE_TESTOPS_PLAN_ID");
        }
    }

    /**
     * Non-numeric value for QASE_TESTOPS_BATCH_SIZE must not throw -- batch size uses default 200.
     */
    @Test
    void nonNumericBatchSizeUsesDefault() {
        System.setProperty("QASE_TESTOPS_BATCH_SIZE", "not-a-number");
        try {
            QaseConfig config = ConfigFactory.loadConfig();
            assertEquals(200, config.testops.batch.getSize());
        } finally {
            System.clearProperty("QASE_TESTOPS_BATCH_SIZE");
        }
    }

    /**
     * Empty string for QASE_TESTOPS_RUN_ID must not throw -- config uses default 0.
     */
    @Test
    void emptyStringRunIdUsesDefault() {
        System.setProperty("QASE_TESTOPS_RUN_ID", "");
        try {
            QaseConfig config = ConfigFactory.loadConfig();
            assertEquals(0, config.testops.run.id);
        } finally {
            System.clearProperty("QASE_TESTOPS_RUN_ID");
        }
    }

    // --- CONF-02: Empty/blank string token and project treated as null ---

    /**
     * Empty string token in testops mode must disable reporter (mode = OFF).
     */
    @Test
    void emptyTokenInTestopsModeDisablesReporter() {
        System.setProperty("QASE_MODE", "testops");
        System.setProperty("QASE_TESTOPS_API_TOKEN", "");
        System.setProperty("QASE_TESTOPS_PROJECT", "PROJ");
        try {
            QaseConfig config = ConfigFactory.loadConfig();
            assertEquals(Mode.OFF, config.mode);
        } finally {
            System.clearProperty("QASE_MODE");
            System.clearProperty("QASE_TESTOPS_API_TOKEN");
            System.clearProperty("QASE_TESTOPS_PROJECT");
        }
    }

    /**
     * Whitespace-only token in testops mode must disable reporter (mode = OFF).
     */
    @Test
    void whitespaceTokenInTestopsModeDisablesReporter() {
        System.setProperty("QASE_MODE", "testops");
        System.setProperty("QASE_TESTOPS_API_TOKEN", "   ");
        System.setProperty("QASE_TESTOPS_PROJECT", "PROJ");
        try {
            QaseConfig config = ConfigFactory.loadConfig();
            assertEquals(Mode.OFF, config.mode);
        } finally {
            System.clearProperty("QASE_MODE");
            System.clearProperty("QASE_TESTOPS_API_TOKEN");
            System.clearProperty("QASE_TESTOPS_PROJECT");
        }
    }

    /**
     * Empty string project in testops mode must disable reporter (mode = OFF).
     */
    @Test
    void emptyProjectInTestopsModeDisablesReporter() {
        System.setProperty("QASE_MODE", "testops");
        System.setProperty("QASE_TESTOPS_API_TOKEN", "valid-token");
        System.setProperty("QASE_TESTOPS_PROJECT", "");
        try {
            QaseConfig config = ConfigFactory.loadConfig();
            assertEquals(Mode.OFF, config.mode);
        } finally {
            System.clearProperty("QASE_MODE");
            System.clearProperty("QASE_TESTOPS_API_TOKEN");
            System.clearProperty("QASE_TESTOPS_PROJECT");
        }
    }

    // --- CONF-03: Batch size lower bound validation ---

    /**
     * BatchConfig.setSize(0) must be rejected -- getSize() returns default 200.
     */
    @Test
    void zeroBatchSizeUsesDefault() {
        BatchConfig batch = new BatchConfig();
        batch.setSize(0);
        assertEquals(200, batch.getSize());
    }

    /**
     * BatchConfig.setSize(-5) must be rejected -- getSize() returns default 200.
     */
    @Test
    void negativeBatchSizeUsesDefault() {
        BatchConfig batch = new BatchConfig();
        batch.setSize(-5);
        assertEquals(200, batch.getSize());
    }

    /**
     * BatchConfig.setSize(100) is valid -- getSize() returns 100.
     */
    @Test
    void validBatchSizeAccepted() {
        BatchConfig batch = new BatchConfig();
        batch.setSize(100);
        assertEquals(100, batch.getSize());
    }

    /**
     * BatchConfig.setSize(3000) exceeds upper bound -- getSize() still returns 200 (existing behavior preserved).
     */
    @Test
    void upperBoundBatchSizeStillWorks() {
        BatchConfig batch = new BatchConfig();
        batch.setSize(3000);
        assertEquals(200, batch.getSize());
    }

    // --- TEST-03: WARN log count verification for bad batch size config ---

    /**
     * TEST-03: Non-numeric batch size via system property must log a WARN and use default 200.
     * Verifies Logger.warn() is actually called (statistics WARN count increases).
     */
    @Test
    void nonNumericBatchSizeViaPropertyLogsWarnAndUsesDefault() {
        Logger logger = Logger.getInstance();
        long warnBefore = logger.getStatistics().get("WARN");

        System.setProperty("QASE_TESTOPS_BATCH_SIZE", "not-a-number");
        try {
            QaseConfig config = ConfigFactory.loadConfig();
            assertEquals(200, config.testops.batch.getSize());
            assertTrue(logger.getStatistics().get("WARN") > warnBefore,
                    "A WARN must be logged for non-numeric batch size");
        } finally {
            System.clearProperty("QASE_TESTOPS_BATCH_SIZE");
        }
    }

    /**
     * TEST-03: Negative batch size via system property must log a WARN and use default 200.
     * Verifies Logger.warn() is actually called (statistics WARN count increases).
     */
    @Test
    void negativeBatchSizeViaPropertyLogsWarnAndUsesDefault() {
        Logger logger = Logger.getInstance();
        long warnBefore = logger.getStatistics().get("WARN");

        System.setProperty("QASE_TESTOPS_BATCH_SIZE", "-10");
        try {
            QaseConfig config = ConfigFactory.loadConfig();
            assertEquals(200, config.testops.batch.getSize());
            assertTrue(logger.getStatistics().get("WARN") > warnBefore,
                    "A WARN must be logged for negative batch size");
        } finally {
            System.clearProperty("QASE_TESTOPS_BATCH_SIZE");
        }
    }
}
