package io.qase.cucumber3.module;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import io.qase.api.config.apiclient.ApiClientConfigurer;
import io.qase.cucumber3.configuration.Cucumber3ApiConfigurer;
import io.qase.guice.Injectors;
import lombok.Getter;

public class Cucumber3Module extends AbstractModule {

    @Getter
    private static final Injector injector = initializeInjector();

    @Provides
    public ApiClientConfigurer apiClientConfigurer() {
        return new Cucumber3ApiConfigurer();
    }

    private static Injector initializeInjector() {
        return Injectors.createDefaultInjectorWithOverridingModules(new Cucumber3Module());
    }
}
