package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.api.QaseApi;
import io.qase.api.exceptions.QaseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class MilestoneServiceTest {
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
            qaseApi.milestones().getAll("PROJ");
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/milestone/PROJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0")));
    }

    @Test
    void getAllWithParams() {
        try {
            qaseApi.milestones().getAll("PROJ", 55, 10, qaseApi.milestones().filter());
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/milestone/PROJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("55"))
                .withQueryParam("offset", equalTo("10")));
    }

    @Test
    void getAllWithFilter() {
        try {
            MilestoneService.Filter filter = qaseApi.milestones().filter().search("title");
            qaseApi.milestones().getAll("PROJ", filter);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/milestone/PROJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0"))
                .withQueryParam("filters[search]", equalTo("title")));
    }

    @Test
    void get() {
        try {
            qaseApi.milestones().get("PROJ", 65);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/milestone/PROJ/65"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @Test
    void create() {
        try {
            qaseApi.milestones().create("PROJ", "MTitle");
        } catch (QaseException e) {
            // ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/milestone/PROJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  \"title\": \"MTitle\"\n}")));
    }

    @Test
    void createWithDescription() {
        try {
            qaseApi.milestones().create("PROJ", "MTitle", "MDescription");
        } catch (QaseException e) {
            // ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/milestone/PROJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  \"title\" : \"MTitle\",\n  \"description\" : \"MDescription\"\n}")));
    }

    @Test
    void delete() {
        try {
            qaseApi.milestones().delete("PROJ", 123);
        } catch (QaseException e) {
            // ignore
        }
        verify(deleteRequestedFor(urlPathEqualTo("/v1/milestone/PROJ/123"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @Test
    void update() {
        try {
            qaseApi.milestones().update("PROJ", 123, "newMTitle");
        } catch (QaseException e) {
            // ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/milestone/PROJ/123"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  \"title\" : \"newMTitle\"\n}")));
    }

    @Test
    void updateWithDescription() {
        try {
            qaseApi.milestones().update("PROJ", 123, "newMTitle", "newMDescription");
        } catch (QaseException e) {
            // ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/milestone/PROJ/123"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  \"title\" : \"newMTitle\",\n  \"description\" : \"newMDescription\"\n}")));
    }
}