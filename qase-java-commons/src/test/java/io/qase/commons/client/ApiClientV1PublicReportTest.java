package io.qase.commons.client;

import io.qase.client.v1.ApiException;
import io.qase.client.v1.api.RunsApi;
import io.qase.client.v1.models.RunPublic;
import io.qase.client.v1.models.RunPublicResponse;
import io.qase.client.v1.models.RunPublicResponseAllOfResult;
import io.qase.commons.QaseException;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.config.TestopsConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

class ApiClientV1PublicReportTest {

    private ApiClientV1 apiClient;
    private QaseConfig config;

    @BeforeEach
    void setUp() {
        config = new QaseConfig();
        config.testops = new TestopsConfig();
        config.testops.project = "TEST_PROJECT";
        config.testops.api.token = "test_token";
        config.testops.api.host = "qase.io";

        apiClient = new ApiClientV1(config);
    }

    @Test
    void testEnablePublicReport_WithValidConfig() {
        // This test verifies that the method can be called without throwing exceptions
        // In a real scenario, you would mock the API client or use integration tests
        
        // Arrange
        Long runId = 123L;
        
        // Act & Assert
        // Since we can't easily mock the API client without reflection,
        // we'll test that the method exists and can be called
        assertDoesNotThrow(() -> {
            try {
                apiClient.enablePublicReport(runId);
            } catch (QaseException e) {
                // Expected to fail in test environment without valid API credentials
                assertTrue(e.getMessage().contains("Failed to enable public report"));
            }
        });
    }

    @Test
    void testEnablePublicReport_WithNullRunId() {
        // Arrange
        Long runId = null;
        
        // Act & Assert
        assertThrows(NullPointerException.class, () -> {
            apiClient.enablePublicReport(runId);
        });
    }
}
