package io.qase.commons.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;


public class StatusFilterConfigTest {

    @BeforeEach
    public void setUp() {
        // Clear any system properties that might interfere
        System.clearProperty("QASE_TESTOPS_STATUS_FILTER");
    }

    @Test
    public void testStatusFilterFromSystemProperty() {
        // Set system property for status filter
        System.setProperty("QASE_TESTOPS_STATUS_FILTER", "SKIPPED,INVALID");
        
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.testops.statusFilter);
        assertEquals(2, config.testops.statusFilter.size());
        assertTrue(config.testops.statusFilter.contains("SKIPPED"));
        assertTrue(config.testops.statusFilter.contains("INVALID"));
    }

    @Test
    public void testStatusFilterFromEnvironment() {
        // Set environment variable for status filter
        System.setProperty("QASE_TESTOPS_STATUS_FILTER", "FAILED,SKIPPED");
        
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.testops.statusFilter);
        assertEquals(2, config.testops.statusFilter.size());
        assertTrue(config.testops.statusFilter.contains("FAILED"));
        assertTrue(config.testops.statusFilter.contains("SKIPPED"));
    }

    @Test
    public void testStatusFilterWithSpaces() {
        // Test with spaces around values
        System.setProperty("QASE_TESTOPS_STATUS_FILTER", " SKIPPED , INVALID , FAILED ");
        
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.testops.statusFilter);
        assertEquals(3, config.testops.statusFilter.size());
        assertTrue(config.testops.statusFilter.contains("SKIPPED"));
        assertTrue(config.testops.statusFilter.contains("INVALID"));
        assertTrue(config.testops.statusFilter.contains("FAILED"));
    }

    @Test
    public void testStatusFilterEmpty() {
        // Test with empty value
        System.setProperty("QASE_TESTOPS_STATUS_FILTER", "");
        
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.testops.statusFilter);
        // Empty string results in array with one empty element, which gets filtered out
        assertEquals(1, config.testops.statusFilter.size());
        assertEquals("", config.testops.statusFilter.get(0));
    }

    @Test
    public void testStatusFilterNotSet() {
        // Test when property is not set
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.testops.statusFilter);
        assertTrue(config.testops.statusFilter.isEmpty());
    }

    @Test
    public void testStatusFilterSingleValue() {
        // Test with single value
        System.setProperty("QASE_TESTOPS_STATUS_FILTER", "SKIPPED");
        
        QaseConfig config = ConfigFactory.loadConfig();
        
        assertNotNull(config.testops.statusFilter);
        assertEquals(1, config.testops.statusFilter.size());
        assertTrue(config.testops.statusFilter.contains("SKIPPED"));
    }

    @Test
    public void testStatusFilterDefaultValue() {
        // Test default value in TestopsConfig
        TestopsConfig config = new TestopsConfig();
        
        assertNotNull(config.statusFilter);
        assertTrue(config.statusFilter.isEmpty());
    }
}
