package io.qase.commons.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigFactoryPublicReportTest {

    @Test
    void testLoadFromEnv_ShowPublicReportLink() {
        // Arrange
        String originalValue = System.getenv("QASE_TESTOPS_SHOW_PUBLIC_REPORT_LINK");
        
        try {
            // Set environment variable
            System.setProperty("QASE_TESTOPS_SHOW_PUBLIC_REPORT_LINK", "true");
            
            // Act
            QaseConfig config = ConfigFactory.loadConfig();
            
            // Assert
            assertTrue(config.testops.showPublicReportLink, "Environment variable should be loaded");
        } finally {
            // Cleanup
            if (originalValue != null) {
                System.setProperty("QASE_TESTOPS_SHOW_PUBLIC_REPORT_LINK", originalValue);
            } else {
                System.clearProperty("QASE_TESTOPS_SHOW_PUBLIC_REPORT_LINK");
            }
        }
    }

    @Test
    void testLoadFromEnv_ShowPublicReportLinkFalse() {
        // Arrange
        String originalValue = System.getenv("QASE_TESTOPS_SHOW_PUBLIC_REPORT_LINK");
        
        try {
            // Set environment variable
            System.setProperty("QASE_TESTOPS_SHOW_PUBLIC_REPORT_LINK", "false");
            
            // Act
            QaseConfig config = ConfigFactory.loadConfig();
            
            // Assert
            assertFalse(config.testops.showPublicReportLink, "Environment variable should be loaded as false");
        } finally {
            // Cleanup
            if (originalValue != null) {
                System.setProperty("QASE_TESTOPS_SHOW_PUBLIC_REPORT_LINK", originalValue);
            } else {
                System.clearProperty("QASE_TESTOPS_SHOW_PUBLIC_REPORT_LINK");
            }
        }
    }

    @Test
    void testDefaultValue_ShowPublicReportLink() {
        // Arrange
        String originalValue = System.getenv("QASE_TESTOPS_SHOW_PUBLIC_REPORT_LINK");
        
        try {
            // Clear environment variable
            System.clearProperty("QASE_TESTOPS_SHOW_PUBLIC_REPORT_LINK");
            
            // Act
            QaseConfig config = ConfigFactory.loadConfig();
            
            // Assert
            assertFalse(config.testops.showPublicReportLink, "Default value should be false");
        } finally {
            // Cleanup
            if (originalValue != null) {
                System.setProperty("QASE_TESTOPS_SHOW_PUBLIC_REPORT_LINK", originalValue);
            } else {
                System.clearProperty("QASE_TESTOPS_SHOW_PUBLIC_REPORT_LINK");
            }
        }
    }
}
