package io.qase.api.services.impl;

import com.google.inject.Inject;
import io.qase.client.v1.ApiException;
import io.qase.api.services.ReportersResultOperations;
import io.qase.client.v1.api.ResultsApi;
import io.qase.client.v1.models.ResultCreate;
import io.qase.client.v1.models.ResultCreateBulk;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static io.qase.api.QaseClient.getConfig;

@RequiredArgsConstructor(onConstructor = @__(@Inject))
@Slf4j
public class ReportersResultOperationsImpl implements ReportersResultOperations {

    private final ResultCreateBulk resultCreateBulk = new ResultCreateBulk();

    private final ResultsApi resultsApi;

    @Override
    public void addBulkResult(ResultCreate resultCreate) {
        resultCreateBulk.addResultsItem(resultCreate);
    }

    @Override
    public void send(ResultCreate resultCreate) {
        try {
            resultsApi.createResult(getConfig().projectCode(),
                getConfig().runId(),
                resultCreate);
        } catch (ApiException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void sendBulkResult() {
        try {
            resultsApi.createResultBulk(
                getConfig().projectCode(),
                getConfig().runId(),
                resultCreateBulk
            );
            resultCreateBulk.getResults().clear();
        } catch (ApiException e) {
            log.error(e.getMessage());
        }
    }
}
