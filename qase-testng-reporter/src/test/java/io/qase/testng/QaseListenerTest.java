package io.qase.testng;


import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.api.utils.TestUtils;
import io.qase.testng.samples.*;
import org.junit.jupiter.api.*;
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
import static io.qase.api.utils.TestUtils.useBulk;
import static io.qase.api.utils.TestUtils.useRunAutocomplete;

class QaseListenerTest {
    public static final WireMockServer wireMockServer = new WireMockServer(options().port(8088));
    public static final String RESULT_BULK_URL = "/v1/result/PRJ/777/bulk";
    public static final String RESULT_URL = "/v1/result/PRJ/777";
    public static final String RUN_COMPELTE_URL = "/v1/run/PRJ/777/complete";

    @BeforeAll
    static void setUp() {
        configureFor(8088);
        wireMockServer.start();
        TestUtils.setupQaseTestEnvironmentVariables(wireMockServer.port());
    }

    @BeforeEach
    public void resetRequests() {
        wireMockServer.resetRequests();
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void runAutocompleteFalse() {
        useRunAutocomplete(false);
        runTest(Passed.class);
        verify(exactly(0), postRequestedFor(urlPathEqualTo(RUN_COMPELTE_URL))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8")));
    }

    @Test
    void runAutocomplete() {
        useRunAutocomplete(true);
        runTest(Passed.class);
        verify(exactly(1), postRequestedFor(urlPathEqualTo(RUN_COMPELTE_URL))
                .withHeader("Token", equalTo("secret-token")));
    }

    @Test
    void passedBulkTest() {
        useRunAutocomplete(false);
        useBulk(true);
        runTest(Passed.class);
        verify(postRequestedFor(urlPathEqualTo(RESULT_BULK_URL))
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
    void passedWithStepsBulkTest() {
        useRunAutocomplete(false);
        useBulk(true);
        runTest(WithSteps.class);
        verify(postRequestedFor(urlPathEqualTo(RESULT_BULK_URL))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"results\" : [ {\n" +
                        "    \"case_id\" : 123,\n" +
                        "    \"status\" : \"failed\",\n" +
                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "    \"defect\" : true,\n" +
                        "    \"stacktrace\" : \"${json-unit.ignore}\"," +
                        "    \"comment\" : \"java.lang.AssertionError\",\n" +
                        "    \"steps\" : [ {\n" +
                        "      \"position\" : 1,\n" +
                        "      \"status\" : \"passed\",\n" +
                        "      \"action\" : \"success step\"\n" +
                        "    }, {\n" +
                        "      \"position\" : 2,\n" +
                        "      \"status\" : \"failed\",\n" +
                        "      \"attachments\" : \"${json-unit.ignore}\",\n" +
                        "      \"action\" : \"failure step\"\n" +
                        "    } ]\n" +
                        "  } ]\n" +
                        "}")));
    }

    @Test
    void passedWithTimeBulkTest() {
        useRunAutocomplete(false);
        useBulk(true);
        runTest(PassedWithTime.class);
        verify(postRequestedFor(urlPathEqualTo(RESULT_BULK_URL))
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
    void failedBulkTest() {
        useRunAutocomplete(false);
        useBulk(true);
        runTest(Failed.class);
        verify(postRequestedFor(urlPathEqualTo(RESULT_BULK_URL))
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
    void failedWithTimeBulkTest() {
        useRunAutocomplete(false);
        useBulk(true);
        runTest(FailedWithTime.class);
        verify(postRequestedFor(urlPathEqualTo(RESULT_BULK_URL))
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
    void allBulkTests() {
        useRunAutocomplete(false);
        useBulk(true);
        runTest(Arrays.asList(Passed.class, PassedWithTime.class, Failed.class, FailedWithTime.class,
                WithSteps.class, WithStepsSuccess.class));
        verify(postRequestedFor(urlPathEqualTo(RESULT_BULK_URL))
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
                        "    \"case_id\" : 123,\n" +
                        "    \"status\" : \"failed\",\n" +
                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "    \"defect\" : true,\n" +
                        "    \"stacktrace\" : \"${json-unit.ignore}\",\n" +
                        "    \"comment\" : \"java.lang.AssertionError\",\n" +
                        "    \"steps\" : [ {\n" +
                        "      \"position\" : 1,\n" +
                        "      \"status\" : \"passed\",\n" +
                        "      \"action\" : \"success step\"\n" +
                        "    }, {\n" +
                        "      \"position\" : 2,\n" +
                        "      \"status\" : \"failed\",\n" +
                        "      \"attachments\" : \"${json-unit.ignore}\",\n" +
                        "      \"action\" : \"failure step\"\n" +
                        "    } ]\n" +
                        "  }, {\n" +
                        "    \"case_id\" : 41332,\n" +
                        "    \"status\" : \"passed\",\n" +
                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "    \"defect\" : false,\n" +
                        "    \"steps\" : [ {\n" +
                        "      \"position\" : 1,\n" +
                        "      \"status\" : \"passed\",\n" +
                        "      \"action\" : \"success step\"\n" +
                        "    } ]\n" +
                        "  }, {\n" +
                        "    \"case_id\" : 321,\n" +
                        "    \"status\" : \"failed\",\n" +
                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "    \"defect\" : true,\n" +
                        "    \"stacktrace\" : \"${json-unit.ignore}\",\n" +
                        "    \"comment\" : \"java.lang.AssertionError: Error message\"\n" +
                        "  } ]\n" +
                        "}", true, false)));
    }

    @Test
    void passedTest() {
        useRunAutocomplete(false);
        useBulk(false);
        runTest(Passed.class);
        verify(postRequestedFor(urlPathEqualTo(RESULT_URL))
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
    void withStepsTest() {
        useRunAutocomplete(false);
        useBulk(false);
        runTest(WithSteps.class);
        verify(postRequestedFor(urlPathEqualTo(RESULT_URL))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"case_id\" : 123,\n" +
                        "  \"status\" : \"failed\",\n" +
                        "  \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "  \"defect\" : true,\n" +
                        "  \"stacktrace\" : \"${json-unit.ignore}\",\n" +
                        "  \"comment\" : \"java.lang.AssertionError\",\n" +
                        "  \"steps\" : [ {\n" +
                        "    \"position\" : 1,\n" +
                        "    \"status\" : \"passed\",\n" +
                        "    \"action\" : \"success step\"\n" +
                        "  }, {\n" +
                        "    \"position\" : 2,\n" +
                        "    \"status\" : \"failed\",\n" +
                        "    \"action\" : \"failure step\",\n" +
                        "    \"attachments\" : [ \"${json-unit.ignore}\" ]\n" +
                        "  } ]\n" +
                        "}")));
    }

    @Test
    void newCaseWithStepsTest() {
        useRunAutocomplete(false);
        useBulk(false);
        runTest(NewCase.class);
        verify(postRequestedFor(urlPathEqualTo(RESULT_URL))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"case\" : {\n" +
                        "    \"title\" : \"Case Title\"\n" +
                        "  },\n" +
                        "  \"status\" : \"failed\",\n" +
                        "  \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "  \"defect\" : true,\n" +
                        "  \"stacktrace\" : \"${json-unit.ignore}\",\n" +
                        "  \"comment\" : \"java.lang.AssertionError\",\n" +
                        "  \"steps\" : [ {\n" +
                        "    \"position\" : 1,\n" +
                        "    \"status\" : \"passed\",\n" +
                        "    \"action\" : \"success step\"\n" +
                        "  }, {\n" +
                        "    \"position\" : 2,\n" +
                        "    \"status\" : \"failed\",\n" +
                        "    \"attachments\" : [ \"${json-unit.ignore}\" ],\n" +
                        "    \"action\" : \"failure step\"\n" +
                        "  } ]\n" +
                        "}")));
    }

    @Test
    void failedTest() {
        useRunAutocomplete(false);
        useBulk(false);
        runTest(Failed.class);
        verify(postRequestedFor(urlPathEqualTo(RESULT_URL))
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
