package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.client.QaseException;
import io.qase.client.ApiClient;
import io.qase.client.api.PlansApi;
import io.qase.client.model.PlanCreate;
import io.qase.client.model.PlanUpdate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class TestPlanServiceTest {
    static final WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
    static final ApiClient qaseApi = new ApiClient();
    static final PlansApi plansApi = new PlansApi(qaseApi);
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
            plansApi.getPlans("PRJ", 100, 0);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/plan/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0")));
    }

    @Test
    void get() {
        try {
            plansApi.getPlan("PRJ", 2);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/plan/PRJ/2"))
                .withHeader("Token", equalTo("secret-token")));
    }

    @Test
    void create() {
        try {
            plansApi.createPlan("PRJ",
                    new PlanCreate()
                            .title("New plan")
                            .cases(Arrays.asList(1L, 2L, 3L, 55L)));
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/plan/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"New plan\",\n  " +
                        "\"cases\": [\n    1,\n    2,\n    3,\n    55\n  ]\n}")));
    }

    @Test
    void createWithDescription() {
        try {
            plansApi.createPlan("PRJ",
                    new PlanCreate()
                            .title("New plan")
                            .description("Awesome plan")
                            .cases(Arrays.asList(1L, 2L, 3L, 55L)));
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/plan/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"New plan\",\n  " +
                        "\"description\": \"Awesome plan\",\n  " +
                        "\"cases\": [\n    1,\n    2,\n    3,\n    55\n  ]\n}")));
    }

    @Test
    void update() {
        try {
            plansApi.updatePlan("PRJ", 2,
                    new PlanUpdate()
                            .title("Updated plan")
                            .description("Updated description")
                            .cases(Arrays.asList(55L, 3L, 2L, 1L)));
        } catch (QaseException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/plan/PRJ/2"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Updated plan\",\n  " +
                        "\"description\": \"Updated description\",\n  " +
                        "\"cases\": [\n    55,\n    3,\n    2,\n    1\n  ]\n}")));
    }

    @Test
    void delete() {
        try {
            plansApi.deletePlan("PRJ", 2);
        } catch (QaseException e) {
            //ignore
        }
        verify(deleteRequestedFor(urlPathEqualTo("/v1/plan/PRJ/2"))
                .withHeader("Token", equalTo("secret-token")));
    }
}