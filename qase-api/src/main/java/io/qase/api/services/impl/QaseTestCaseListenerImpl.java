package io.qase.api.services.impl;

import com.google.inject.Inject;
import io.qase.api.CasesStorage;
import io.qase.api.QaseClient;
import io.qase.api.exceptions.QaseException;
import io.qase.api.services.QaseTestCaseListener;
import io.qase.api.services.ReportersResultOperations;
import io.qase.client.api.RunsApi;
import io.qase.client.model.ResultCreate;
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
     * */
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    private final RunsApi runsApi;

    private final ReportersResultOperations resultOperations;

    @Override
    public void onTestCasesSetFinished() { // TODO: make conformant with QaseConfig.useBulk() in concurrent mode
        if (!QaseClient.isEnabled()) {
            return;
        }

        if (getConfig().useBulk()) {
            resultOperations.sendBulkResult();
        }
        if (getConfig().runAutocomplete()) {
            try {
                runsApi.completeRun(getConfig().projectCode(), getConfig().runId());
            } catch (QaseException e) {
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
        resultCreate.timeMs(stopTestCaseTimer());
        resultCreateConfigurer.accept(resultCreate);
        CasesStorage.stopCase();
        if (getConfig().useBulk()) {
            resultOperations.addBulkResult(resultCreate);
        } else {
            resultOperations.send(resultCreate);
        }
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
