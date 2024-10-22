//package io.qase.cucumber7.configuration;
//
//import com.google.inject.Singleton;
//import io.cucumber.java.After;
//import io.qase.config.DefaultHeadersApiConfigurer;
//import lombok.AccessLevel;
//import lombok.NoArgsConstructor;
//
//@Singleton
//@NoArgsConstructor(access = AccessLevel.PUBLIC)
//public class Cucumber7ApiConfigurer extends DefaultHeadersApiConfigurer {
//
//    private static final Class<?> CUCUMBER_FRAMEWORK_CLASS = After.class;
//
//    private static final String CUCUMBER_FRAMEWORK_NAME = "cucumber7";
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
