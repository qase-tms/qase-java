package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.api.QaseApi;
import io.qase.api.exceptions.QaseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class SuiteServiceTest {
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
            qaseApi.suites().getAll("PRJ");
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/suite/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0")));
    }

    @Test
    void getAllWithParamsAndFilter() {
        try {
            SuiteService.Filter filter = qaseApi.suites().filter().search("title");
            qaseApi.suites().getAll("PRJ", 55, 2, filter);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/suite/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("55"))
                .withQueryParam("offset", equalTo("2"))
                .withQueryParam("filters[search]", equalTo("title")));
    }

    @Test
    void get() {
        try {
            qaseApi.suites().get("PRJ", 1);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/suite/PRJ/1"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @Test
    void create() {
        try {
            qaseApi.suites().create("PRJ", "Test suite");
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/suite/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Test suite\"\n}")));
    }

    @Test
    void createWithDescription() {
        try {
            qaseApi.suites().create("PRJ", "Test suite", "Suite description");
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/suite/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Test suite\",\n  " +
                        "\"description\": \"Suite description\"\n}")));
    }

    @Test
    void createWithPreconditions() {
        try {
            qaseApi.suites().create("PRJ", "Test suite", "Suite description", "Suite preconditions");
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/suite/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Test suite\",\n  " +
                        "\"description\": \"Suite description\",\n  " +
                        "\"preconditions\": \"Suite preconditions\"\n}")));
    }

    @Test
    void createWithParent() {
        try {
            qaseApi.suites().create("PRJ", "Test suite", "Suite description", "Suite preconditions", 12);
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/suite/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"parent_id\": 12,\n  " +
                        "\"title\": \"Test suite\",\n  " +
                        "\"description\": \"Suite description\",\n  " +
                        "\"preconditions\": \"Suite preconditions\"\n}")));
    }

    @Test
    void update() {
        try {
            qaseApi.suites().update("PRJ", 1, "Test suite");
        } catch (QaseException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/suite/PRJ/1"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Test suite\"\n}")));
    }

    @Test
    void updateWithDescription() {
        try {
            qaseApi.suites().update("PRJ", 1, "Test suite", "Suite description");
        } catch (QaseException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/suite/PRJ/1"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Test suite\",\n  " +
                        "\"description\": \"Suite description\"\n}")));
    }

    @Test
    void updateWithPreconditions() {
        try {
            qaseApi.suites().update("PRJ", 1, "Test suite", "Suite description", "Suite preconditions");
        } catch (QaseException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/suite/PRJ/1"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Test suite\",\n  " +
                        "\"description\": \"Suite description\",\n  " +
                        "\"preconditions\": \"Suite preconditions\"\n}")));
    }

    @Test
    void updateWithParent() {
        try {
            qaseApi.suites().update("PRJ", 1, "Test suite", "Suite description", "Suite preconditions", 2);
        } catch (QaseException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/suite/PRJ/1"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"parent_id\": 2,\n  " +
                        "\"title\": \"Test suite\",\n  " +
                        "\"description\": \"Suite description\",\n  " +
                        "\"preconditions\": \"Suite preconditions\"\n}")));
    }

    @Test
    void delete() {
        try {
            qaseApi.suites().delete("PRJ", 1);
        } catch (QaseException e) {
            //ignore
        }
        verify(deleteRequestedFor(urlPathEqualTo("/v1/suite/PRJ/1"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }
}