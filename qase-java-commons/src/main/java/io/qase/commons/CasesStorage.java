package io.qase.commons;

import io.qase.commons.logger.Logger;
import io.qase.commons.models.domain.TestResult;


public final class CasesStorage {
    private static final Logger logger = Logger.getInstance();
    private static final ThreadLocal<TestResult> CURRENT_CASE = new ThreadLocal<>();

    public static void startCase(TestResult resultCreate) {
        checkCaseIsNotInProgress();

        CURRENT_CASE.set(resultCreate);
    }

    public static void stopCase() {
        checkCaseIsInProgress();

        CURRENT_CASE.remove();
    }

    public static TestResult getCurrentCase() {
        checkCaseIsInProgress();

        return CURRENT_CASE.get();
    }

    public static boolean isCaseInProgress() {
        return CURRENT_CASE.get() != null;
    }

    private static void checkCaseIsInProgress() {
        if (!isCaseInProgress()) {
            logger.error("A case has not been started yet.");
        }
    }

    private static void checkCaseIsNotInProgress() {
        if (isCaseInProgress()) {
            logger.error("Previous case is still in progress.");
        }
    }
}
