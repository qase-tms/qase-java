package io.qase.cucumber5;


import com.github.tomakehurst.wiremock.WireMockServer;
import io.cucumber.core.cli.Main;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.qase.api.utils.TestUtils.useBulk;

public class QaseEventListenerTests {
    static final WireMockServer wireMockServer = new WireMockServer(options().port(8088));

    @BeforeAll
    static void setUp() {
        configureFor(8088);
        wireMockServer.start();
        System.setProperty("QASE_ENABLE", "true");
        System.setProperty("QASE_PROJECT_CODE", "PRJ");
        System.setProperty("QASE_RUN_ID", "777");
        System.setProperty("QASE_API_TOKEN", "secret-token");
        System.setProperty("QASE_URL", "http://localhost:8088/v1");
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    public void bulk() {
        useBulk(true);
        String[] args = new String[]{
                "-g", "io.qase.cucumber5",
                "--add-plugin", "io.qase.cucumber5.QaseEventListener",
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
                        "}")));
    }

    @Test
    public void success() {
        useBulk(false);
        String[] args = new String[]{
                "-g", "io.qase.cucumber5",
                "--add-plugin", "io.qase.cucumber5.QaseEventListener",
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
    public void successWithTime() {
        useBulk(false);
        String[] args = new String[]{
                "-g", "io.qase.cucumber5",
                "--add-plugin", "io.qase.cucumber5.QaseEventListener",
                "classpath:features/success_with_time.feature"
        };
        Main.run(args, Thread.currentThread().getContextClassLoader());

        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/777"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "\"case_id\" : 123,\n" +
                        "\"status\" : \"passed\",\n" +
                        "\"time_ms\" : \"${json-unit.ignore}\",\n" +
                        "\"defect\" : false\n" +
                        "}")));
    }

    @Test
    public void failed() {
        useBulk(false);
        String[] args = new String[]{
                "-g", "io.qase.cucumber5",
                "--add-plugin", "io.qase.cucumber5.QaseEventListener",
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
    public void failedWithTime() {
        useBulk(false);
        String[] args = new String[]{
                "-g", "io.qase.cucumber5",
                "--add-plugin", "io.qase.cucumber5.QaseEventListener",
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
