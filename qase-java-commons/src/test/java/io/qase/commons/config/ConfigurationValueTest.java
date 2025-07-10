package io.qase.commons.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ConfigurationValueTest {

    @Test
    public void testConfigurationValueCreation() {
        ConfigurationValue config = new ConfigurationValue("browser", "chrome");
        
        assertEquals("browser", config.getName());
        assertEquals("chrome", config.getValue());
    }

    @Test
    public void testConfigurationValueSetters() {
        ConfigurationValue config = new ConfigurationValue();
        config.setName("environment");
        config.setValue("staging");
        
        assertEquals("environment", config.getName());
        assertEquals("staging", config.getValue());
    }

    @Test
    public void testConfigurationValueToString() {
        ConfigurationValue config = new ConfigurationValue("browser", "firefox");
        String result = config.toString();
        
        assertTrue(result.contains("browser"));
        assertTrue(result.contains("firefox"));
    }
} 
