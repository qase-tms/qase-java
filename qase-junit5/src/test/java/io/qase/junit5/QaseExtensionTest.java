package io.qase.junit5;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.api.QaseClient;
import io.qase.api.utils.TestUtils;
import io.qase.junit5.samples.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherConfig;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.qase.api.utils.TestUtils.useBulk;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

class QaseExtensionTest {
    static final WireMockServer wireMockServer = new WireMockServer(options().port(8088));

    @BeforeAll
    static void setUp() {
        configureFor(8088);
        wireMockServer.start();
        TestUtils.setupQaseTestEnvironmentVariables(wireMockServer.port());
    }

    @AfterEach
    public void resetRequests() {
        wireMockServer.resetRequests();
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
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
                        "  \"stacktrace\" : \"${json-unit.ignore}\"," +
                        "  \"comment\" : \"java.lang.AssertionError\",\n" +
                        "  \"steps\" : [ {\n" +
                        "    \"position\" : 1,\n" +
                        "    \"status\" : \"passed\",\n" +
                        "    \"action\" : \"success step\"\n" +
                        "  }, {\n" +
                        "    \"position\" : 2,\n" +
                        "    \"status\" : \"failed\",\n" +
                        "    \"attachments\" : \"${json-unit.ignore}\"," +
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
                        "    \"attachments\" : \"${json-unit.ignore}\"," +
                        "    \"action\" : \"failure step\"\n" +
                        "  } ]\n" +
                        "}")));
    }

    @Test
    void passedTest() {
        useBulk(false);
        runTest(Passed.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"case_id\" : 123,\n" +
                        "  \"case\" : { },\n" +
                        "  \"status\" : \"passed\",\n" +
                        "  \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "  \"defect\" : false\n" +
                        "}")));
    }

    @Test
    void passedWithTimeTest() {
        useBulk(false);
        runTest(PassedWithTime.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"case_id\" : 123,\n" +
                        "  \"case\" : { },\n" +
                        "  \"status\" : \"passed\",\n" +
                        "  \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "  \"defect\" : false\n" +
                        "}")));
    }

    @Test
    void failedTest() {
        useBulk(false);
        runTest(Failed.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"case_id\" : 321,\n" +
                        "  \"case\" : { },\n" +
                        "  \"status\" : \"failed\",\n" +
                        "  \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "  \"comment\" : \"java.lang.AssertionError: Error message\",\n" +
                        "  \"stacktrace\" : \"${json-unit.ignore}\"," +
                        "  \"defect\" : true\n" +
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
                        "  \"case_id\" : 321,\n" +
                        "  \"case\" : { },\n" +
                        "  \"status\" : \"failed\",\n" +
                        "  \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "  \"comment\" : \"java.lang.AssertionError: Error message\",\n" +
                        "  \"stacktrace\" : \"${json-unit.ignore}\"," +
                        "  \"defect\" : true\n" +
                        "}")));
    }

    private void runTest(Class<?> className) {
        QaseClient.setEnabled(true);
        LauncherDiscoveryRequest launcherDiscoveryRequest = LauncherDiscoveryRequestBuilder
                .request()
                .selectors(selectClass(className))
                .configurationParameter("junit.jupiter.execution.parallel.enabled", "true")
                .configurationParameter("junit.jupiter.execution.parallel.mode.default", "concurrent")
                .build();

        final LauncherConfig config = LauncherConfig.builder().addTestEngines()
                .enableTestExecutionListenerAutoRegistration(true)
                .build();

        Launcher launcher = LauncherFactory.create(config);
        launcher.execute(launcherDiscoveryRequest);
    }
}
