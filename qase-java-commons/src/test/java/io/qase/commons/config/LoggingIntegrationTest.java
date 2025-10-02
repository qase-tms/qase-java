package io.qase.commons.config;

import io.qase.commons.logger.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;

public class LoggingIntegrationTest {

    @BeforeEach
    public void setUp() {
        // Clear any system properties that might interfere
        System.clearProperty("QASE_LOGGING_CONSOLE");
        System.clearProperty("QASE_LOGGING_FILE");
        System.clearProperty("QASE_DEBUG");
    }

    @Test
    public void testLoggingConfigurationFromSystemProperties() {
        System.setProperty("QASE_LOGGING_CONSOLE", "false");
        System.setProperty("QASE_LOGGING_FILE", "true");
        
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.logging);
        assertFalse(config.logging.isConsole());
        assertTrue(config.logging.isFile());
    }

    @Test
    public void testLoggingConfigurationDefaultValues() {
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.logging);
        assertTrue(config.logging.isConsole()); // Default value
        assertFalse(config.logging.isFile()); // Default value
    }

    @Test
    public void testLoggingConfigurationWithDebug() {
        System.setProperty("QASE_DEBUG", "true");
        
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.logging);
        assertTrue(config.logging.isConsole()); // Default value
        assertFalse(config.logging.isFile()); // Default value, but will be overridden in CoreReporterFactory
    }

    @Test
    public void testLoggerConfigurationApplied() {
        Logger logger = Logger.getInstance();
        
        // Test default configuration
        assertTrue(logger.isConsoleEnabled());
        assertFalse(logger.isFileEnabled());
        
        // Test configuration changes
        logger.setConsoleEnabled(false);
        logger.setFileEnabled(true);
        
        assertFalse(logger.isConsoleEnabled());
        assertTrue(logger.isFileEnabled());
    }

    @Test
    public void testLoggingMethodsWork() {
        Logger logger = Logger.getInstance();
        
        // These should not throw exceptions
        assertDoesNotThrow(() -> logger.info("Test info message"));
        assertDoesNotThrow(() -> logger.debug("Test debug message"));
        assertDoesNotThrow(() -> logger.warn("Test warning message"));
        assertDoesNotThrow(() -> logger.error("Test error message"));
    }
}
