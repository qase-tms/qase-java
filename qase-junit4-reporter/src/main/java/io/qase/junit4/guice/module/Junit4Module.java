package io.qase.junit4.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import io.qase.api.config.apiclient.ApiClientConfigurer;
import io.qase.guice.Injectors;
import io.qase.junit4.configuration.Junit4ApiConfigurer;
import lombok.Getter;

public class Junit4Module extends AbstractModule {

    @Getter
    private static final Injector injector = initializeInjector();

    @Provides
    public ApiClientConfigurer apiClientConfigurer() {
        return new Junit4ApiConfigurer();
    }

    private static Injector initializeInjector() {
        return Injectors.createDefaultInjectorWithOverridingModules(new Junit4Module());
    }
}
