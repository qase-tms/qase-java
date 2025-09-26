package io.qase.commons.utils;

import io.qase.commons.logger.Logger;
import io.qase.commons.models.domain.TestResultStatus;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Utility class for handling status mapping functionality.
 * Provides methods to parse, validate and apply status mappings.
 */
public class StatusMappingUtils {
    private static final Logger logger = Logger.getInstance();
    
    // Valid status values for mapping
    private static final Set<String> VALID_STATUSES = new HashSet<String>() {{
        add("passed");
        add("failed");
        add("skipped");
        add("blocked");
        add("invalid");
    }};
    
    /**
     * Parse status mapping from environment variable format.
     * Expected format: "invalid=failed,skipped=passed"
     * 
     * @param mappingString the mapping string from environment variable
     * @return map of status mappings, empty map if parsing fails
     */
    public static Map<String, String> parseStatusMapping(String mappingString) {
        Map<String, String> mapping = new HashMap<>();
        
        if (mappingString == null || mappingString.trim().isEmpty()) {
            return mapping;
        }
        
        try {
            String[] pairs = mappingString.split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.trim().split("=");
                if (keyValue.length == 2) {
                    String fromStatus = keyValue[0].trim().toLowerCase();
                    String toStatus = keyValue[1].trim().toLowerCase();
                    
                    if (isValidStatus(fromStatus) && isValidStatus(toStatus)) {
                        mapping.put(fromStatus, toStatus);
                    } else {
                        logger.warn("Invalid status mapping pair ignored: %s=%s", fromStatus, toStatus);
                    }
                } else {
                    logger.warn("Invalid mapping format ignored: %s", pair);
                }
            }
        } catch (Exception e) {
            logger.error("Failed to parse status mapping: %s", e.getMessage());
        }
        
        return mapping;
    }
    
    /**
     * Validate status mapping map.
     * 
     * @param mapping the mapping to validate
     * @return true if mapping is valid, false otherwise
     */
    public static boolean validateStatusMapping(Map<String, String> mapping) {
        if (mapping == null) {
            return true;
        }
        
        for (Map.Entry<String, String> entry : mapping.entrySet()) {
            String fromStatus = entry.getKey();
            String toStatus = entry.getValue();
            
            if (!isValidStatus(fromStatus) || !isValidStatus(toStatus)) {
                logger.warn("Invalid status mapping: %s -> %s", fromStatus, toStatus);
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * Apply status mapping to a test result status.
     * 
     * @param originalStatus the original status
     * @param mapping the status mapping
     * @return the mapped status or original status if no mapping exists
     */
    public static TestResultStatus applyStatusMapping(TestResultStatus originalStatus, Map<String, String> mapping) {
        if (originalStatus == null || mapping == null || mapping.isEmpty()) {
            return originalStatus;
        }
        
        String originalStatusStr = originalStatus.name().toLowerCase();
        String mappedStatusStr = mapping.get(originalStatusStr);
        
        if (mappedStatusStr != null) {
            try {
                TestResultStatus mappedStatus = TestResultStatus.valueOf(mappedStatusStr.toUpperCase());
                logger.debug("Status mapping applied: %s -> %s", originalStatusStr, mappedStatusStr);
                return mappedStatus;
            } catch (IllegalArgumentException e) {
                logger.warn("Failed to map status %s to %s: %s", originalStatusStr, mappedStatusStr, e.getMessage());
            }
        }
        
        return originalStatus;
    }
    
    /**
     * Check if a status string is valid for mapping.
     * 
     * @param status the status string to check
     * @return true if status is valid, false otherwise
     */
    private static boolean isValidStatus(String status) {
        return status != null && VALID_STATUSES.contains(status.toLowerCase());
    }
    
    /**
     * Convert TestResultStatus enum to lowercase string for mapping.
     * 
     * @param status the TestResultStatus enum value
     * @return lowercase string representation
     */
    public static String statusToString(TestResultStatus status) {
        return status != null ? status.name().toLowerCase() : null;
    }
    
    /**
     * Convert string to TestResultStatus enum.
     * 
     * @param statusStr the status string
     * @return TestResultStatus enum or null if invalid
     */
    public static TestResultStatus stringToStatus(String statusStr) {
        if (statusStr == null || statusStr.trim().isEmpty()) {
            return null;
        }
        
        try {
            return TestResultStatus.valueOf(statusStr.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid status string: %s", statusStr);
            return null;
        }
    }
}
