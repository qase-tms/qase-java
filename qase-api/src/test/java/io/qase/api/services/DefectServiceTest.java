package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.api.QaseApi;
import io.qase.api.enums.DefectStatus;
import io.qase.api.exceptions.QaseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class DefectServiceTest {
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
            qaseApi.defects().getAll("PROJ");
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/defect/PROJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0")));
    }

    @Test
    void getAllWithParams() {
        try {
            qaseApi.defects().getAll("PROJ", 88, 12, qaseApi.defects().filter());
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/defect/PROJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("88"))
                .withQueryParam("offset", equalTo("12")));
    }

    @Test
    void getAllWithParamsAndFilter() {
        try {
            DefectService.Filter filter = qaseApi.defects().filter().status(DefectStatus.open);
            qaseApi.defects().getAll("PROJ", 88, 12, filter);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/defect/PROJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("filters[status]", equalTo("open"))
                .withQueryParam("limit", equalTo("88"))
                .withQueryParam("offset", equalTo("12")));
    }

    @Test
    void get() {
        try {
            qaseApi.defects().get("PROJ", 99);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/defect/PROJ/99"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @Test
    void resolve() {
        try {
            qaseApi.defects().resolve("PROJ", 88);
        } catch (QaseException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/defect/PROJ/resolve/88"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @Test
    void delete() {
        try {
            qaseApi.defects().delete("PROJ", 77);
        } catch (QaseException e) {
            //ignore
        }
        verify(deleteRequestedFor(urlPathEqualTo("/v1/defect/PROJ/77"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }
}