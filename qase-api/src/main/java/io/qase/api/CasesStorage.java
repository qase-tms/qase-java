package io.qase.api;

import io.qase.client.model.ResultCreate;

public final class CasesStorage {

    private static final ThreadLocal<ResultCreate> CURRENT_CASE = new ThreadLocal<>();

    public static void startCase(ResultCreate resultCreate) {
        ensureCaseIsNotInProgress();

        CURRENT_CASE.set(resultCreate);
    }

    public static void stopCase() {
        ensureCaseIsInProgress();

        CURRENT_CASE.remove();
    }

    public static ResultCreate getCurrentCase() {
        ensureCaseIsInProgress();

        return CURRENT_CASE.get();
    }

    public static boolean isCaseInProgress() {
        return CURRENT_CASE.get() != null;
    }

    public static void removeCurrentCase() {
        CURRENT_CASE.remove();
    }

    private static void ensureCaseIsInProgress() {
        if (!isCaseInProgress()) {
            throw new IllegalStateException("A case has not been started yet.");
        }
    }

    private static void ensureCaseIsNotInProgress() {
        if (isCaseInProgress()) {
            throw new IllegalStateException("Previous case is still in progress.");
        }
    }
}
