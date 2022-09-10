package io.qase.cucumber4.module;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import io.qase.api.config.apiclient.ApiClientConfigurer;
import io.qase.cucumber4.configuration.Cucumber4ApiConfigurer;
import io.qase.guice.Injectors;
import lombok.Getter;

public class Cucumber4Module extends AbstractModule {

    @Getter
    private static final Injector injector = initializeInjector();

    @Provides
    public ApiClientConfigurer apiClientConfigurer() {
        return new Cucumber4ApiConfigurer();
    }

    private static Injector initializeInjector() {
        return Injectors.createDefaultInjectorWithOverridingModules(new Cucumber4Module());
    }
}
