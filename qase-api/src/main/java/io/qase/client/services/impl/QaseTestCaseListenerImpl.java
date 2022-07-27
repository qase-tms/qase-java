package io.qase.client.services.impl;

import com.google.inject.Inject;
import io.qase.api.QaseClient;
import io.qase.api.exceptions.QaseException;
import io.qase.client.api.RunsApi;
import io.qase.client.model.ResultCreate;
import io.qase.client.services.QaseTestCaseListener;
import io.qase.client.services.ReportersResultOperations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static io.qase.api.Constants.X_CLIENT_REPORTER;
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
    public void reportResults() { // TODO: make conformant with QaseConfig.useBulk() in concurrent mode
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

        startTestCaseTimer();
    }

    @Override
    public void onTestCaseFinished(ResultCreate resultCreate) {
        if (!QaseClient.isEnabled()) {
            return;
        }

        resultCreate.timeMs(stopTestCaseTimer());
        if (getConfig().useBulk()) {
            resultOperations.addBulkResult(resultCreate);
        } else {
            resultOperations.send(resultCreate);
        }
    }

    @Override
    public void setupReporterName(String reporterName) {
        runsApi.getApiClient().addDefaultHeader(X_CLIENT_REPORTER, reporterName);
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
