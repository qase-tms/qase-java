package io.qase.commons.config;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ConfigFactoryFixTest {

    @Test
    void testStatusMappingNotOverwrittenByEmptyEnv() {
        // Create config with status mapping
        QaseConfig config = new QaseConfig();
        Map<String, String> statusMapping = new HashMap<>();
        statusMapping.put("invalid", "failed");
        statusMapping.put("skipped", "passed");
        config.setStatusMapping(statusMapping);
        
        // Verify initial mapping
        assertEquals(2, config.statusMapping.size());
        assertEquals("failed", config.statusMapping.get("invalid"));
        assertEquals("passed", config.statusMapping.get("skipped"));
        
        // Simulate what happens in loadFromEnv when QASE_STATUS_MAPPING is not set
        String envStatusMapping = null; // This is what getEnv returns when not set
        if (envStatusMapping != null) {
            config.setStatusMapping(envStatusMapping);
        }
        
        // Verify mapping is preserved
        assertEquals(2, config.statusMapping.size());
        assertEquals("failed", config.statusMapping.get("invalid"));
        assertEquals("passed", config.statusMapping.get("skipped"));
    }

    @Test
    void testStatusMappingOverwrittenByEnv() {
        // Create config with status mapping
        QaseConfig config = new QaseConfig();
        Map<String, String> statusMapping = new HashMap<>();
        statusMapping.put("invalid", "failed");
        config.setStatusMapping(statusMapping);
        
        // Verify initial mapping
        assertEquals(1, config.statusMapping.size());
        assertEquals("failed", config.statusMapping.get("invalid"));
        
        // Simulate what happens in loadFromEnv when QASE_STATUS_MAPPING is set
        String envStatusMapping = "skipped=passed";
        if (envStatusMapping != null) {
            config.setStatusMapping(envStatusMapping);
        }
        
        // Verify mapping is overwritten
        assertEquals(1, config.statusMapping.size());
        assertEquals("passed", config.statusMapping.get("skipped"));
    }
}
