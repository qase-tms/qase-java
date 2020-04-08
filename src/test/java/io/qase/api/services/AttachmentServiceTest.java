package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.api.QaseApi;
import io.qase.api.exceptions.QaseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class AttachmentServiceTest {
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
            qaseApi.attachments().getAll();
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/attachment"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0")));
    }

    @Test
    void getAllWithParams() {
        try {
            qaseApi.attachments().getAll(55, 20);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/attachment"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("55"))
                .withQueryParam("offset", equalTo("20")));
    }


    @Test
    void get() {
        try {
            qaseApi.attachments().get("2497be4bc95f807d2fe3c2203793673f6e5140e8");
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/attachment/2497be4bc95f807d2fe3c2203793673f6e5140e8"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @Test
    void add() {
        try {
            qaseApi.attachments().add("2497be4bc95f807d2fe3c2203793673f6e5140e8", Paths.get("src/test/resources/logo.jpg").toFile());
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/attachment/2497be4bc95f807d2fe3c2203793673f6e5140e8"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", containing("multipart/form-data;"))
                .withRequestBody(containing("Content-Disposition: form-data; name=\"logo.jpg\"; filename=\"logo.jpg\"")));
    }

    @Test
    void delete() {
        try {
            qaseApi.attachments().delete("2497be4bc95f807d2fe3c2203793673f6e5140e8");
        } catch (QaseException e) {
            //ignore
        }
        verify(deleteRequestedFor(urlPathEqualTo("/v1/attachment/2497be4bc95f807d2fe3c2203793673f6e5140e8"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }
}