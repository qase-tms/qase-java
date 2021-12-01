package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.api.exceptions.QaseException;
import io.qase.client.ApiClient;
import io.qase.client.api.ResultsApi;
import io.qase.client.model.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class TestRunResultServiceTest {
    static final WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
    static final ApiClient qaseApi = new ApiClient();
    static final ResultsApi resultsApi = new ResultsApi(qaseApi);
    static int port;

    @BeforeAll
    static void setUp() {
        wireMockServer.start();
        port = wireMockServer.port();
        configureFor(port);
        qaseApi.setBasePath("http://localhost:" + port + "/v1");
        qaseApi.setApiKey("secret-token");
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void getAll() {
        try {
            resultsApi.getResults("PRJ", 100, 0, null);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/result/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0")));
    }

    @Test
    void getAllWithParamsAndFilter() {
        String timeFormat = "yyyy-MM-dd HH:mm:ss";

        LocalDateTime from = LocalDateTime.now().minusDays(1);
        LocalDateTime to = LocalDateTime.now();
        String fromString = DateTimeFormatter.ofPattern(timeFormat).format(from);
        String toString = DateTimeFormatter.ofPattern(timeFormat).format(to);
        try {
            resultsApi.getResults("PRJ", 33, 3,
                    new Filters4()
                            .caseId("1")
                            .member("2")
                            .run("3")
                            .status("in_progress")
                            .fromEndTime(fromString)
                            .toEndTime(toString));
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/result/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("33"))
                .withQueryParam("offset", equalTo("3"))
                .withQueryParam("filters%5Bstatus%5D", equalTo("in_progress"))
                .withQueryParam("filters%5Bmember%5D", equalTo("2"))
                .withQueryParam("filters%5Brun%5D", equalTo("3"))
                .withQueryParam("filters%5Bcase_id%5D", equalTo("1"))
                .withQueryParam("filters%5Bfrom_end_time%5D", equalTo(fromString))
                .withQueryParam("filters%5Bto_end_time%5D", equalTo(toString)));
    }

    @Test
    void get() {
        try {
            resultsApi.getResult("PRJ", "6efce6e4f9de887a2ee863e8197cb74ab626a273");
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/result/PRJ/6efce6e4f9de887a2ee863e8197cb74ab626a273"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @Test
    void create() {
        try {
            resultsApi.createResult("PRJ", "2",
                    new ResultCreate()
                            .caseId(1L)
                            .status(ResultCreate.StatusEnum.PASSED));
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/2"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"case_id\": 1,\n  " +
                        "\"status\": \"passed\"\n  }")));
    }

    @Test
    void createWithParams() {
        try {
            resultsApi.createResult("PRJ", "2",
                    new ResultCreate()
                            .caseId(1L)
                            .status(ResultCreate.StatusEnum.PASSED)
                            .comment("Failed via API")
                            .defect(true)
                            .time(120L)
                            .steps(
                                    Arrays.asList(
                                            new ResultCreateSteps()
                                                    .position(1)
                                                    .status(ResultCreateSteps.StatusEnum.PASSED),
                                            new ResultCreateSteps()
                                                    .position(2)
                                                    .status(ResultCreateSteps.StatusEnum.FAILED))
                            ));
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/2"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"case_id\": 1,\n  " +
                        "\"comment\": \"Failed via API\",\n  " +
                        "\"defect\": true,\n  " +
                        "\"status\": \"passed\",\n  " +
                        "\"steps\": [\n    " +
                        "{\n      \"position\": 1,\n      \"status\": \"passed\"\n    },\n    " +
                        "{\n      \"position\": 2,\n      \"status\": \"failed\"\n    }\n  ],\n  " +
                        "\"time\": 120\n}")));
    }

    @Test
    void createWithStacktraceParams() {
        try {
            resultsApi.createResult("PRJ", "2",
                    new ResultCreate()
                            .caseId(1L)
                            .status(ResultCreate.StatusEnum.PASSED)
                            .comment("Failed via API")
                            .stacktrace("Exception Stacktrace")
                            .defect(true)
                            .time(120L)
                            .steps(
                                    Arrays.asList(
                                            new ResultCreateSteps()
                                                    .position(1)
                                                    .status(ResultCreateSteps.StatusEnum.PASSED),
                                            new ResultCreateSteps()
                                                    .position(2)
                                                    .status(ResultCreateSteps.StatusEnum.FAILED))
                            ));
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/2"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "\"case_id\" : 1,\n" +
                        "\"status\" : \"passed\",\n" +
                        "\"time\" : 120,\n" +
                        "\"comment\" : \"Failed via API\",\n" +
                        "\"stacktrace\" : \"Exception Stacktrace\",\n" +
                        "\"defect\" : true,\n" +
                        "\"steps\" : [ {\n" +
                        "  \"position\" : 1,\n" +
                        "  \"status\" : \"passed\"\n" +
                        "}, {\n" +
                        "  \"position\" : 2,\n" +
                        "  \"status\" : \"failed\"\n" +
                        "} ]\n" +
                        "}")));
    }

    @Test
    void update() {
        try {
            resultsApi.updateResult("PRJ", 1, "2898ba7f3b4d857cec8bee4a852cdc85f8b33132",
                    new ResultUpdate().status(ResultUpdate.StatusEnum.FAILED));
        } catch (QaseException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/result/PRJ/1/2898ba7f3b4d857cec8bee4a852cdc85f8b33132"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"status\": \"failed\"\n  " +
                        "\n}")));
    }

    @Test
    void updateWithTimeSpent() {
        try {
            resultsApi.updateResult("PRJ", 1, "2898ba7f3b4d857cec8bee4a852cdc85f8b33132",
                    new ResultUpdate()
                            .status(ResultUpdate.StatusEnum.FAILED)
                            .timeMs(Duration.ofMinutes(3).toMillis()));
        } catch (QaseException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/result/PRJ/1/2898ba7f3b4d857cec8bee4a852cdc85f8b33132"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"status\": \"failed\",\n  " +
                        "\"time_ms\": 180000\n}")));
    }

    @Test
    void updateWithParams() {
        try {
            resultsApi.updateResult("PRJ", 1, "2898ba7f3b4d857cec8bee4a852cdc85f8b33132",
                    new ResultUpdate()
                            .status(ResultUpdate.StatusEnum.FAILED)
                            .timeMs(Duration.ofSeconds(10).toMillis())
                            .comment("Failed via API")
                            .defect(false)
                            .steps(Collections.singletonList(
                                    new ResultUpdateSteps().position(2).status(ResultUpdateSteps.StatusEnum.PASSED))));
        } catch (QaseException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/result/PRJ/1/2898ba7f3b4d857cec8bee4a852cdc85f8b33132"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n" +
                        "  \"status\" : \"failed\",\n" +
                        "  \"time_ms\" : 10000,\n" +
                        "  \"defect\" : false,\n" +
                        "  \"comment\" : \"Failed via API\",\n" +
                        "  \"steps\" : [ {\n" +
                        "    \"position\" : 2,\n" +
                        "    \"status\" : \"passed\"\n" +
                        "  } ]\n" +
                        "}")));
    }

    @Test
    void delete() {
        try {
            resultsApi.deleteResult("PRJ", 1, "2898ba7f3b4d857cec8bee4a852cdc85f8b33132");
        } catch (QaseException e) {
            //ignore
        }
        verify(deleteRequestedFor(urlPathEqualTo("/v1/result/PRJ/1/2898ba7f3b4d857cec8bee4a852cdc85f8b33132"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }
}