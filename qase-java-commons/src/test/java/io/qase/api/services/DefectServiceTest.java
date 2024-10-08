package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.api.DefectsApi;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class DefectServiceTest {
    static final WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
    static final ApiClient qaseApi = new ApiClient();
    static final DefectsApi defectsApi = new DefectsApi(qaseApi);
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
            defectsApi.getDefects("PROJ", null, 100, 0);
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/defect/PROJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0")));
    }

    @Test
    void getAllWithParamsAndFilter() {
        try {
            defectsApi.getDefects("PROJ", "open", 88, 12);
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/defect/PROJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withQueryParam("status", equalTo("open"))
                .withQueryParam("limit", equalTo("88"))
                .withQueryParam("offset", equalTo("12")));
    }

    @Test
    void get() {
        try {
            defectsApi.getDefect("PROJ", 99);
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/defect/PROJ/99"))
                .withHeader("Token", equalTo("secret-token")));
    }

    @Test
    void resolve() {
        try {
            defectsApi.resolveDefect("PROJ", 88);
        } catch (ApiException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/defect/PROJ/resolve/88"))
                .withHeader("Token", equalTo("secret-token")));
    }

    @Test
    void delete() {
        try {
            defectsApi.deleteDefect("PROJ", 77);
        } catch (ApiException e) {
            //ignore
        }
        verify(deleteRequestedFor(urlPathEqualTo("/v1/defect/PROJ/77"))
                .withHeader("Token", equalTo("secret-token")));
    }
}
