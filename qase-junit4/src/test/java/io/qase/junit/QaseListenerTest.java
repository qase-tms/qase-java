package io.qase.junit;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.api.utils.TestUtils;
import io.qase.junit.samples.*;
import io.qase.junit4.QaseListener;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.JUnitCore;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.qase.api.utils.TestUtils.useBulk;

class QaseListenerTest {
    static final WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
    static int port;

    @BeforeAll
    static void setUp() {
        wireMockServer.start();
        port = wireMockServer.port();
        configureFor(port);
        TestUtils.setupQaseTestEnvironmentVariables(port);
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    @AfterEach
    void resetRequests() {
        wireMockServer.resetRequests();
    }

    @Test
    void bulkWithStepsTest() {
        useBulk(true);
        runTest(WithSteps.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777/bulk"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"results\" : [ {\n" +
                        "    \"case_id\" : 123,\n" +
                        "    \"case\" : { },\n" +
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
                        "  } ]\n" +
                        "}")));
    }

    @Test
    void bulkMultipleTest() {
        useBulk(true);
        runTest(Multiple.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777/bulk"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"results\" : [ {\n" +
                        "    \"case_id\" : 123,\n" +
                        "    \"case\" : { },\n" +
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
                        "    \"case_id\" : 321,\n" +
                        "    \"case\" : { },\n" +
                        "    \"status\" : \"failed\",\n" +
                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "    \"defect\" : true,\n" +
                        "    \"stacktrace\" : \"${json-unit.ignore}\",\n" +
                        "    \"comment\" : \"java.lang.AssertionError: Error message\"\n" +
                        "  }, {\n" +
                        "    \"case_id\" : 456,\n" +
                        "    \"case\" : { },\n" +
                        "    \"status\" : \"passed\",\n" +
                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "    \"defect\" : false,\n" +
                        "    \"steps\" : [ {\n" +
                        "      \"position\" : 1,\n" +
                        "      \"status\" : \"passed\",\n" +
                        "      \"action\" : \"success step\"\n" +
                        "    } ]\n" +
                        "  } ]\n" +
                        "}", true, false)));
    }

    @Test
    void passedTest() {
        useBulk(false);
        runTest(Passed.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"case_id\": 123,\n  " +
                        "\"case\" : { },\n" +
                        "\"status\": \"passed\",\n  " +
                        "\"defect\" : false,\n" +
                        "\"time_ms\": \"${json-unit.ignore}\"\n}")));
    }

    @Test
    void withStepsTest() {
        useBulk(false);
        runTest(WithSteps.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"case_id\" : 123,\n" +
                        "  \"case\" : { },\n" +
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
                        "    \"attachments\" : \"${json-unit.ignore}\",\n" +
                        "    \"action\" : \"failure step\"\n" +
                        "  } ]\n" +
                        "}")));
    }

    @Test
    void newCaseWithStepsTest() {
        useBulk(false);
        runTest(NewCase.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
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
                        "    \"attachments\" : \"${json-unit.ignore}\",\n" +
                        "    \"action\" : \"failure step\"\n" +
                        "  } ]\n" +
                        "}")));
    }

    @Test
    void passedWithTimeTest() {
        useBulk(false);
        runTest(PassedWithTime.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"case_id\": 123,\n  " +
                        " \"case\" : { },\n" +
                        "\"status\": \"passed\",\n  " +
                        "\"defect\" : false,\n" +
                        "\"time_ms\": \"${json-unit.ignore}\"\n}")));
    }

    @Test
    void failedTest() {
        useBulk(false);
        runTest(Failed.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "\"case_id\" : 321,\n" +
                        " \"case\" : { },\n" +
                        "\"status\" : \"failed\",\n" +
                        "\"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "\"comment\" : \"java.lang.AssertionError: Error message\",\n" +
                        "\"stacktrace\" : \"${json-unit.ignore}\"," +
                        "\"defect\" : true\n" +
                        "}")));
    }

    @Test
    void failedWithTimeTest() {
        useBulk(false);
        runTest(FailedWithTime.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "\"case_id\" : 321,\n" +
                        "\"case\" : { },\n" +
                        "\"status\" : \"failed\",\n" +
                        "\"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "\"comment\" : \"java.lang.AssertionError: Error message\",\n" +
                        "\"stacktrace\" : \"${json-unit.ignore}\"," +
                        "\"defect\" : true\n" +
                        "}")));
    }

    private void runTest(Class<?> className) {
        JUnitCore jUnitCore = new JUnitCore();
        jUnitCore.addListener(new QaseListener());
        jUnitCore.run(className);
    }
}
