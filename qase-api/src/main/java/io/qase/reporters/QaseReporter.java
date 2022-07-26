package io.qase.reporters;

import com.google.inject.Inject;
import io.qase.api.exceptions.QaseException;
import io.qase.client.api.RunsApi;
import io.qase.client.model.ResultCreate;
import io.qase.client.services.ReportersResultOperations;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static io.qase.api.Constants.X_CLIENT_REPORTER;
import static io.qase.api.QaseClient.getConfig;

@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class QaseReporter {

    /**
     * @see #startTestCaseTimer
     * @see #stopTestCaseTimer
     * */
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();

    private final RunsApi runsApi;

    private final ReportersResultOperations resultOperations;

    public void onTestRunFinished() {
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

    public void onTestCaseStarted() {
        startTestCaseTimer();
    }

    public void onTestCaseFinished(ResultCreate resultCreate) {
        resultCreate.timeMs(stopTestCaseTimer());
        if (getConfig().useBulk()) {
            resultOperations.addBulkResult(resultCreate);
        } else {
            resultOperations.send(resultCreate);
        }
    }

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
