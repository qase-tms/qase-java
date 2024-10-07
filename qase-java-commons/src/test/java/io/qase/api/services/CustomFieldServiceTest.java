package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.api.CustomFieldsApi;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class CustomFieldServiceTest {
    static final WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
    static final ApiClient qaseApi = new ApiClient();
    static final CustomFieldsApi customFieldsApi = new CustomFieldsApi(qaseApi);
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
            customFieldsApi.getCustomFields(null, "100", 0, 0);
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/custom_field"))
                .withHeader("Token", equalTo("secret-token"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0")));
    }

    @Test
    void get() {
        try {
            customFieldsApi.getCustomField(123);
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/custom_field/123"))
                .withHeader("Token", equalTo("secret-token")));
    }
}
