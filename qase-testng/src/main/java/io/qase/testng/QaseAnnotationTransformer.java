package io.qase.testng;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class QaseAnnotationTransformer implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        CaseId caseId = testMethod.getAnnotation(CaseId.class);
        if (caseId != null && caseId.hasDataSet()) {
            annotation.setDataProviderClass(QaseDataProvider.class);
            annotation.setDataProvider("dataProvider");
        }
    }
}
