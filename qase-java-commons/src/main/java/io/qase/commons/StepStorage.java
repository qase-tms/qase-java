package io.qase.commons;

import io.qase.commons.logger.Logger;
import io.qase.commons.models.domain.StepResult;

import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class StepStorage {
    private static final Logger logger = Logger.getInstance();
    private static final ThreadLocal<StepResult> STEP_IN_PROGRESS = new ThreadLocal<>();
    private static final ThreadLocal<String> STEP_ID = new ThreadLocal<>();
    private static final ThreadLocal<LinkedList<StepResult>> STEPS_STORAGE =
            ThreadLocal.withInitial(LinkedList::new);
    private static final Map<String, StepResult> STEPS_MAP = new ConcurrentHashMap<>();

    public static void startStep() {
        StepResult resultCreateSteps = new StepResult();

        if (isStepInProgress()) {
            StepResult currentStep = getCurrentStep();
            resultCreateSteps.parentId = currentStep.id;
            currentStep.steps.add(resultCreateSteps);
        }

        STEP_ID.set(resultCreateSteps.id);
        STEP_IN_PROGRESS.set(resultCreateSteps);
        STEPS_MAP.put(resultCreateSteps.id, resultCreateSteps);
    }

    public static void stopStep() {
        checkStepIsInProgress();

        StepResult resultCreateSteps = STEP_IN_PROGRESS.get();
        resultCreateSteps.execution.stop();

        if (resultCreateSteps.parentId != null) {
            STEP_ID.set(resultCreateSteps.parentId);
            STEP_IN_PROGRESS.set(STEPS_MAP.get(resultCreateSteps.parentId));
            return;
        }

        STEP_IN_PROGRESS.remove();
        STEPS_STORAGE.get().add(resultCreateSteps);
    }

    public static StepResult getCurrentStep() {
        checkStepIsInProgress();

        return STEP_IN_PROGRESS.get();
    }

    public static boolean isStepInProgress() {
        return STEP_IN_PROGRESS.get() != null;
    }

    private static void checkStepIsInProgress() {
        if (!isStepInProgress()) {
            logger.error("A step has not been started yet.");
        }
    }

    private static void checkStepIsNotInProgress() {
        if (isStepInProgress()) {
            logger.error("A previous step is still in progress.");
        }
    }

    public static LinkedList<StepResult> stopSteps() {
        checkStepIsNotInProgress();

        LinkedList<StepResult> resultCreateSteps = STEPS_STORAGE.get();
        STEPS_STORAGE.remove();

        return resultCreateSteps;
    }
}
