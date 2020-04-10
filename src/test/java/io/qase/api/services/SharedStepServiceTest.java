package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.api.QaseApi;
import io.qase.api.exceptions.QaseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class SharedStepServiceTest {
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
            qaseApi.sharedSteps().getAll("PRJ");
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/shared_step/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0")));
    }

    @Test
    void getAllWithFilter() {
        try {
            SharedStepService.Filter filter = qaseApi.sharedSteps().filter().search("title");
            qaseApi.sharedSteps().getAll("PRJ", filter);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/shared_step/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0"))
                .withQueryParam("filters[search]", equalTo("title")));
    }

    @Test
    void getAllWithParams() {
        try {
            SharedStepService.Filter filter = qaseApi.sharedSteps().filter().search("title");
            qaseApi.sharedSteps().getAll("PRJ", 99, 22, filter);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/shared_step/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("99"))
                .withQueryParam("offset", equalTo("22"))
                .withQueryParam("filters[search]", equalTo("title")));
    }

    @Test
    void get() {
        try {
            qaseApi.sharedSteps().get("PRJ", "0223905c291bada23e6049d415385982af92d758");
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/shared_step/PRJ/0223905c291bada23e6049d415385982af92d758"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @Test
    void create() {
        try {
            qaseApi.sharedSteps().create("PRJ", "Shared step", "Open URL");
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/shared_step/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Shared step\",\n  " +
                        "\"action\": \"Open URL\"\n}")));
    }

    @Test
    void createWithExpectedResult() {
        try {
            qaseApi.sharedSteps().create("PRJ", "Shared step", "Open URL", "URL is opened");
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/shared_step/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Shared step\",\n  " +
                        "\"action\": \"Open URL\",\n  " +
                        "\"expected_result\": \"URL is opened\"\n}")));
    }

    @Test
    void delete() {
        try {
            qaseApi.sharedSteps().delete("PRJ", "0223905c291bada23e6049d415385982af92d758");
        } catch (QaseException e) {
            //ignore
        }
        verify(deleteRequestedFor(urlPathEqualTo("/v1/shared_step/PRJ/0223905c291bada23e6049d415385982af92d758"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @Test
    void update() {
        try {
            qaseApi.sharedSteps().update("PRJ", "0223905c291bada23e6049d415385982af92d758",
                    "Shared step",
                    "Open URL");
        } catch (QaseException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/shared_step/PRJ/0223905c291bada23e6049d415385982af92d758"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Shared step\",\n  " +
                        "\"action\": \"Open URL\"\n}")));
    }

    @Test
    void updateWithExpectedResult() {
        try {
            qaseApi.sharedSteps().update("PRJ", "0223905c291bada23e6049d415385982af92d758",
                    "Shared step",
                    "Open URL",
                    "URL is opened");
        } catch (QaseException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/shared_step/PRJ/0223905c291bada23e6049d415385982af92d758"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Shared step\",\n  " +
                        "\"action\": \"Open URL\",\n  " +
                        "\"expected_result\": \"URL is opened\"\n}")));
    }
}