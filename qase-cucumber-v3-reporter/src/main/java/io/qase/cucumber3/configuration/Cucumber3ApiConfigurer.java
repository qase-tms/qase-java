//package io.qase.cucumber3.configuration;
//
//import com.google.inject.Singleton;
//import cucumber.api.java.After;
//import io.qase.config.DefaultHeadersApiConfigurer;
//import lombok.AccessLevel;
//import lombok.NoArgsConstructor;
//
//@Singleton
//@NoArgsConstructor(access = AccessLevel.PUBLIC)
//public class Cucumber3ApiConfigurer extends DefaultHeadersApiConfigurer {
//
//    private static final Class<?> CUCUMBER_FRAMEWORK_CLASS = After.class;
//
//    private static final String CUCUMBER_FRAMEWORK_NAME = "cucumber3";
//
//    @Override
//    protected String getFrameworkName() {
//        return CUCUMBER_FRAMEWORK_NAME;
//    }
//
//    @Override
//    protected String getFrameworkVersion() {
//        return getFrameworkVersionByClassOrUnknown(CUCUMBER_FRAMEWORK_CLASS);
//    }
//}
