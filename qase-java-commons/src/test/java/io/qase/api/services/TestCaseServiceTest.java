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
                .withQueryParam("filters%5Bmilestone_id%5D", equalTo("11"))
                .withQueryParam("filters%5Bseverity%5D", equalTo("critical"))
                .withQueryParam("filters%5Bbehavior%5D", equalTo("positive"))
                .withQueryParam("filters%5Bautomation%5D", equalTo("is-not-automated,to-be-automated"))
                .withQueryParam("filters%5Bsearch%5D", equalTo("title"))
                .withQueryParam("filters%5Bpriority%5D", equalTo("high,medium"))
                .withQueryParam("filters%5Btype%5D", equalTo("functional,acceptance"))
                .withQueryParam("filters%5Bstatus%5D", equalTo("actual"))
                .withQueryParam("filters%5Bsuite_id%5D", equalTo("2")));
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
