package io.qase.api;

import io.qase.client.model.ResultCreate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class CasesStorage {

    private static final ThreadLocal<ResultCreate> CURRENT_CASE = new ThreadLocal<>();

    public static void startCase(ResultCreate resultCreate) {
        checkCaseIsNotInProgress();

        CURRENT_CASE.set(resultCreate);
    }

    public static void stopCase() {
        checkCaseIsInProgress();

        CURRENT_CASE.remove();
    }

    public static ResultCreate getCurrentCase() {
        checkCaseIsInProgress();

        return CURRENT_CASE.get();
    }

    public static boolean isCaseInProgress() {
        return CURRENT_CASE.get() != null;
    }

    private static void checkCaseIsInProgress() {
        if (!isCaseInProgress()) {
            log.error("A case has not been started yet.");
        }
    }

    private static void checkCaseIsNotInProgress() {
        if (isCaseInProgress()) {
            log.error("Previous case is still in progress.");
        }
    }
}
