package io.qase.junit;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.junit.samples.*;
import io.qase.junit4.QaseListener;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.JUnitCore;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class QaseListenerTest {
    static final WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
    static int port;

    @BeforeAll
    static void setUp() {
        wireMockServer.start();
        port = wireMockServer.port();
        configureFor(port);
        System.setProperty("qase.enable", "true");
        System.setProperty("qase.project.code", "PRJ");
        System.setProperty("qase.run.id", "777");
        System.setProperty("qase.api.token", "secret-token");
        System.setProperty("qase.url", "http://localhost:" + port + "/v1");
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
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"case_id\": 123,\n  " +
                        "\"status\": \"passed\",\n  " +
                        "\"defect\" : false,\n" +
                        "\"time_ms\": \"${json-unit.ignore}\"\n}")));
    }


    @Test
    public void passedWithTimeTest() {
        runTest(PassedWithTime.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"case_id\": 123,\n  " +
                        "\"status\": \"passed\",\n  " +
                        "\"defect\" : false,\n" +
                        "\"time_ms\": \"${json-unit.ignore}\"\n}")));
    }

    @Test
    public void failedTest() {
        runTest(Failed.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "\"case_id\" : 321,\n" +
                        "\"status\" : \"failed\",\n" +
                        "\"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "\"comment\" : \"java.lang.AssertionError: Error message\",\n" +
                        "\"stacktrace\" : \"${json-unit.ignore}\"," +
                        "\"defect\" : true\n" +
                        "}")));
    }

    @Test
    public void failedWithTimeTest() {
        runTest(FailedWithTime.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "\"case_id\" : 321,\n" +
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
