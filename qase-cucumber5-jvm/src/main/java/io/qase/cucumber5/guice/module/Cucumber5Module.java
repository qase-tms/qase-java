package io.qase.cucumber5.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import io.qase.api.config.apiclient.ApiClientConfigurer;
import io.qase.cucumber5.configuration.Cucumber5ApiConfigurer;
import io.qase.guice.Injectors;
import lombok.Getter;

public class Cucumber5Module extends AbstractModule {

    @Getter
    private static final Injector injector = initializeInjector();

    @Provides
    public ApiClientConfigurer apiClientConfigurer() {
        return new Cucumber5ApiConfigurer();
    }

    private static Injector initializeInjector() {
        return Injectors.createDefaultInjectorWithOverridingModules(new Cucumber5Module());
    }
}
