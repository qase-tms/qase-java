package io.qase.testng;


import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.testng.samples.Failed;
import io.qase.testng.samples.FailedWithTime;
import io.qase.testng.samples.Passed;
import io.qase.testng.samples.PassedWithTime;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.Collections;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class QaseListenerTest {
    static final WireMockServer wireMockServer = new WireMockServer(options().port(8088));

    @BeforeAll
    static void setUp() {
        configureFor(8088);
        wireMockServer.start();
        System.setProperty("qase.enable", "true");
        System.setProperty("qase.project.code", "PRJ");
        System.setProperty("qase.run.id", "777");
        System.setProperty("qase.api.token", "secret-token");
        System.setProperty("qase.url", "http://localhost:8088/v1");
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void passedTest() {
        runTest(Passed.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"case_id\" : 123,\n" +
                        "  \"status\" : \"passed\",\n" +
                        "  \"time\" : 0,\n" +
                        "  \"defect\" : false,\n" +
                        "  \"steps\" : [ ]\n" +
                        "}")));
    }

    @Test
    public void passedWithTimeTest() {
        runTest(PassedWithTime.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"case_id\" : 123,\n" +
                        "  \"status\" : \"passed\",\n" +
                        "  \"time\" : 3,\n" +
                        "  \"defect\" : false,\n" +
                        "  \"steps\" : [ ]\n" +
                        "}")));
    }

    @Test
    public void failedTest() {
        runTest(Failed.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"case_id\" : 321,\n" +
                        "  \"status\" : \"failed\",\n" +
                        "  \"time\" : 0,\n" +
                        "  \"comment\" : \"java.lang.AssertionError: Error message\",\n" +
                        "  \"stacktrace\" : \"${json-unit.ignore}\"," +
                        "  \"defect\" : true,\n" +
                        "  \"steps\" : [ ]\n" +
                        "}")));
    }

    @Test
    public void failedWithTimeTest() {
        runTest(FailedWithTime.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"case_id\" : 321,\n" +
                        "  \"status\" : \"failed\",\n" +
                        "  \"time\" : 2,\n" +
                        "  \"comment\" : \"java.lang.AssertionError: Error message\",\n" +
                        "  \"stacktrace\" : \"${json-unit.ignore}\"," +
                        "  \"defect\" : true,\n" +
                        "  \"steps\" : [ ]\n" +
                        "}")));
    }

    private void runTest(Class<?> className) {
        TestNG testNG = new TestNG(false);
        XmlSuite suite = new XmlSuite();
        XmlTest test = new XmlTest(suite);
        XmlClass aClass = new XmlClass();
        aClass.setClass(className);
        test.setXmlClasses(Collections.singletonList(aClass));
        suite.setTests(Collections.singletonList(test));
        testNG.setXmlSuites(Collections.singletonList(suite));
        testNG.run();
    }
}
