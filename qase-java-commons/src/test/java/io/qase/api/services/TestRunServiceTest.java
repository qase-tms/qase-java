package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.api.RunsApi;
import io.qase.client.v1.models.RunCreate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class TestRunServiceTest {
    static final WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
    static ApiClient qaseApi = new ApiClient();
    static final RunsApi runsApi = new RunsApi(qaseApi);
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
    void getAllWithoutInclude() {
        try {
            runsApi.getRuns("PRJ", null, null, null, null,null,null,100,0,null);
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/run/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0"))
                .withQueryParam("include", absent()));
    }

    @Test
    void getAllWithInclude() {
        try {
            runsApi.getRuns("PRJ", null, null, null, 1,null,null,100,0,null);
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/run/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0"))
                .withQueryParam("include", equalTo("cases")));
    }

    @Test
    void getAllWithParamsAndFilter() {
        try {
            runsApi.getRuns("PRJ", null, "complete", null, 1,null,null,33,3,null);
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/run/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withQueryParam("limit", equalTo("33"))
                .withQueryParam("offset", equalTo("3"))
                .withQueryParam("include", equalTo("cases"))
                .withQueryParam("filters%5Bstatus%5D", equalTo("complete")));
    }

    @Test
    void get() {
        try {
            runsApi.getRun("PRJ", 1, null);
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/run/PRJ/1"))
                .withHeader("Token", equalTo("secret-token"))
                .withQueryParam("include", absent()));
    }

    @Test
    void create() {
        try {
            runsApi.createRun("PRJ",
                    new RunCreate()
                            .title("New test run")
                            .cases(Arrays.asList(1L, 2L, 3L, 55L)));
        } catch (ApiException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/run/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"New test run\",\n  " +
                        "\"cases\": [\n    1,\n    2,\n    3,\n    55\n  ]\n}")));
    }

    @Test
    void createWithParams() {
        try {
            runsApi.createRun("PRJ",
                    new RunCreate()
                            .title("New test run")
                            .environmentId(1L)
                            .description("Awesome run by API")
                            .cases(Arrays.asList(1L, 2L, 3L, 55L)));
        } catch (ApiException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/run/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"New test run\",\n  " +
                        "\"description\": \"Awesome run by API\",\n  " +
                        "\"cases\": [\n    1,\n    2,\n    3,\n    55\n  ],\n  " +
                        "\"environment_id\": 1\n}")));
    }

    @Test
    void delete() {
        try {
            runsApi.deleteRun("PRJ", 22);
        } catch (ApiException e) {
            //ignore
        }
        verify(deleteRequestedFor(urlPathEqualTo("/v1/run/PRJ/22"))
                .withHeader("Token", equalTo("secret-token")));
    }
}
