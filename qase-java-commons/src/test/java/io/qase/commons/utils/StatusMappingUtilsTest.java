package io.qase.commons.utils;

import io.qase.commons.models.domain.TestResultStatus;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StatusMappingUtilsTest {

    @Test
    void testParseStatusMapping_ValidInput() {
        String mappingString = "invalid=failed,skipped=passed";
        Map<String, String> result = StatusMappingUtils.parseStatusMapping(mappingString);
        
        assertEquals(2, result.size());
        assertEquals("failed", result.get("invalid"));
        assertEquals("passed", result.get("skipped"));
    }

    @Test
    void testParseStatusMapping_EmptyInput() {
        Map<String, String> result = StatusMappingUtils.parseStatusMapping("");
        assertTrue(result.isEmpty());
        
        result = StatusMappingUtils.parseStatusMapping(null);
        assertTrue(result.isEmpty());
    }

    @Test
    void testParseStatusMapping_InvalidFormat() {
        String mappingString = "invalid=failed,invalidformat,skipped=passed";
        Map<String, String> result = StatusMappingUtils.parseStatusMapping(mappingString);
        
        assertEquals(2, result.size());
        assertEquals("failed", result.get("invalid"));
        assertEquals("passed", result.get("skipped"));
    }

    @Test
    void testParseStatusMapping_InvalidStatus() {
        String mappingString = "invalid=failed,unknown=passed";
        Map<String, String> result = StatusMappingUtils.parseStatusMapping(mappingString);
        
        assertEquals(1, result.size());
        assertEquals("failed", result.get("invalid"));
    }

    @Test
    void testValidateStatusMapping_ValidMapping() {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("invalid", "failed");
        mapping.put("skipped", "passed");
        
        assertTrue(StatusMappingUtils.validateStatusMapping(mapping));
    }

    @Test
    void testValidateStatusMapping_InvalidMapping() {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("invalid", "failed");
        mapping.put("unknown", "passed");
        
        assertFalse(StatusMappingUtils.validateStatusMapping(mapping));
    }

    @Test
    void testValidateStatusMapping_NullMapping() {
        assertTrue(StatusMappingUtils.validateStatusMapping(null));
    }

    @Test
    void testApplyStatusMapping_WithMapping() {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("invalid", "failed");
        mapping.put("skipped", "passed");
        
        TestResultStatus result = StatusMappingUtils.applyStatusMapping(TestResultStatus.INVALID, mapping);
        assertEquals(TestResultStatus.FAILED, result);
        
        result = StatusMappingUtils.applyStatusMapping(TestResultStatus.SKIPPED, mapping);
        assertEquals(TestResultStatus.PASSED, result);
    }

    @Test
    void testApplyStatusMapping_NoMapping() {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("invalid", "failed");
        
        TestResultStatus result = StatusMappingUtils.applyStatusMapping(TestResultStatus.PASSED, mapping);
        assertEquals(TestResultStatus.PASSED, result);
    }

    @Test
    void testApplyStatusMapping_NullInput() {
        Map<String, String> mapping = new HashMap<>();
        mapping.put("invalid", "failed");
        
        TestResultStatus result = StatusMappingUtils.applyStatusMapping(null, mapping);
        assertNull(result);
        
        result = StatusMappingUtils.applyStatusMapping(TestResultStatus.INVALID, null);
        assertEquals(TestResultStatus.INVALID, result);
    }

    @Test
    void testApplyStatusMapping_EmptyMapping() {
        Map<String, String> mapping = new HashMap<>();
        
        TestResultStatus result = StatusMappingUtils.applyStatusMapping(TestResultStatus.INVALID, mapping);
        assertEquals(TestResultStatus.INVALID, result);
    }

    @Test
    void testStatusToString() {
        assertEquals("passed", StatusMappingUtils.statusToString(TestResultStatus.PASSED));
        assertEquals("failed", StatusMappingUtils.statusToString(TestResultStatus.FAILED));
        assertEquals("skipped", StatusMappingUtils.statusToString(TestResultStatus.SKIPPED));
        assertEquals("invalid", StatusMappingUtils.statusToString(TestResultStatus.INVALID));
        assertNull(StatusMappingUtils.statusToString(null));
    }

    @Test
    void testStringToStatus() {
        assertEquals(TestResultStatus.PASSED, StatusMappingUtils.stringToStatus("passed"));
        assertEquals(TestResultStatus.PASSED, StatusMappingUtils.stringToStatus("PASSED"));
        assertEquals(TestResultStatus.PASSED, StatusMappingUtils.stringToStatus(" Passed "));
        assertEquals(TestResultStatus.FAILED, StatusMappingUtils.stringToStatus("failed"));
        assertEquals(TestResultStatus.SKIPPED, StatusMappingUtils.stringToStatus("skipped"));
        assertEquals(TestResultStatus.INVALID, StatusMappingUtils.stringToStatus("invalid"));
        
        assertNull(StatusMappingUtils.stringToStatus("unknown"));
        assertNull(StatusMappingUtils.stringToStatus(null));
        assertNull(StatusMappingUtils.stringToStatus(""));
    }
}
