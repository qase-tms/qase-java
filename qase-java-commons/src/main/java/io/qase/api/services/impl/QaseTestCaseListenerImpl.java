package io.qase.api.services.impl;

import com.google.inject.Inject;
import io.qase.api.CasesStorage;
import io.qase.api.QaseClient;
import io.qase.client.v1.ApiException;
import io.qase.api.services.QaseTestCaseListener;
import io.qase.api.services.ReportersResultOperations;
import io.qase.client.v1.api.RunsApi;
import io.qase.client.v1.models.ResultCreate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

import static io.qase.api.QaseClient.getConfig;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class QaseTestCaseListenerImpl implements QaseTestCaseListener {

    /**
     * @see #startTestCaseTimer
     * @see #stopTestCaseTimer
     */
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    private final RunsApi runsApi;

    private final ReportersResultOperations resultOperations;

    @Override
    public void onTestCasesSetFinished() { // TODO: make conformant with QaseConfig.useBulk() in concurrent mode
        if (!QaseClient.isEnabled()) {
            return;
        }

        resultOperations.sendBulkResult();

        if (getConfig().testops.run.complete) {
            try {
                runsApi.completeRun(getConfig().testops.project, getConfig().testops.run.id);
            } catch (ApiException e) {
                log.error(e.getMessage());
            }
        }
    }

    @Override
    public void onTestCaseStarted() {
        if (!QaseClient.isEnabled()) {
            return;
        }

        CasesStorage.startCase(new ResultCreate());
        startTestCaseTimer();
    }

    @Override
    public void onTestCaseFinished(Consumer<ResultCreate> resultCreateConfigurer) {
        if (!QaseClient.isEnabled()) {
            return;
        }

        ResultCreate resultCreate = CasesStorage.getCurrentCase();
        if (resultCreate == null) {
            return;
        }
        resultCreate.timeMs(stopTestCaseTimer());
        resultCreateConfigurer.accept(resultCreate);
        CasesStorage.stopCase();
        if (resultCreate.getCaseId() == null
                && (resultCreate.getCase() == null || resultCreate.getCase().getTitle() == null)) {
            return;
        }

        resultOperations.addBulkResult(resultCreate);
    }

    private void startTestCaseTimer() {
        startTime.set(System.currentTimeMillis());
    }

    private long stopTestCaseTimer() {
        long testCaseStartTime = startTime.get();
        startTime.remove();
        return System.currentTimeMillis() - testCaseStartTime;
    }
}
