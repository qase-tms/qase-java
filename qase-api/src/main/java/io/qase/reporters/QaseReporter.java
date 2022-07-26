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

    public void onTestCaseFinished(ResultCreate resultCreate) {
        if (getConfig().useBulk()) {
            resultOperations.addBulkResult(resultCreate);
        } else {
            resultOperations.send(resultCreate);
        }
    }

    public void setupReporterName(String reporterName) {
        runsApi.getApiClient().addDefaultHeader(X_CLIENT_REPORTER, reporterName);
    }
}
