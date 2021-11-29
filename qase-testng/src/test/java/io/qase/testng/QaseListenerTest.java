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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.qase.api.utils.IntegrationUtils.BULK_KEY;

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
    public void passedBulkTest() {
        runTest(Passed.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777/bulk"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"results\" : [ {\n" +
                        "    \"case_id\" : 123,\n" +
                        "    \"status\" : \"passed\",\n" +
                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "    \"defect\" : false\n" +
                        "  } ]\n" +
                        "}")));
    }

    @Test
    public void passedWithTimeBulkTest() {
        runTest(PassedWithTime.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777/bulk"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"results\" : [ {\n" +
                        "    \"case_id\" : 123,\n" +
                        "    \"status\" : \"passed\",\n" +
                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "    \"defect\" : false\n" +
                        "  } ]\n" +
                        "}")));
    }

    @Test
    public void failedBulkTest() {
        runTest(Failed.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777/bulk"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"results\" : [ {\n" +
                        "    \"case_id\" : 321,\n" +
                        "    \"status\" : \"failed\",\n" +
                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "    \"defect\" : true,\n" +
                        "    \"stacktrace\" : \"${json-unit.ignore}\"," +
                        "    \"comment\" : \"java.lang.AssertionError: Error message\"\n" +
                        "  } ]\n" +
                        "}")));
    }

    @Test
    public void failedWithTimeBulkTest() {
        runTest(FailedWithTime.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777/bulk"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"results\" : [ {\n" +
                        "    \"case_id\" : 321,\n" +
                        "    \"status\" : \"failed\",\n" +
                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "    \"defect\" : true,\n" +
                        "    \"stacktrace\" : \"${json-unit.ignore}\"," +
                        "    \"comment\" : \"java.lang.AssertionError: Error message\"\n" +
                        "  } ]\n" +
                        "}")));
    }

    @Test
    public void allBulkTests() {
        runTest(Arrays.asList(Passed.class, PassedWithTime.class, Failed.class, FailedWithTime.class));
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777/bulk"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"results\" : [ {\n" +
                        "    \"case_id\" : 123,\n" +
                        "    \"status\" : \"passed\",\n" +
                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "    \"defect\" : false\n" +
                        "  }, {\n" +
                        "    \"case_id\" : 123,\n" +
                        "    \"status\" : \"passed\",\n" +
                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "    \"defect\" : false\n" +
                        "  }, {\n" +
                        "    \"case_id\" : 321,\n" +
                        "    \"status\" : \"failed\",\n" +
                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "    \"defect\" : true,\n" +
                        "    \"stacktrace\" : \"${json-unit.ignore}\",\n" +
                        "    \"comment\" : \"java.lang.AssertionError: Error message\"\n" +
                        "  }, {\n" +
                        "    \"case_id\" : 321,\n" +
                        "    \"status\" : \"failed\",\n" +
                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "    \"defect\" : true,\n" +
                        "    \"stacktrace\" : \"${json-unit.ignore}\",\n" +
                        "    \"comment\" : \"java.lang.AssertionError: Error message\"\n" +
                        "  } ]\n" +
                        "}")));
    }

    @Test
    public void passedTest() {
        System.setProperty(BULK_KEY, "false");
        runTest(Passed.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"case_id\" : 123,\n" +
                        "  \"status\" : \"passed\",\n" +
                        "  \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "  \"defect\" : false\n" +
                        "}")));
    }

    @Test
    public void failedTest() {
        System.setProperty(BULK_KEY, "false");
        runTest(Failed.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "    \"case_id\" : 321,\n" +
                        "    \"status\" : \"failed\",\n" +
                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "    \"defect\" : true,\n" +
                        "    \"stacktrace\" : \"${json-unit.ignore}\"," +
                        "    \"comment\" : \"java.lang.AssertionError: Error message\"\n" +
                        "}")));
    }

    private void runTest(Class<?> className) {
        runTest(Collections.singletonList(className));
    }

    private void runTest(List<Class<?>> classesName) {
        TestNG testNG = new TestNG(false);
        XmlSuite suite = new XmlSuite();
        XmlTest test = new XmlTest(suite);
        test.setParallel(XmlSuite.ParallelMode.METHODS);
        List<XmlClass> xmlClasses = classesName.stream().map(XmlClass::new).collect(Collectors.toList());
        test.setXmlClasses(xmlClasses);
        suite.setTests(Collections.singletonList(test));
        testNG.setXmlSuites(Collections.singletonList(suite));
        testNG.run();
    }
}
