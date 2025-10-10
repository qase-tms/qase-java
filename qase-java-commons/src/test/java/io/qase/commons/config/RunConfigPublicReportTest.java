package io.qase.commons.config;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestopsConfigPublicReportTest {

    @Test
    void testDefaultShowPublicReportLink() {
        // Arrange & Act
        TestopsConfig config = new TestopsConfig();

        // Assert
        assertFalse(config.showPublicReportLink, "Default value should be false");
    }

    @Test
    void testSetShowPublicReportLink() {
        // Arrange
        TestopsConfig config = new TestopsConfig();

        // Act
        config.showPublicReportLink = true;

        // Assert
        assertTrue(config.showPublicReportLink);
    }
}
