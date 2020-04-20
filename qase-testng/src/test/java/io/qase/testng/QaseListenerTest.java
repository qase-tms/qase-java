package io.qase.testng;


import org.testng.annotations.Test;

public class QaseListenerTest {

    @Test
    public void onTestStart() {
//        TestNG testNG = new TestNG(true);
////        QaseListener spy = spy(new QaseListener());
////        testNG.addListener((ITestNGListener) spy);
//        XmlSuite suite = new XmlSuite();
//        XmlTest test = new XmlTest();
//        XmlClass aClass = new XmlClass();
//        aClass.setClass(Tests.class);
//        test.setClasses(Collections.singletonList(aClass));
//        suite.setTests(Collections.singletonList(test));
//        testNG.setXmlSuites(Collections.singletonList(suite));
//        testNG.run();
    }

    @Test
    public void onTestSuccess() {
    }

    @Test
    public void onTestFailure() {
    }

    @Test
    public void onTestSkipped() {
    }

    @Test
    public void onTestFailedButWithinSuccessPercentage() {
    }

    @Test
    public void onStart() {
    }

    @Test
    public void onFinish() {
    }

    public static class Tests {
        @Test
        public void success() {

        }

//        @Test
//        public void failed() {
//            Assert.assertTrue(false);
//        }
    }
}
