package io.qase.api;

import io.qase.api.inner.RouteFilter;
import io.qase.api.models.v1.shared_steps.NewSharedStep;
import io.qase.api.models.v1.shared_steps.SharedStep;
import io.qase.api.models.v1.shared_steps.SharedSteps;
import io.qase.api.services.SharedStepService;

import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singletonMap;

public final class SharedStepServiceImpl implements SharedStepService {
    private final QaseApiClient qaseApiClient;

    public SharedStepServiceImpl(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    @Override
    public SharedSteps getAll(String projectCode, int limit, int offset, RouteFilter filter) {
        return qaseApiClient.get(SharedSteps.class, "/shared_step/{code}", singletonMap("code", projectCode), filter, limit, offset);
    }

    @Override
    public SharedSteps getAll(String projectCode, RouteFilter filter) {
        return this.getAll(projectCode, 100, 0, filter);
    }

    @Override
    public SharedSteps getAll(String projectCode) {
        return this.getAll(projectCode, 100, 0, filter());
    }

    @Override
    public SharedStep get(String projectCode, String hash) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("hash", hash);
        return qaseApiClient.get(SharedStep.class, "/shared_step/{code}/{hash}", routeParams);
    }

    @Override
    public String create(String projectCode, String title, String action, String expectedResult) {
        NewSharedStep createUpdateSharedStepRequest = new NewSharedStep(title, action);
        createUpdateSharedStepRequest.setExpectedResult(expectedResult);
        return qaseApiClient.post(SharedStep.class,
                "/shared_step/{code}",
                singletonMap("code", projectCode),
                createUpdateSharedStepRequest).getHash();
    }

    @Override
    public String create(String projectCode, String title, String action) {
        return create(projectCode, title, action, null);
    }


    @Override
    public boolean delete(String projectCode, String hash) {
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("hash", hash);
        return (boolean) qaseApiClient.delete("/shared_step/{code}/{hash}", routeParams).get("status");
    }

    @Override
    public String update(String projectCode, String hash, String title, String action, String expectedResult) {
        NewSharedStep createUpdateSharedStepRequest = new NewSharedStep(title, action);
        createUpdateSharedStepRequest.setExpectedResult(expectedResult);
        Map<String, Object> routeParams = new HashMap<>();
        routeParams.put("code", projectCode);
        routeParams.put("hash", hash);
        return qaseApiClient.patch(SharedStep.class, "/shared_step/{code}/{id}", routeParams, createUpdateSharedStepRequest)
                .getHash();
    }

    @Override
    public String update(String projectCode, String hash, String title, String action) {
        return this.update(projectCode, hash, title, action, null);
    }
}
