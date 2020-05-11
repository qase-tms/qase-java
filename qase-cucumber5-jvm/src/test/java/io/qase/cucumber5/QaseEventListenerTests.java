package io.qase.cucumber5;


import com.github.tomakehurst.wiremock.WireMockServer;
import io.cucumber.core.cli.Main;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class QaseEventListenerTests {
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
    public void success() {
        String[] args = new String[]{
                "-g", "io.qase.cucumber5",
                "--add-plugin", "io.qase.cucumber5.QaseEventListener",
                "classpath:features/success.feature"
        };
        Main.run(args, Thread.currentThread().getContextClassLoader());

        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"case_id\": 123,\n  " +
                        "\"status\": \"passed\",\n  " +
                        "\"defect\" : false,\n" +
                        "\"steps\": [],\n  " +
                        "\"time\": 0\n}")));
    }

    @Test
    public void successWithTime() {
        String[] args = new String[]{
                "-g", "io.qase.cucumber5",
                "--add-plugin", "io.qase.cucumber5.QaseEventListener",
                "classpath:features/success_with_time.feature"
        };
        Main.run(args, Thread.currentThread().getContextClassLoader());

        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n" +
                        "\"case_id\" : 123,\n" +
                        "\"status\" : \"passed\",\n" +
                        "\"time\" : 5,\n" +
                        "\"defect\" : false,\n" +
                        "\"steps\" : [ ]\n" +
                        "}")));
    }

    @Test
    public void failed() {
        String[] args = new String[]{
                "-g", "io.qase.cucumber5",
                "--add-plugin", "io.qase.cucumber5.QaseEventListener",
                "classpath:features/failed.feature"
        };
        Main.run(args, Thread.currentThread().getContextClassLoader());

        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n" +
                        "\"case_id\" : 123,\n" +
                        "\"status\" : \"failed\",\n" +
                        "\"time\" : 0,\n" +
                        "\"comment\" : \"java.lang.AssertionError\",\n" +
                        "\"stacktrace\" : \"${json-unit.ignore}\"," +
                        "\"defect\" : true,\n" +
                        "\"steps\" : [ ]\n" +
                        "}")));
    }

    @Test
    public void failedWithTime() {
        String[] args = new String[]{
                "-g", "io.qase.cucumber5",
                "--add-plugin", "io.qase.cucumber5.QaseEventListener",
                "classpath:features/failed_with_time.feature"
        };
        Main.run(args, Thread.currentThread().getContextClassLoader());

        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n" +
                        "\"case_id\" : 123,\n" +
                        "\"status\" : \"failed\",\n" +
                        "\"time\" : 3,\n" +
                        "\"comment\" : \"java.lang.AssertionError\",\n" +
                        "\"stacktrace\" : \"${json-unit.ignore}\"," +
                        "\"defect\" : true,\n" +
                        "\"steps\" : [ ]\n" +
                        "}")));
    }
}
