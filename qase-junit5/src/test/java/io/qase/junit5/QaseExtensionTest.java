package io.qase.junit5;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.junit5.samples.Failed;
import io.qase.junit5.samples.FailedWithTime;
import io.qase.junit5.samples.Passed;
import io.qase.junit5.samples.PassedWithTime;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

public class QaseExtensionTest {
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
                .withRequestBody(equalToJson("{\n  " +
                        "\"case_id\": 123,\n  " +
                        "\"status\": \"passed\",\n  " +
                        "\"steps\": [],\n  " +
                        "\"time\": 0\n}")));
    }


    @Test
    public void passedWithTimeTest() {
        runTest(PassedWithTime.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"case_id\": 123,\n  " +
                        "\"status\": \"passed\",\n  " +
                        "\"steps\": [],\n  " +
                        "\"time\": 3\n}")));
    }

    @Test
    public void failedTest() {
        runTest(Failed.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n" +
                        "\"case_id\" : 321,\n" +
                        "\"status\" : \"failed\",\n" +
                        "\"time\" : 0,\n" +
                        "\"comment\" : \"java.lang.AssertionError: Error message\",\n" +
                        "\"steps\" : [ ]\n" +
                        "}")));
    }

    @Test
    public void failedWithTimeTest() {
        runTest(FailedWithTime.class);
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n" +
                        "\"case_id\" : 321,\n" +
                        "\"status\" : \"failed\",\n" +
                        "\"time\" : 2,\n" +
                        "\"comment\" : \"java.lang.AssertionError: Error message\",\n" +
                        "\"steps\" : [ ]\n" +
                        "}")));
    }

    private void runTest(Class<?> className) {
        LauncherDiscoveryRequest launcherDiscoveryRequest = LauncherDiscoveryRequestBuilder.request()
                .selectors(selectClass(className)).build();
        Launcher launcher = LauncherFactory.create();
        launcher.execute(launcherDiscoveryRequest, new QaseExtension());
    }
}
