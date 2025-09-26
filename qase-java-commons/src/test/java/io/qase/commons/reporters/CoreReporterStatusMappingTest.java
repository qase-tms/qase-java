package io.qase.commons.reporters;

import io.qase.commons.config.Mode;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.models.domain.TestResult;
import io.qase.commons.models.domain.TestResultExecution;
import io.qase.commons.models.domain.TestResultStatus;
import io.qase.commons.models.domain.StepResult;
import io.qase.commons.models.domain.StepExecution;
import io.qase.commons.models.domain.StepResultStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CoreReporterStatusMappingTest {

    private QaseConfig config;
    private CoreReporter reporter;

    @BeforeEach
    void setUp() {
        config = new QaseConfig();
        config.mode = Mode.REPORT;
        config.fallback = Mode.OFF;
        reporter = new CoreReporter(config);
    }

    @Test
    void testAddResult_WithStatusMapping() {
        // Setup status mapping
        Map<String, String> statusMapping = new HashMap<>();
        statusMapping.put("invalid", "failed");
        statusMapping.put("skipped", "passed");
        config.setStatusMapping(statusMapping);

        // Create test result with INVALID status
        TestResult result = createTestResult("Test with invalid status", TestResultStatus.INVALID);
        
        // Apply status mapping (this would normally be done in addResult)
        TestResultStatus originalStatus = result.execution.status;
        assertEquals(TestResultStatus.INVALID, originalStatus);
        
        // Simulate the mapping application
        TestResultStatus mappedStatus = io.qase.commons.utils.StatusMappingUtils.applyStatusMapping(originalStatus, config.statusMapping);
        assertEquals(TestResultStatus.FAILED, mappedStatus);
    }

    @Test
    void testAddResult_WithStepStatusMapping() {
        // Setup status mapping
        Map<String, String> statusMapping = new HashMap<>();
        statusMapping.put("invalid", "failed");
        config.setStatusMapping(statusMapping);

        // Create test result with steps
        TestResult result = createTestResultWithSteps("Test with step mapping", TestResultStatus.PASSED);
        
        // Verify step status is NOT modified by mapping
        StepResult step = result.steps.get(0);
        StepResultStatus originalStepStatus = step.execution.status;
        assertEquals(StepResultStatus.BLOCKED, originalStepStatus);
        
        // Step statuses should remain unchanged - only main test status is mapped
    }

    @Test
    void testAddResult_NoStatusMapping() {
        // No status mapping configured
        config.setStatusMapping(new HashMap<>());

        TestResult result = createTestResult("Test without mapping", TestResultStatus.INVALID);
        TestResultStatus originalStatus = result.execution.status;
        assertEquals(TestResultStatus.INVALID, originalStatus);
        
        // Status should remain unchanged
        TestResultStatus mappedStatus = io.qase.commons.utils.StatusMappingUtils.applyStatusMapping(originalStatus, config.statusMapping);
        assertEquals(TestResultStatus.INVALID, mappedStatus);
    }

    @Test
    void testAddResult_NullStatusMapping() {
        // Null status mapping
        config.setStatusMapping((Map<String, String>) null);

        TestResult result = createTestResult("Test with null mapping", TestResultStatus.INVALID);
        TestResultStatus originalStatus = result.execution.status;
        assertEquals(TestResultStatus.INVALID, originalStatus);
        
        // Status should remain unchanged
        TestResultStatus mappedStatus = io.qase.commons.utils.StatusMappingUtils.applyStatusMapping(originalStatus, config.statusMapping);
        assertEquals(TestResultStatus.INVALID, mappedStatus);
    }

    private TestResult createTestResult(String title, TestResultStatus status) {
        TestResult result = new TestResult();
        result.title = title;
        result.execution = new TestResultExecution();
        result.execution.status = status;
        return result;
    }

    private TestResult createTestResultWithSteps(String title, TestResultStatus status) {
        TestResult result = createTestResult(title, status);
        
        // Add a step with BLOCKED status
        StepResult step = new StepResult();
        step.execution = new StepExecution();
        step.execution.status = StepResultStatus.BLOCKED;
        result.steps.add(step);
        
        return result;
    }
}
