package io.qase.commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StepStorageTest {

    @BeforeEach
    void cleanState() throws Exception {
        // Clean up any leftover ThreadLocal state from previous tests
        try {
            StepStorage.stopSteps();
        } catch (Exception ignored) {
            // Ignore errors during cleanup
        }
        // Also clear the global STEPS_MAP in case orphans remain from a previous test run
        getStepsMap().clear();
    }

    @SuppressWarnings("unchecked")
    private Map<String, Object> getStepsMap() throws Exception {
        Field field = StepStorage.class.getDeclaredField("STEPS_MAP");
        field.setAccessible(true);
        return (Map<String, Object>) field.get(null);
    }

    @Test
    void orphanedStepIsCleanedByStopSteps() throws Exception {
        // Start a step but do NOT call stopStep() — simulates test abort mid-step
        StepStorage.startStep();

        Map<String, Object> stepsMap = getStepsMap();
        assertEquals(1, stepsMap.size(), "STEPS_MAP should have 1 entry after startStep");

        // stopSteps() without stopStep() should still clean up the orphan
        LinkedList<?> result = StepStorage.stopSteps();

        assertTrue(stepsMap.isEmpty(), "STEPS_MAP should be empty after stopSteps cleans up the orphan");
        assertTrue(result.isEmpty(), "returned list should be empty because orphan was never added to STEPS_STORAGE");
    }

    @Test
    void nestedOrphanedStepsAreCleaned() throws Exception {
        // Start a parent step, then a nested child step — simulate abort mid-nested-step
        StepStorage.startStep(); // parent
        StepStorage.startStep(); // child (nested)

        Map<String, Object> stepsMap = getStepsMap();
        assertEquals(2, stepsMap.size(), "STEPS_MAP should have 2 entries (parent + child)");

        // stopSteps() without any stopStep() should clean up both
        LinkedList<?> result = StepStorage.stopSteps();

        assertTrue(stepsMap.isEmpty(), "STEPS_MAP should be empty after stopSteps cleans up both parent and child");
        assertTrue(result.isEmpty(), "returned list should be empty because orphans were never added to STEPS_STORAGE");
    }

    @Test
    void normalFlowUnchanged() throws Exception {
        // Normal: startStep -> stopStep -> stopSteps
        StepStorage.startStep();
        StepStorage.stopStep();

        LinkedList<?> result = StepStorage.stopSteps();

        assertEquals(1, result.size(), "returned list should have 1 step from normal flow");
        Map<String, Object> stepsMap = getStepsMap();
        assertTrue(stepsMap.isEmpty(), "STEPS_MAP should be empty after normal flow stopSteps");
    }
}
