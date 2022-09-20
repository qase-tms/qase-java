package io.qase.guice.module;

import com.google.inject.*;
import io.qase.api.config.apiclient.ApiClientConfigurer;
import io.qase.api.QaseClient;
import io.qase.api.config.QaseConfig;
import io.qase.api.services.TestPlanService;
import io.qase.api.services.impl.TestPlanServiceImpl;
import io.qase.client.ApiClient;
import io.qase.client.api.AttachmentsApi;
import io.qase.client.api.PlansApi;
import io.qase.client.api.ResultsApi;
import io.qase.client.api.RunsApi;
import io.qase.api.services.QaseTestCaseListener;
import io.qase.api.services.ReportersResultOperations;
import io.qase.api.services.impl.QaseTestCaseListenerImpl;
import io.qase.api.services.impl.ReportersResultOperationsImpl;
import io.qase.plugin.codeparsing.ClassParser;
import io.qase.plugin.codeparsing.MethodFilter;
import io.qase.plugin.codeparsing.impl.ClassParserImpl;
import io.qase.plugin.codeparsing.impl.MethodFilterImpl;
import io.qase.client.ApiClient;
import io.qase.client.api.AttachmentsApi;
import io.qase.client.api.ResultsApi;
import io.qase.client.api.RunsApi;

public class QaseModule extends AbstractModule {

    private static final Injector injector = Guice.createInjector(new QaseModule());

    public static Injector getInjector() {
        return injector;
    }

    /**
     * This is just a utility method to increase readability
     * */
    public static <T> T inject(Class<T> clazz) {
        return getInjector().getInstance(clazz);
    }

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
    public PlansApi plansApi(ApiClient apiClient) {
        return new PlansApi(apiClient);
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

    @Provides
    @Singleton
    public ClassParser classParser() {
        return new ClassParserImpl();
    }

    @Provides
    @Singleton
    public MethodFilter methodFilter() {
        return new MethodFilterImpl();
    }

    @Provides
    @Singleton
    public TestPlanService testPlanService(PlansApi plansApi) {
        return new TestPlanServiceImpl(plansApi);
    }
}
