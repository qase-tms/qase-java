package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.api.QaseApi;
import io.qase.api.enums.RunResultStatus;
import io.qase.api.enums.StepStatus;
import io.qase.api.exceptions.QaseException;
import io.qase.api.models.v1.testrunresults.Step;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class TestRunResultServiceTest {
    static final WireMockServer wireMockServer = new WireMockServer(options().port(8088));
    static final QaseApi qaseApi = new QaseApi("secret-token", "http://localhost:8088/v1");

    @BeforeAll
    static void setUp() {
        configureFor(8088);
        wireMockServer.start();
    }

    @AfterAll
    static void tearDown() {
        wireMockServer.stop();
    }

    @Test
    void getAll() {
        try {
            qaseApi.testRunResults().getAll("PRJ");
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
        LocalDateTime from = LocalDateTime.now().minusDays(1);
        LocalDateTime to = LocalDateTime.now();
        try {
            TestRunResultService.Filter filter = qaseApi.testRunResults().filter()
                    .caseId(1)
                    .member(2)
                    .run(3)
                    .status(RunResultStatus.in_progress)
                    .fromEndTime(from)
                    .toEndTime(to);
            qaseApi.testRunResults().getAll("PRJ", 33, 3, filter);
        } catch (QaseException e) {
            //ignore
        }
        String timeFormat = "yyyy-MM-dd HH:mm:ss";
        verify(getRequestedFor(urlPathEqualTo("/v1/result/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("33"))
                .withQueryParam("offset", equalTo("3"))
                .withQueryParam("filters[status]", equalTo("in_progress"))
                .withQueryParam("filters[member]", equalTo("2"))
                .withQueryParam("filters[run]", equalTo("3"))
                .withQueryParam("filters[case_id]", equalTo("1"))
                .withQueryParam("filters[from_end_time]", equalTo(DateTimeFormatter.ofPattern(timeFormat).format(from)))
                .withQueryParam("filters[to_end_time]", equalTo(DateTimeFormatter.ofPattern(timeFormat).format(to))));
    }

    @Test
    void get() {
        try {
            qaseApi.testRunResults().get("PRJ", "6efce6e4f9de887a2ee863e8197cb74ab626a273");
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
            qaseApi.testRunResults().create("PRJ", 2, 1, RunResultStatus.passed);
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/2"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"case_id\": 1,\n  " +
                        "\"status\": \"passed\",\n  " +
                        "\"steps\": []\n}")));
    }

    @Test
    void createWithParams() {
        try {
            qaseApi.testRunResults().create("PRJ", 2, 1, RunResultStatus.passed,
                    Duration.ofMinutes(2),
                    3,
                    "Failed via API",
                    true,
                    new Step(1, StepStatus.passed),
                    new Step(2, StepStatus.failed));
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/result/PRJ/2"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"case_id\": 1,\n  " +
                        "\"comment\": \"Failed via API\",\n  " +
                        "\"defect\": true,\n  " +
                        "\"member_id\": 3,\n  " +
                        "\"status\": \"passed\",\n  " +
                        "\"steps\": [\n    " +
                        "{\n      \"position\": 1,\n      \"status\": \"passed\"\n    },\n    " +
                        "{\n      \"position\": 2,\n      \"status\": \"failed\"\n    }\n  ],\n  " +
                        "\"time\": 120\n}")));
    }

    @Test
    void update() {
        try {
            qaseApi.testRunResults().update("PRJ", 1, "2898ba7f3b4d857cec8bee4a852cdc85f8b33132",
                    RunResultStatus.failed);
        } catch (QaseException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/result/PRJ/1/2898ba7f3b4d857cec8bee4a852cdc85f8b33132"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"status\": \"failed\",\n  " +
                        "\"steps\": []\n}")));
    }

    @Test
    void updateWithTimeSpent() {
        try {
            qaseApi.testRunResults().update("PRJ", 1, "2898ba7f3b4d857cec8bee4a852cdc85f8b33132",
                    RunResultStatus.failed, Duration.ofMinutes(3));
        } catch (QaseException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/result/PRJ/1/2898ba7f3b4d857cec8bee4a852cdc85f8b33132"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"status\": \"failed\",\n  " +
                        "\"steps\": [],\n  " +
                        "\"time\": 180\n}")));
    }

    @Test
    void updateWithParams() {
        try {
            qaseApi.testRunResults().update("PRJ", 1, "2898ba7f3b4d857cec8bee4a852cdc85f8b33132",
                    RunResultStatus.failed,
                    Duration.ofSeconds(10),
                    3,
                    "Failed via API",
                    false,
                    new Step(2, StepStatus.passed));
        } catch (QaseException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/result/PRJ/1/2898ba7f3b4d857cec8bee4a852cdc85f8b33132"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"comment\": \"Failed via API\",\n  " +
                        "\"defect\": false,\n  " +
                        "\"member_id\": 3,\n  " +
                        "\"status\": \"failed\",\n  " +
                        "\"steps\": [\n   " +
                        " {\n      \"position\": 2,\n      \"status\": \"passed\"\n    }\n  ],\n  " +
                        "\"time\": 10\n}")));
    }

    @Test
    void delete() {
        try {
            qaseApi.testRunResults().delete("PRJ", 1, "2898ba7f3b4d857cec8bee4a852cdc85f8b33132");
        } catch (QaseException e) {
            //ignore
        }
        verify(deleteRequestedFor(urlPathEqualTo("/v1/result/PRJ/1/2898ba7f3b4d857cec8bee4a852cdc85f8b33132"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }
}