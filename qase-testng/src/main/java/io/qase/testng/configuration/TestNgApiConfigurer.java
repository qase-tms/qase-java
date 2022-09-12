package io.qase.testng.configuration;

import com.google.inject.Singleton;
import io.qase.api.config.apiclient.DefaultHeadersApiConfigurer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.testng.annotations.Test;

@Singleton
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class TestNgApiConfigurer extends DefaultHeadersApiConfigurer {

    private static final Class<?> TESTNG_FRAMEWORK_CLASS = Test.class;

    private static final String TESTNG_FRAMEWORK_NAME = "testng";

    @Override
    protected String getFrameworkName() {
        return TESTNG_FRAMEWORK_NAME;
    }

    @Override
    protected String getFrameworkVersion() {
        return getFrameworkVersionByClassOrUnknown(TESTNG_FRAMEWORK_CLASS);
    }
}
