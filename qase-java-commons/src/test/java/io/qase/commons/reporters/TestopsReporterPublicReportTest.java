package io.qase.commons.reporters;

import io.qase.commons.QaseException;
import io.qase.commons.client.ApiClient;
import io.qase.commons.config.TestopsConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestopsReporterPublicReportTest {

    private TestopsConfig config;

    @BeforeEach
    void setUp() {
        config = new TestopsConfig();
        config.run.id = 123;
        config.showPublicReportLink = true;
    }

    @Test
    void testCompleteTestRun_WithPublicReportEnabled() {
        // This test verifies that the TestopsReporter can be created and configured
        // In a real scenario, you would mock the ApiClient
        
        // Arrange
        ApiClient mockClient = new ApiClient() {
            @Override
            public Long createTestRun() throws QaseException {
                return 123L;
            }

            @Override
            public void completeTestRun(Long runId) throws QaseException {
                // Mock implementation
            }

            @Override
            public void uploadResults(Long runId, java.util.List<io.qase.commons.models.domain.TestResult> results) throws QaseException {
                // Mock implementation
            }

            @Override
            public java.util.List<Long> getTestCaseIdsForExecution() throws QaseException {
                return java.util.Collections.emptyList();
            }

            @Override
            public void updateExternalIssue(Long runId) throws QaseException {
                // Mock implementation
            }

            @Override
            public String enablePublicReport(Long runId) throws QaseException {
                return "https://app.qase.io/public/report/abc123";
            }
        };

        TestopsReporter reporter = new TestopsReporter(config, mockClient);

        // Act & Assert
        assertDoesNotThrow(() -> {
            reporter.completeTestRun();
        });
    }

    @Test
    void testCompleteTestRun_WithPublicReportDisabled() {
        // Arrange
        config.showPublicReportLink = false;
        
        ApiClient mockClient = new ApiClient() {
            @Override
            public Long createTestRun() throws QaseException {
                return 123L;
            }

            @Override
            public void completeTestRun(Long runId) throws QaseException {
                // Mock implementation
            }

            @Override
            public void uploadResults(Long runId, java.util.List<io.qase.commons.models.domain.TestResult> results) throws QaseException {
                // Mock implementation
            }

            @Override
            public java.util.List<Long> getTestCaseIdsForExecution() throws QaseException {
                return java.util.Collections.emptyList();
            }

            @Override
            public void updateExternalIssue(Long runId) throws QaseException {
                // Mock implementation
            }

            @Override
            public String enablePublicReport(Long runId) throws QaseException {
                fail("enablePublicReport should not be called when showPublicReportLink is false");
                return null;
            }
        };

        TestopsReporter reporter = new TestopsReporter(config, mockClient);

        // Act & Assert
        assertDoesNotThrow(() -> {
            reporter.completeTestRun();
        });
    }
}
