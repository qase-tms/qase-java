package org.example;

import io.qase.commons.annotation.QaseId;
import io.qase.commons.annotation.QaseTitle;
import io.qase.commons.annotation.QaseFields;
import io.qase.commons.models.annotation.Field;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ParameterizedTests {

    @DataProvider(name = "login-credentials")
    public Object[][] loginCredentials() {
        return new Object[][]{
                {"admin", "secret"},
                {"user", "password123"},
                {"guest", "guest"}
        };
    }

    @DataProvider(name = "browsers")
    public Object[][] browsers() {
        return new Object[][]{
                {"Chrome"},
                {"Firefox"},
                {"Safari"},
                {"Edge"}
        };
    }

    @DataProvider(name = "api-endpoints")
    public Object[][] apiEndpoints() {
        return new Object[][]{
                {"GET", "/api/users", 200},
                {"POST", "/api/users", 201},
                {"DELETE", "/api/users/1", 204},
                {"GET", "/api/unknown", 404}
        };
    }

    @Test(dataProvider = "login-credentials")
    @QaseTitle("Login with credentials from data provider")
    public void testWithMultipleParams(String username, String password) {
        System.out.println("Login with " + username + " / " + password);
    }

    @Test(dataProvider = "browsers")
    @QaseId(20)
    @QaseTitle("Browser compatibility test")
    public void testWithSingleParam(String browser) {
        System.out.println("Testing with browser: " + browser);
    }

    @Test(dataProvider = "api-endpoints")
    @QaseTitle("API endpoint response codes")
    @QaseFields(value = {
            @Field(name = "layer", value = "api"),
    })
    public void testWithMixedParams(String method, String endpoint, int expectedStatus) {
        System.out.println(method + " " + endpoint + " -> " + expectedStatus);
    }
}
