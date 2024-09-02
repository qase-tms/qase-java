package io.qase.cucumber6.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import io.qase.config.ApiClientConfigurer;
import io.qase.cucumber6.configuration.Cucumber6ApiConfigurer;
import io.qase.guice.Injectors;
import lombok.Getter;

public class Cucumber6Module extends AbstractModule {

    @Getter
    private static final Injector injector = initializeInjector();

    @Provides
    public ApiClientConfigurer apiClientConfigurer() {
        return new Cucumber6ApiConfigurer();
    }

    private static Injector initializeInjector() {
        return Injectors.createDefaultInjectorWithOverridingModules(new Cucumber6Module());
    }
}
