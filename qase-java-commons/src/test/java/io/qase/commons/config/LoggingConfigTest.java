package io.qase.commons.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LoggingConfigTest {

    @BeforeEach
    public void setUp() {
        // Clear any system properties that might interfere
        System.clearProperty("QASE_LOGGING_CONSOLE");
        System.clearProperty("QASE_LOGGING_FILE");
    }

    @Test
    public void testDefaultLoggingConfig() {
        LoggingConfig config = new LoggingConfig();
        
        assertTrue(config.isConsole());
        assertFalse(config.isFile());
    }

    @Test
    public void testLoggingConfigWithParameters() {
        LoggingConfig config = new LoggingConfig(false, true);
        
        assertFalse(config.isConsole());
        assertTrue(config.isFile());
    }

    @Test
    public void testLoggingConfigSetters() {
        LoggingConfig config = new LoggingConfig();
        
        config.setConsole(false);
        config.setFile(true);
        
        assertFalse(config.isConsole());
        assertTrue(config.isFile());
    }

    @Test
    public void testLoggingConfigFromSystemProperties() {
        System.setProperty("QASE_LOGGING_CONSOLE", "false");
        System.setProperty("QASE_LOGGING_FILE", "true");
        
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.logging);
        assertFalse(config.logging.isConsole());
        assertTrue(config.logging.isFile());
    }

    @Test
    public void testLoggingConfigFromEnvironment() {
        // Note: Environment variables are harder to test in unit tests
        // This test verifies the configuration structure
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.logging);
        assertTrue(config.logging.isConsole()); // Default value
        assertFalse(config.logging.isFile()); // Default value
    }
}
