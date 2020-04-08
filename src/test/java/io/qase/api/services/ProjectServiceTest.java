package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.api.QaseApi;
import io.qase.api.enums.Access;
import io.qase.api.exceptions.QaseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class ProjectServiceTest {
    private static final WireMockServer wireMockServer = new WireMockServer(options().port(8088));
    private static final QaseApi qaseApi = new QaseApi("secret-token", "http://localhost:8088/v1");

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
            qaseApi.projects().getAll();
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/project"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0")));
    }

    @Test
    void getAllWithParams() {
        try {
            qaseApi.projects().getAll(18, 2);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/project"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("18"))
                .withQueryParam("offset", equalTo("2")));
    }

    @Test
    void get() {
        try {
            qaseApi.projects().get("PROJ");
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/project/PROJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @Test
    void create() {
        try {
            qaseApi.projects().create("PROJ", "Project title");
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/project"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
        .withRequestBody(
                equalToJson("{\n  \"code\": \"PROJ\",\n  " +
                        "\"title\": \"Project title\",\n  " +
                        "\"access\": \"none\"\n}")));
    }

    @Test
    void testCreateWithDescription() {
        try {
            qaseApi.projects().create("PROJ", "Project title", "Awesome project");
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/project"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(
                        equalToJson("{\n  \"code\": \"PROJ\",\n  " +
                                "\"title\": \"Project title\",\n  " +
                                "\"description\": \"Awesome project\",\n  " +
                                "\"access\": \"none\"\n}")));
    }

    @Test
    void createWithParams() {
        try {
            qaseApi.projects().create("PROJ", "Project title", "Awesome project", Access.group, "groupHash");
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/project"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(
                        equalToJson("{\n  \"code\": \"PROJ\",\n  " +
                                "\"title\": \"Project title\",\n  " +
                                "\"description\": \"Awesome project\",\n  " +
                                "\"access\": \"group\",\n  " +
                                "\"group\": \"groupHash\"\n}")));
    }
}