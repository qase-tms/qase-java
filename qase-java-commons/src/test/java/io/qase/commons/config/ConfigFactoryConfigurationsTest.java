package io.qase.commons.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class ConfigFactoryConfigurationsTest {

    @BeforeEach
    public void setUp() {
        // Clear any system properties that might interfere
        System.clearProperty("QASE_TESTOPS_RUN_CONFIGURATIONS");
    }

    @Test
    public void testParseConfigurationValue() throws Exception {
        // Use reflection to access private method
        java.lang.reflect.Method parseMethod = ConfigFactory.class.getDeclaredMethod("parseConfigurationValue", String.class);
        parseMethod.setAccessible(true);
        
        // Test valid configuration
        ConfigurationValue result = (ConfigurationValue) parseMethod.invoke(null, "browser=chrome");
        assertNotNull(result);
        assertEquals("browser", result.getName());
        assertEquals("chrome", result.getValue());
        
        // Test with spaces
        result = (ConfigurationValue) parseMethod.invoke(null, " environment = staging ");
        assertNotNull(result);
        assertEquals("environment", result.getName());
        assertEquals("staging", result.getValue());
        
        // Test invalid configuration
        result = (ConfigurationValue) parseMethod.invoke(null, "invalid");
        assertNull(result);
        
        // Test null
        result = (ConfigurationValue) parseMethod.invoke(null, (String) null);
        assertNull(result);
        
        // Test empty
        result = (ConfigurationValue) parseMethod.invoke(null, "");
        assertNull(result);
    }

    @Test
    public void testLoadConfigurationsFromSystemProperty() {
        System.setProperty("QASE_TESTOPS_RUN_CONFIGURATIONS", "browser=chrome,environment=staging");
        
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.testops.configurations);
        assertEquals(2, config.testops.configurations.getValues().size());
        
        ConfigurationValue browserConfig = config.testops.configurations.getValues().get(0);
        assertEquals("browser", browserConfig.getName());
        assertEquals("chrome", browserConfig.getValue());
        
        ConfigurationValue envConfig = config.testops.configurations.getValues().get(1);
        assertEquals("environment", envConfig.getName());
        assertEquals("staging", envConfig.getValue());
    }

    @Test
    public void testLoadConfigurationsFromEnvironment() {
        // Set environment variable
        System.setProperty("QASE_TESTOPS_RUN_CONFIGURATIONS", "browser=firefox,os=linux");
        
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.testops.configurations);
        assertEquals(2, config.testops.configurations.getValues().size());
        
        ConfigurationValue browserConfig = config.testops.configurations.getValues().get(0);
        assertEquals("browser", browserConfig.getName());
        assertEquals("firefox", browserConfig.getValue());
        
        ConfigurationValue osConfig = config.testops.configurations.getValues().get(1);
        assertEquals("os", osConfig.getName());
        assertEquals("linux", osConfig.getValue());
    }

    @Test
    public void testLoadCreateIfNotExistsFromEnvironment() {
        // Set environment variable for createIfNotExists
        System.setProperty("QASE_TESTOPS_CONFIGURATIONS_CREATE_IF_NOT_EXISTS", "true");
        
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.testops.configurations);
        assertTrue(config.testops.configurations.isCreateIfNotExists());
    }

    @Test
    public void testLoadCreateIfNotExistsFromSystemProperty() {
        // Set system property for createIfNotExists
        System.setProperty("QASE_TESTOPS_CONFIGURATIONS_CREATE_IF_NOT_EXISTS", "false");
        
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.testops.configurations);
        assertFalse(config.testops.configurations.isCreateIfNotExists());
    }

    @Test
    public void testCombinedConfigurationsAndCreateIfNotExists() {
        // Set both configurations and createIfNotExists
        System.setProperty("QASE_TESTOPS_RUN_CONFIGURATIONS", "browser=chrome,os=windows");
        System.setProperty("QASE_TESTOPS_CONFIGURATIONS_CREATE_IF_NOT_EXISTS", "true");
        
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.testops.configurations);
        assertEquals(2, config.testops.configurations.getValues().size());
        assertTrue(config.testops.configurations.isCreateIfNotExists());
        
        ConfigurationValue browserConfig = config.testops.configurations.getValues().get(0);
        assertEquals("browser", browserConfig.getName());
        assertEquals("chrome", browserConfig.getValue());
        
        ConfigurationValue osConfig = config.testops.configurations.getValues().get(1);
        assertEquals("os", osConfig.getName());
        assertEquals("windows", osConfig.getValue());
    }
} 
