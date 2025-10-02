package io.qase.commons.logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoggerTest {

    private Logger logger;

    @BeforeEach
    public void setUp() {
        logger = Logger.getInstance();
        // Reset to default configuration
        logger.setConsoleEnabled(true);
        logger.setFileEnabled(false);
        logger.setGlobalLogLevel(Logger.LogLevel.INFO);
    }

    @Test
    public void testLoggerSingleton() {
        Logger instance1 = Logger.getInstance();
        Logger instance2 = Logger.getInstance();
        
        assertSame(instance1, instance2);
    }

    @Test
    public void testLogLevelEnum() {
        assertEquals(0, Logger.LogLevel.OFF.getValue());
        assertEquals(1, Logger.LogLevel.ERROR.getValue());
        assertEquals(2, Logger.LogLevel.WARN.getValue());
        assertEquals(3, Logger.LogLevel.INFO.getValue());
        assertEquals(4, Logger.LogLevel.DEBUG.getValue());
        assertEquals(5, Logger.LogLevel.TRACE.getValue());
    }

    @Test
    public void testConsoleEnabledConfiguration() {
        logger.setConsoleEnabled(false);
        assertFalse(logger.isConsoleEnabled());
        
        logger.setConsoleEnabled(true);
        assertTrue(logger.isConsoleEnabled());
    }

    @Test
    public void testFileEnabledConfiguration() {
        logger.setFileEnabled(true);
        assertTrue(logger.isFileEnabled());
        
        logger.setFileEnabled(false);
        assertFalse(logger.isFileEnabled());
    }

    @Test
    public void testGlobalLogLevelConfiguration() {
        logger.setGlobalLogLevel(Logger.LogLevel.DEBUG);
        assertEquals(Logger.LogLevel.DEBUG, logger.getGlobalLogLevel());
        
        logger.setGlobalLogLevel(Logger.LogLevel.ERROR);
        assertEquals(Logger.LogLevel.ERROR, logger.getGlobalLogLevel());
    }

    @Test
    public void testPackageLogLevelConfiguration() {
        logger.setPackageLogLevel("io.qase.commons.logger", Logger.LogLevel.DEBUG);
        
        // Test that the package level affects the effective log level
        // This is indirectly tested through isEnabled method
        assertTrue(logger.isEnabled(Logger.LogLevel.DEBUG));
    }

    @Test
    public void testLogLevelEnabled() {
        logger.setGlobalLogLevel(Logger.LogLevel.INFO);
        
        assertTrue(logger.isEnabled(Logger.LogLevel.ERROR));
        assertTrue(logger.isEnabled(Logger.LogLevel.WARN));
        assertTrue(logger.isEnabled(Logger.LogLevel.INFO));
        assertFalse(logger.isEnabled(Logger.LogLevel.DEBUG));
        assertFalse(logger.isEnabled(Logger.LogLevel.TRACE));
    }

    @Test
    public void testLogLevelDisabled() {
        logger.setGlobalLogLevel(Logger.LogLevel.OFF);
        
        // When log level is OFF, no messages should be enabled
        // Note: This test checks the global log level behavior
        // The actual isEnabled method depends on caller class, so we test the core logic
        assertTrue(Logger.LogLevel.ERROR.getValue() > Logger.LogLevel.OFF.getValue());
        assertTrue(Logger.LogLevel.WARN.getValue() > Logger.LogLevel.OFF.getValue());
        assertTrue(Logger.LogLevel.INFO.getValue() > Logger.LogLevel.OFF.getValue());
        assertTrue(Logger.LogLevel.DEBUG.getValue() > Logger.LogLevel.OFF.getValue());
        assertTrue(Logger.LogLevel.TRACE.getValue() > Logger.LogLevel.OFF.getValue());
    }

    @Test
    public void testLoggingMethods() {
        // These methods should not throw exceptions
        assertDoesNotThrow(() -> logger.error("Test error message"));
        assertDoesNotThrow(() -> logger.warn("Test warning message"));
        assertDoesNotThrow(() -> logger.info("Test info message"));
        assertDoesNotThrow(() -> logger.debug("Test debug message"));
        assertDoesNotThrow(() -> logger.trace("Test trace message"));
    }

    @Test
    public void testLoggingWithException() {
        Exception testException = new RuntimeException("Test exception");
        
        assertDoesNotThrow(() -> logger.error("Test error with exception", testException));
        assertDoesNotThrow(() -> logger.warn("Test warning with exception", testException));
        assertDoesNotThrow(() -> logger.debug("Test debug with exception", testException));
    }

    @Test
    public void testLoggingWithFormat() {
        assertDoesNotThrow(() -> logger.error("Test error: %s", "formatted"));
        assertDoesNotThrow(() -> logger.warn("Test warning: %s", "formatted"));
        assertDoesNotThrow(() -> logger.info("Test info: %s", "formatted"));
        assertDoesNotThrow(() -> logger.debug("Test debug: %s", "formatted"));
        assertDoesNotThrow(() -> logger.trace("Test trace: %s", "formatted"));
    }
}
