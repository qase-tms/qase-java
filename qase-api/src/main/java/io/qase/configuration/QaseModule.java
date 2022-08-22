package io.qase.configuration;

import com.google.inject.*;
import io.qase.api.Constants;
import io.qase.api.QaseClient;
import io.qase.api.config.QaseConfig;
import io.qase.client.ApiClient;
import io.qase.client.api.AttachmentsApi;
import io.qase.client.api.PlansApi;
import io.qase.client.api.ResultsApi;
import io.qase.client.api.RunsApi;
import io.qase.api.services.QaseTestCaseListener;
import io.qase.api.services.ReportersResultOperations;
import io.qase.api.services.impl.QaseTestCaseListenerImpl;
import io.qase.api.services.impl.ReportersResultOperationsImpl;

public class QaseModule extends AbstractModule {

    private static Injector INJECTOR = Guice.createInjector(new QaseModule());

    public static Injector getInjector() {
        return INJECTOR;
    }

    @Override
    protected void configure() {
        bind(ReportersResultOperations.class).to(ReportersResultOperationsImpl.class);
        bind(QaseTestCaseListener.class).to(QaseTestCaseListenerImpl.class);
    }

    @Provides
    @Singleton
    public ApiClient apiClient() {
        ApiClient apiClient = QaseClient.getApiClient();
        apiClient.addDefaultHeader(Constants.X_CLIENT_REPORTER, QaseClient.getConfig().clientReporterName());
        apiClient.setBasePath(System.getProperty(QaseConfig.BASE_URL_KEY));
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

    @Singleton
    @Provides
    public PlansApi plansApi(ApiClient apiClient) {
        return new PlansApi(apiClient);
    }
}
