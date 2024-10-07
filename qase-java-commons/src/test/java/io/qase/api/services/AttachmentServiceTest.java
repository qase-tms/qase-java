package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.Configuration;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.api.AttachmentsApi;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import java.util.Collections;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class AttachmentServiceTest {
    static final WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
    static final ApiClient qaseApi = Configuration.getDefaultApiClient();
    static final AttachmentsApi attachmentsApi = new AttachmentsApi(qaseApi);
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
            attachmentsApi.getAttachments(100, 0);
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/attachment"))
                .withHeader("Token", equalTo("secret-token"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0")));
    }


    @Test
    void get() {
        try {
            attachmentsApi.getAttachment("2497be4bc95f807d2fe3c2203793673f6e5140e8");
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/attachment/2497be4bc95f807d2fe3c2203793673f6e5140e8"))
                .withHeader("Token", equalTo("secret-token")));
    }

    @Test
    void add() {
        try {
            attachmentsApi.uploadAttachment("2497be4bc95f807d2fe3c2203793673f6e5140e8",
                    Collections.singletonList(Paths.get("src/test/resources/logo.jpg").toFile()));
        } catch (ApiException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/attachment/2497be4bc95f807d2fe3c2203793673f6e5140e8"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", containing("multipart/form-data;"))
                .withAnyRequestBodyPart(aMultipart().withName("file")));
    }

    @Test
    void delete() {
        try {
            attachmentsApi.deleteAttachment("2497be4bc95f807d2fe3c2203793673f6e5140e8");
        } catch (ApiException e) {
            //ignore
        }
        verify(deleteRequestedFor(urlPathEqualTo("/v1/attachment/2497be4bc95f807d2fe3c2203793673f6e5140e8"))
                .withHeader("Token", equalTo("secret-token")));
    }
}
