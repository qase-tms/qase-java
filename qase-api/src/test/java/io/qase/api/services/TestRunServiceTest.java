package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.api.QaseApi;
import io.qase.api.enums.RunStatus;
import io.qase.api.exceptions.QaseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class TestRunServiceTest {
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
    void getAllWithoutInclude() {
        try {
            qaseApi.testRuns().getAll("PRJ", false);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/run/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0"))
                .withQueryParam("include", absent()));
    }

    @Test
    void getAllWithInclude() {
        try {
            qaseApi.testRuns().getAll("PRJ", true);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/run/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0"))
                .withQueryParam("include", equalTo("cases")));
    }

    @Test
    void getAllWithParamsAndFilter() {
        try {
            TestRunService.Filter filter = qaseApi.testRuns().filter()
                    .status(RunStatus.complete);
            qaseApi.testRuns().getAll("PRJ", 33, 3, filter, true);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/run/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("33"))
                .withQueryParam("offset", equalTo("3"))
                .withQueryParam("include", equalTo("cases"))
                .withQueryParam("filters[status]", equalTo("complete")));
    }

    @Test
    void getWithoutInclude() {
        try {
            qaseApi.testRuns().get("PRJ", 1, false);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/run/PRJ/1"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("include", absent()));
    }

    @Test
    void getWithInclude() {
        try {
            qaseApi.testRuns().get("PRJ", 1, true);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/run/PRJ/1"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("include", equalTo("cases")));
    }

    @Test
    void create() {
        try {
            qaseApi.testRuns().create("PRJ", "New test run", 1, 2, 3, 55);
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/run/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"New test run\",\n  " +
                        "\"cases\": [\n    1,\n    2,\n    3,\n    55\n  ]\n}")));
    }

    @Test
    void createWithParams() {
        try {
            qaseApi.testRuns().create("PRJ", "New test run", 1, "Awesome run by API", 1, 2, 3, 55);
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/run/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"New test run\",\n  " +
                        "\"description\": \"Awesome run by API\",\n  " +
                        "\"cases\": [\n    1,\n    2,\n    3,\n    55\n  ],\n  " +
                        "\"environment_id\": 1\n}")));
    }

    @Test
    void delete() {
        try {
            qaseApi.testRuns().delete("PRJ", 22);
        } catch (QaseException e) {
            //ignore
        }
        verify(deleteRequestedFor(urlPathEqualTo("/v1/run/PRJ/22"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }
}