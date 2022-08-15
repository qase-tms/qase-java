package io.qase.cucumber3;


import com.github.tomakehurst.wiremock.WireMockServer;
import cucumber.api.cli.Main;
import io.qase.api.utils.TestUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.qase.api.utils.TestUtils.useBulk;

class QaseEventListenerTests {
    static final WireMockServer wireMockServer = new WireMockServer(options().port(8088));

    @BeforeAll
    static void setUp() {
        configureFor(8088);
        wireMockServer.start();
        TestUtils.setupQaseTestEnvironmentVariables(wireMockServer.port());
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
    void bulk() {
        useBulk(true);
        String[] args = new String[]{
                "-g", "io.qase.cucumber3",
                "--add-plugin", io.qase.cucumber3.QaseEventListener.class.getCanonicalName(),
                "classpath:features/"
        };
        Main.run(args, Thread.currentThread().getContextClassLoader());

        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777/bulk"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"results\" : [ {\n" +
                        "    \"case_id\" : 123,\n" +
                        "    \"status\" : \"failed\",\n" +
                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "    \"defect\" : true,\n" +
                        "    \"stacktrace\" : \"${json-unit.ignore}\",\n" +
                        "    \"comment\" : \"java.lang.AssertionError\"\n" +
                        "  }, {\n" +
                        "    \"case_id\" : 123,\n" +
                        "    \"status\" : \"failed\",\n" +
                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "    \"defect\" : true,\n" +
                        "    \"stacktrace\" : \"${json-unit.ignore}\",\n" +
                        "    \"comment\" : \"java.lang.AssertionError\"\n" +
                        "  }, {\n" +
                        "    \"case_id\" : 123,\n" +
                        "    \"status\" : \"passed\",\n" +
                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "    \"defect\" : false\n" +
                        "  }, {\n" +
                        "    \"case_id\" : 123,\n" +
                        "    \"status\" : \"passed\",\n" +
                        "    \"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "    \"defect\" : false\n" +
                        "  } ]\n" +
                        "}", true, false)));
    }

    @Test
    void success() {
        useBulk(false);
        String[] args = new String[]{
                "-g", "io.qase.cucumber3",
                "--add-plugin", io.qase.cucumber3.QaseEventListener.class.getCanonicalName(),
                "classpath:features/success.feature"
        };
        Main.run(args, Thread.currentThread().getContextClassLoader());

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
    void successWithTime() {
        useBulk(false);
        String[] args = new String[]{
                "-g", "io.qase.cucumber3",
                "--add-plugin", io.qase.cucumber3.QaseEventListener.class.getCanonicalName(),
                "classpath:features/success_with_time.feature"
        };
        Main.run(args, Thread.currentThread().getContextClassLoader());

        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "\"case_id\" : 123,\n" +
                        "\"status\" : \"passed\",\n" +
                        "\"defect\" : false,\n" +
                        "\"time_ms\" : \"${json-unit.ignore}\"\n" +
                        "}")));
    }

    @Test
    void failed() {
        useBulk(false);
        String[] args = new String[]{
                "-g", "io.qase.cucumber3",
                "--add-plugin", io.qase.cucumber3.QaseEventListener.class.getCanonicalName(),
                "classpath:features/failed.feature"
        };
        Main.run(args, Thread.currentThread().getContextClassLoader());

        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "\"case_id\" : 123,\n" +
                        "\"status\" : \"failed\",\n" +
                        "\"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "\"comment\" : \"java.lang.AssertionError\",\n" +
                        "\"stacktrace\" : \"${json-unit.ignore}\"," +
                        "\"defect\" : true\n" +
                        "}")));
    }

    @Test
    void failedWithTime() {
        useBulk(false);
        String[] args = new String[]{
                "-g", "io.qase.cucumber3",
                "--add-plugin", io.qase.cucumber3.QaseEventListener.class.getCanonicalName(),
                "classpath:features/failed_with_time.feature"
        };
        Main.run(args, Thread.currentThread().getContextClassLoader());

        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "\"case_id\" : 123,\n" +
                        "\"status\" : \"failed\",\n" +
                        "\"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "\"comment\" : \"java.lang.AssertionError\",\n" +
                        "\"stacktrace\" : \"${json-unit.ignore}\"," +
                        "\"defect\" : true\n" +
                        "}")));
    }
}
