package io.qase.api.services.impl;

import com.google.inject.Inject;
import io.qase.api.exceptions.QaseException;
import io.qase.api.services.ReportersResultOperations;
import io.qase.client.api.ResultsApi;
import io.qase.client.model.ResultCreate;
import io.qase.client.model.ResultCreateBulk;
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
        } catch (QaseException e) {
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
        } catch (QaseException e) {
            log.error(e.getMessage());
        }
    }
}
