package io.qase.commons.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConfigFactoryStatusMappingTest {

    private String originalEnvValue;

    @BeforeEach
    void setUp() {
        // Store original environment variable value
        originalEnvValue = System.getenv("QASE_STATUS_MAPPING");
    }

    @AfterEach
    void tearDown() {
        // Restore original environment variable value
        if (originalEnvValue != null) {
            System.setProperty("QASE_STATUS_MAPPING", originalEnvValue);
        } else {
            System.clearProperty("QASE_STATUS_MAPPING");
        }
    }

    @Test
    void testLoadConfig_WithStatusMappingEnvVar() {
        // Set environment variable
        System.setProperty("QASE_STATUS_MAPPING", "invalid=failed,skipped=passed");
        
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.statusMapping);
        assertEquals(2, config.statusMapping.size());
        assertEquals("failed", config.statusMapping.get("invalid"));
        assertEquals("passed", config.statusMapping.get("skipped"));
    }

    @Test
    void testLoadConfig_WithEmptyStatusMappingEnvVar() {
        // Set empty environment variable
        System.setProperty("QASE_STATUS_MAPPING", "");
        
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.statusMapping);
        assertTrue(config.statusMapping.isEmpty());
    }

    @Test
    void testLoadConfig_WithInvalidStatusMappingEnvVar() {
        // Set invalid environment variable
        System.setProperty("QASE_STATUS_MAPPING", "invalid=failed,unknown=passed");
        
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.statusMapping);
        // Only valid mappings should be included
        assertEquals(1, config.statusMapping.size());
        assertEquals("failed", config.statusMapping.get("invalid"));
    }

    @Test
    void testLoadConfig_WithoutStatusMappingEnvVar() {
        // Clear environment variable
        System.clearProperty("QASE_STATUS_MAPPING");
        
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.statusMapping);
        assertTrue(config.statusMapping.isEmpty());
    }

    @Test
    void testQaseConfig_SetStatusMappingFromString() {
        QaseConfig config = new QaseConfig();
        
        config.setStatusMapping("invalid=failed,skipped=passed");
        
        assertNotNull(config.statusMapping);
        assertEquals(2, config.statusMapping.size());
        assertEquals("failed", config.statusMapping.get("invalid"));
        assertEquals("passed", config.statusMapping.get("skipped"));
    }

    @Test
    void testQaseConfig_SetStatusMappingFromMap() {
        QaseConfig config = new QaseConfig();
        
        Map<String, String> mapping = new HashMap<>();
        mapping.put("invalid", "failed");
        mapping.put("skipped", "passed");
        config.setStatusMapping(mapping);
        
        assertNotNull(config.statusMapping);
        assertEquals(2, config.statusMapping.size());
        assertEquals("failed", config.statusMapping.get("invalid"));
        assertEquals("passed", config.statusMapping.get("skipped"));
    }

    @Test
    void testQaseConfig_SetInvalidStatusMappingFromMap() {
        QaseConfig config = new QaseConfig();
        
        Map<String, String> invalidMapping = new HashMap<>();
        invalidMapping.put("invalid", "failed");
        invalidMapping.put("unknown", "passed");
        config.setStatusMapping(invalidMapping);
        
        // Invalid mapping should be ignored
        assertNotNull(config.statusMapping);
        assertTrue(config.statusMapping.isEmpty());
    }

    @Test
    void testQaseConfig_GetStatusMapping() {
        QaseConfig config = new QaseConfig();
        
        Map<String, String> mapping = new HashMap<>();
        mapping.put("invalid", "failed");
        config.setStatusMapping(mapping);
        
        Map<String, String> result = config.getStatusMapping();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("failed", result.get("invalid"));
    }
}
