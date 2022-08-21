package io.qase.plugin.maven.configuration;

import com.google.inject.*;
import io.qase.api.services.TestPlanService;
import io.qase.api.services.impl.TestPlanServiceImpl;
import io.qase.client.api.PlansApi;
import io.qase.configuration.QaseModule;
import io.qase.plugin.maven.codeparsing.ClassParser;
import io.qase.plugin.maven.codeparsing.MethodFilter;
import io.qase.plugin.maven.codeparsing.impl.ClassParserImpl;
import io.qase.plugin.maven.codeparsing.impl.MethodFilterImpl;

public class QaseMavenModule extends AbstractModule {

    public static final Injector INJECTOR = Guice.createInjector(new QaseMavenModule(), new QaseModule());

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
    public TestPlanService testPlanService(PlansApi plansApi) {
        return new TestPlanServiceImpl(plansApi);
    }
}
