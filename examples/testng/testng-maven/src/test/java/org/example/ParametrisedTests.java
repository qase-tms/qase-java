package org.example;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ParametrisedTests {
    @DataProvider(name = "data-provider")
    public Object[][] dataProviderMethod() {
        return new Object[][] { { "data one" }, { "data two" }, { "data three" } };
    }

    @Test(dataProvider = "data-provider")
    @Parameters("data")
    public void testMethod_success(String data) {
        System.out.println("Data is: " + data);
    }

    @Test(dataProvider = "data-provider")
    @Parameters("data")
    public void testMethod_failed(String data) {
        System.out.println("Data is: " + data);
        throw new RuntimeException("Test failed");
    }
}
