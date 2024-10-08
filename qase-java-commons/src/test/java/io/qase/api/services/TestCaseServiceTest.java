package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.api.CasesApi;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class TestCaseServiceTest {
    static final WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
    static final ApiClient qaseApi = new ApiClient();
    static final CasesApi casesApi = new CasesApi(qaseApi);
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
            casesApi.getCases("PRJ", "", null, null, null, null, null, null, null, null, null, null, null, 100, 0);
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/case/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0")));
    }

    @Test
    void getAllWithFilter() {
        try {
            casesApi.getCases("PRJ", "title", 11, 2, "critical", "high,medium", "functional,acceptance", "positive", "is-not-automated,to-be-automated", "actual", null, null, null, 100, 0);
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/case/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0"))
                .withQueryParam("milestone_id", equalTo("11"))
                .withQueryParam("severity", equalTo("critical"))
                .withQueryParam("behavior", equalTo("positive"))
                .withQueryParam("automation", equalTo("is-not-automated,to-be-automated"))
                .withQueryParam("search", equalTo("title"))
                .withQueryParam("priority", equalTo("high,medium"))
                .withQueryParam("type", equalTo("functional,acceptance"))
                .withQueryParam("status", equalTo("actual"))
                .withQueryParam("suite_id", equalTo("2")));
    }

    @Test
    void get() {
        try {
            casesApi.getCase("PRJ", 8);
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/case/PRJ/8"))
                .withHeader("Token", equalTo("secret-token")));
    }

    @Test
    void delete() {
        try {
            casesApi.deleteCase("PRJ", 8);
        } catch (ApiException e) {
            //ignore
        }
        verify(deleteRequestedFor(urlPathEqualTo("/v1/case/PRJ/8"))
                .withHeader("Token", equalTo("secret-token")));
    }
}
