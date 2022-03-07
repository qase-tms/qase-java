package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.api.exceptions.QaseException;
import io.qase.client.ApiClient;
import io.qase.client.api.MilestonesApi;
import io.qase.client.model.Filters3;
import io.qase.client.model.MilestoneCreate;
import io.qase.client.model.MilestoneUpdate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class MilestoneServiceTest {
    static final WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
    static final ApiClient qaseApi = new ApiClient();
    static final MilestonesApi milestonesApi = new MilestonesApi(qaseApi);
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
            milestonesApi.getMilestones("PROJ", null, 100, 0);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/milestone/PROJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0")));
    }

    @Test
    void getAllWithFilter() {
        try {
            milestonesApi.getMilestones("PROJ", new Filters3().search("title"), 100, 0);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/milestone/PROJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0"))
                .withQueryParam("filters%5Bsearch%5D", equalTo("title")));
    }

    @Test
    void get() {
        try {
            milestonesApi.getMilestone("PROJ", 65);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/milestone/PROJ/65"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @Test
    void create() {
        try {
            milestonesApi.createMilestone("PROJ", new MilestoneCreate().title("MTitle"));
        } catch (QaseException e) {
            // ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/milestone/PROJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  \"title\": \"MTitle\"\n}")));
    }

    @Test
    void createWithDescription() {
        try {
            milestonesApi.createMilestone("PROJ",
                    new MilestoneCreate()
                            .title("MTitle")
                            .description("MDescription"));
        } catch (QaseException e) {
            // ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/milestone/PROJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  \"title\" : \"MTitle\",\n  \"description\" : \"MDescription\"\n}")));
    }

    @Test
    void delete() {
        try {
            milestonesApi.deleteMilestone("PROJ", 123);
        } catch (QaseException e) {
            // ignore
        }
        verify(deleteRequestedFor(urlPathEqualTo("/v1/milestone/PROJ/123"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @Test
    void update() {
        try {
            milestonesApi.updateMilestone("PROJ", 123, new MilestoneUpdate().title("newMTitle"));
        } catch (QaseException e) {
            // ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/milestone/PROJ/123"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  \"title\" : \"newMTitle\"\n}")));
    }

    @Test
    void updateWithDescription() {
        try {
            milestonesApi.updateMilestone("PROJ", 123,
                    new MilestoneUpdate()
                            .title("newMTitle")
                            .description("newMDescription"));
        } catch (QaseException e) {
            // ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/milestone/PROJ/123"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  \"title\" : \"newMTitle\",\n  \"description\" : \"newMDescription\"\n}")));
    }
}
