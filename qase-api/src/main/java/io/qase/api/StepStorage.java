package io.qase.api;

import io.qase.client.model.ResultCreateSteps;

import java.util.LinkedList;

public final class StepStorage {
    private static final ThreadLocal<LinkedList<ResultCreateSteps>> stepsStorage = ThreadLocal.withInitial(LinkedList::new);
    private static final ThreadLocal<Integer> stepPosition = ThreadLocal.withInitial(() -> 1);

    public static void addStep(ResultCreateSteps steps) {
        LinkedList<ResultCreateSteps> resultCreateSteps = StepStorage.stepsStorage.get();
        int position = stepPosition.get();
        resultCreateSteps.add(steps.position(position));
        stepPosition.set(++position);
    }

    public static LinkedList<ResultCreateSteps> getSteps() {
        LinkedList<ResultCreateSteps> resultCreateSteps = stepsStorage.get();
        clearSteps();
        return resultCreateSteps;
    }

    public static void clearSteps() {
        stepsStorage.remove();
        stepPosition.set(1);
    }
}
