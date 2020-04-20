package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.api.QaseApi;
import io.qase.api.exceptions.QaseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class TestPlanServiceTest {
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
            qaseApi.testPlans().getAll("PRJ");
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/plan/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0")));
    }

    @Test
    void getAllWithParamsAndFilter() {
        try {
            TestPlanService.Filter filter = qaseApi.testPlans().filter().search("title");
            qaseApi.testPlans().getAll("PRJ", 55, 5, filter);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/plan/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("55"))
                .withQueryParam("offset", equalTo("5"))
                .withQueryParam("filters[search]", equalTo("title")));
    }

    @Test
    void get() {
        try {
            qaseApi.testPlans().get("PRJ", 2);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/plan/PRJ/2"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @Test
    void create() {
        try {
            qaseApi.testPlans().create("PRJ", "New plan", 1, 2, 3, 55);
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/plan/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"New plan\",\n  " +
                        "\"cases\": [\n    1,\n    2,\n    3,\n    55\n  ]\n}")));
    }

    @Test
    void createWithDescription() {
        try {
            qaseApi.testPlans().create("PRJ", "New plan", "Awesome plan", 1, 2, 3, 55);
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/plan/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"New plan\",\n  " +
                        "\"description\": \"Awesome plan\",\n  " +
                        "\"cases\": [\n    1,\n    2,\n    3,\n    55\n  ]\n}")));
    }

    @Test
    void update() {
        try {
            qaseApi.testPlans().update("PRJ", 2, "Updated plan", "Updated description", 55, 3, 2, 1);
        } catch (QaseException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/plan/PRJ/2"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Updated plan\",\n  " +
                        "\"description\": \"Updated description\",\n  " +
                        "\"cases\": [\n    55,\n    3,\n    2,\n    1\n  ]\n}")));
    }

    @Test
    void delete() {
        try {
            qaseApi.testPlans().delete("PRJ", 2);
        } catch (QaseException e) {
            //ignore
        }
        verify(deleteRequestedFor(urlPathEqualTo("/v1/plan/PRJ/2"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }
}