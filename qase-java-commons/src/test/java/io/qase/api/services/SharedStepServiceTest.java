package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.api.SharedStepsApi;
import io.qase.client.v1.models.SharedStepCreate;
import io.qase.client.v1.models.SharedStepUpdate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class SharedStepServiceTest {
    static final WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
    static final ApiClient qaseApi = new ApiClient();
    static final SharedStepsApi sharedStepsApi = new SharedStepsApi(qaseApi);
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
            sharedStepsApi.getSharedSteps("PRJ", null, 100, 0);
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/shared_step/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0")));
    }

    @Test
    void getAllWithFilter() {
        try {
            sharedStepsApi.getSharedSteps("PRJ", "title", 100, 0);
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/shared_step/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0"))
                .withQueryParam("search", equalTo("title"))
        );
    }

    @Test
    void get() {
        try {
            sharedStepsApi.getSharedStep("PRJ", "0223905c291bada23e6049d415385982af92d758");
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/shared_step/PRJ/0223905c291bada23e6049d415385982af92d758"))
                .withHeader("Token", equalTo("secret-token")));
    }

    @Test
    void create() {
        try {
            sharedStepsApi.createSharedStep("PRJ",
                    new SharedStepCreate()
                            .title("Shared step")
                            .action("Open URL"));
        } catch (ApiException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/shared_step/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Shared step\",\n  " +
                        "\"action\": \"Open URL\"\n}")));
    }

    @Test
    void createWithExpectedResult() {
        try {
            sharedStepsApi.createSharedStep("PRJ",
                    new SharedStepCreate()
                            .title("Shared step")
                            .action("Open URL")
                            .expectedResult("URL is opened"));
        } catch (ApiException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/shared_step/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Shared step\",\n  " +
                        "\"action\": \"Open URL\",\n  " +
                        "\"expected_result\": \"URL is opened\"\n}")));
    }

    @Test
    void delete() {
        try {
            sharedStepsApi.deleteSharedStep("PRJ", "0223905c291bada23e6049d415385982af92d758");
        } catch (ApiException e) {
            //ignore
        }
        verify(deleteRequestedFor(urlPathEqualTo("/v1/shared_step/PRJ/0223905c291bada23e6049d415385982af92d758"))
                .withHeader("Token", equalTo("secret-token")));
    }

    @Test
    void update() {
        try {
            sharedStepsApi.updateSharedStep("PRJ", "0223905c291bada23e6049d415385982af92d758",
                    new SharedStepUpdate()
                            .title("Shared step")
                            .action("Open URL"));
        } catch (ApiException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/shared_step/PRJ/0223905c291bada23e6049d415385982af92d758"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Shared step\",\n  " +
                        "\"action\": \"Open URL\"\n}")));
    }

    @Test
    void updateWithExpectedResult() {
        try {
            sharedStepsApi.updateSharedStep("PRJ", "0223905c291bada23e6049d415385982af92d758",
                    new SharedStepUpdate()
                            .title("Shared step")
                            .action("Open URL")
                            .expectedResult("URL is opened"));
        } catch (ApiException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/shared_step/PRJ/0223905c291bada23e6049d415385982af92d758"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Shared step\",\n  " +
                        "\"action\": \"Open URL\",\n  " +
                        "\"expected_result\": \"URL is opened\"\n}")));
    }
}
