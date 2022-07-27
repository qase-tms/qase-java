package io.qase.configuration;

import com.google.inject.*;
import io.qase.api.Constants;
import io.qase.api.QaseClient;
import io.qase.client.ApiClient;
import io.qase.client.api.AttachmentsApi;
import io.qase.client.api.ResultsApi;
import io.qase.client.api.RunsApi;
import io.qase.client.services.QaseTestCaseListener;
import io.qase.client.services.ReportersResultOperations;
import io.qase.client.services.ScreenshotsSender;
import io.qase.client.services.impl.AttachmentsApiScreenshotsUploader;
import io.qase.client.services.impl.QaseTestCaseListenerImpl;
import io.qase.client.services.impl.ReportersResultOperationsImpl;

public class QaseModule extends AbstractModule {

    public static Injector INJECTOR = Guice.createInjector(new QaseModule());

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
    public ScreenshotsSender screenshotsSender(AttachmentsApi attachmentsApi) {
        return new AttachmentsApiScreenshotsUploader(attachmentsApi);
    }

    @Provides
    @Singleton
    public AttachmentsApi attachmentsApi(ApiClient apiClient) {
        return new AttachmentsApi(apiClient);
    }
}
