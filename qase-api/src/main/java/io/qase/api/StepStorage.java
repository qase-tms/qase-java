package io.qase.api;

import io.qase.client.model.ResultCreateSteps;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;

@Slf4j
public final class StepStorage {

    private static final ThreadLocal<ResultCreateSteps> STEP_IN_PROGRESS = new ThreadLocal<>();

    private static final ThreadLocal<LinkedList<ResultCreateSteps>> STEPS_STORAGE =
        ThreadLocal.withInitial(LinkedList::new);

    private static final ThreadLocal<Integer> STEP_POSITION = ThreadLocal.withInitial(() -> 1);

    public static void startStep() {
        checkStepIsNotInProgress();

        ResultCreateSteps resultCreateSteps = new ResultCreateSteps();
        int position = STEP_POSITION.get();
        resultCreateSteps.position(position);
        STEP_POSITION.set(++position);
        STEP_IN_PROGRESS.set(resultCreateSteps);
    }

    public static void stopStep() {
        checkStepIsInProgress();

        ResultCreateSteps resultCreateSteps = STEP_IN_PROGRESS.get();
        STEP_IN_PROGRESS.remove();
        STEPS_STORAGE.get().add(resultCreateSteps);
    }

    public static ResultCreateSteps getCurrentStep() {
        checkStepIsInProgress();

        return STEP_IN_PROGRESS.get();
    }

    public static boolean isStepInProgress() {
        return STEP_IN_PROGRESS.get() != null;
    }

    private static void checkStepIsInProgress() {
        if (!isStepInProgress()) {
            log.error("A step has not been started yet.");
        }
    }

    private static void checkStepIsNotInProgress() {
        if (isStepInProgress()) {
            log.error("A previous step is still in progress.");
        }
    }

    public static LinkedList<ResultCreateSteps> stopSteps() {
        checkStepIsNotInProgress();

        LinkedList<ResultCreateSteps> resultCreateSteps = STEPS_STORAGE.get();
        STEPS_STORAGE.remove();
        STEP_POSITION.set(1);
        return resultCreateSteps;
    }
}
