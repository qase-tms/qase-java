package io.qase.testng;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;

public class QaseDataProvider {
    @DataProvider
    public Object[][] dataProvider(Method method) {
        CaseId annotation = method.getAnnotation(CaseId.class);
        return new Object[][]{
                {annotation.value(), annotation.hasDataSet()}
        };
    }
}
