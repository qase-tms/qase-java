package io.qase.api;

import io.qase.client.model.ResultCreateSteps;

import java.util.LinkedList;

public final class StepStorage {

    private static final ThreadLocal<ResultCreateSteps> STEP_IN_PROGRESS = new ThreadLocal<>();

    private static final ThreadLocal<LinkedList<ResultCreateSteps>> STEPS_STORAGE =
        ThreadLocal.withInitial(LinkedList::new);

    private static final ThreadLocal<Integer> STEP_POSITION = ThreadLocal.withInitial(() -> 1);

    public static void startStep() {
        ensureStepIsNotInProgress();

        ResultCreateSteps resultCreateSteps = new ResultCreateSteps();
        int position = STEP_POSITION.get();
        resultCreateSteps.position(position);
        STEP_POSITION.set(++position);
        STEP_IN_PROGRESS.set(resultCreateSteps);
    }

    public static void stopStep() {
        ensureStepIsInProgress();

        ResultCreateSteps resultCreateSteps = STEP_IN_PROGRESS.get();
        STEP_IN_PROGRESS.remove();
        STEPS_STORAGE.get().add(resultCreateSteps);
    }

    public static ResultCreateSteps getCurrentStep() {
        ensureStepIsInProgress();

        return STEP_IN_PROGRESS.get();
    }

    public static boolean isStepInProgress() {
        return STEP_IN_PROGRESS.get() != null;
    }

    private static void ensureStepIsInProgress() {
        if (!isStepInProgress()) {
            throw new IllegalStateException("A step has not been started yet.");
        }
    }

    private static void ensureStepIsNotInProgress() {
        if (isStepInProgress()) {
            throw new IllegalStateException("A previous step is still in progress.");
        }
    }

    public static LinkedList<ResultCreateSteps> stopSteps() {
        ensureStepIsNotInProgress();

        LinkedList<ResultCreateSteps> resultCreateSteps = STEPS_STORAGE.get();
        STEPS_STORAGE.remove();
        STEP_POSITION.set(1);
        return resultCreateSteps;
    }
}
