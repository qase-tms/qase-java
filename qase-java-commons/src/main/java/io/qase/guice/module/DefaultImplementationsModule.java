package io.qase.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.qase.config.ApiClientConfigurer;
import io.qase.config.DefaultHeadersApiConfigurer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DefaultImplementationsModule extends AbstractModule  {

    @Provides
    public ApiClientConfigurer apiClientConfigurer() {
        return DefaultHeadersApiConfigurer.createDefaultConfigurer();
    }
}
