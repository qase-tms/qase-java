package io.qase.guice.module;

import com.google.inject.*;
import io.qase.config.ApiClientConfigurer;
import io.qase.api.QaseClient;
import io.qase.api.services.QaseTestCaseListener;
import io.qase.api.services.ReportersResultOperations;
import io.qase.api.services.impl.QaseTestCaseListenerImpl;
import io.qase.api.services.impl.ReportersResultOperationsImpl;
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.api.AttachmentsApi;
import io.qase.client.v1.api.ResultsApi;
import io.qase.client.v1.api.RunsApi;

public class QaseModule extends AbstractModule {

    @Override
    protected void configure() {
        requireBinding(ApiClientConfigurer.class);

        bind(ReportersResultOperations.class).to(ReportersResultOperationsImpl.class);
        bind(QaseTestCaseListener.class).to(QaseTestCaseListenerImpl.class);
    }

    @Provides
    @Singleton
    public ApiClient apiClient(ApiClientConfigurer apiClientConfigurer) {
        ApiClient apiClient = QaseClient.getApiClient();
        apiClientConfigurer.configure(apiClient);
        return apiClient;
    }

    @Provides
    @Singleton
    public ResultsApi resultsApi(ApiClient apiClient) {
        return new ResultsApi(apiClient);
    }

    @Provides
    @Singleton
    public RunsApi runsApi(ApiClient apiClient) {
        return new RunsApi(apiClient);
    }

    @Provides
    @Singleton
    public AttachmentsApi attachmentsApi(ApiClient apiClient) {
        return new AttachmentsApi(apiClient);
    }
}
