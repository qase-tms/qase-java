package io.qase.junit4.configuration;

import com.google.inject.Singleton;
import io.qase.api.config.apiclient.DefaultHeadersApiConfigurer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.junit.Test;

@Singleton
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Junit4ApiConfigurer extends DefaultHeadersApiConfigurer {

    private static final Class<?> JUNIT_FRAMEWORK_CLASS = Test.class;

    private static final String JUNIT_FRAMEWORK_NAME = "junit4";

    @Override
    protected String getFrameworkName() {
        return JUNIT_FRAMEWORK_NAME;
    }

    @Override
    protected String getFrameworkVersion() {
        return getFrameworkVersionByClassOrUnknown(JUNIT_FRAMEWORK_CLASS);
    }
}
