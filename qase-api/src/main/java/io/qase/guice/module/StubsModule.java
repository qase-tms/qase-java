package io.qase.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import io.qase.api.config.apiclient.ApiClientConfigurer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StubsModule extends AbstractModule  {

    @Provides
    public ApiClientConfigurer apiClientConfigurer() {
        return apiClient -> log.warn("This configurer supposes doing nothing with passed api client: " + apiClient);
    }
}
