package io.qase.cucumber7.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provides;
import io.qase.api.config.apiclient.ApiClientConfigurer;
import io.qase.cucumber7.configuration.Cucumber7ApiConfigurer;
import io.qase.guice.Injectors;
import lombok.Getter;

public class Cucumber7Module extends AbstractModule {

    @Getter
    private static final Injector injector = initializeInjector();

    @Provides
    public ApiClientConfigurer apiClientConfigurer() {
        return new Cucumber7ApiConfigurer();
    }

    private static Injector initializeInjector() {
        return Injectors.createDefaultInjectorWithOverridingModules(new Cucumber7Module());
    }
}
