package io.qase.commons.config;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationsConfigTest {

    @Test
    public void testConfigurationsConfigCreation() {
        ConfigurationsConfig config = new ConfigurationsConfig();
        
        assertNotNull(config.getValues());
        assertFalse(config.isCreateIfNotExists());
    }

    @Test
    public void testConfigurationsConfigSetters() {
        ConfigurationsConfig config = new ConfigurationsConfig();
        
        List<ConfigurationValue> values = new ArrayList<>();
        values.add(new ConfigurationValue("browser", "chrome"));
        values.add(new ConfigurationValue("environment", "staging"));
        
        config.setValues(values);
        config.setCreateIfNotExists(true);
        
        assertEquals(2, config.getValues().size());
        assertTrue(config.isCreateIfNotExists());
    }

    @Test
    public void testConfigurationsConfigToString() {
        ConfigurationsConfig config = new ConfigurationsConfig();
        config.setCreateIfNotExists(true);
        
        String result = config.toString();
        
        assertTrue(result.contains("createIfNotExists=true"));
        assertTrue(result.contains("values="));
    }
} 
