package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.api.SuitesApi;
import io.qase.client.v1.models.SuiteCreate;
import io.qase.client.v1.models.SuiteUpdate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class SuiteServiceTest {
    static final WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
    static final ApiClient qaseApi = new ApiClient();
    static final SuitesApi suitesApi = new SuitesApi(qaseApi);
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
            suitesApi.getSuites("PRJ", null, 100, 0);
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/suite/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0")));
    }

    @Test
    void getAllWithParamsAndFilter() {
        try {
            suitesApi.getSuites("PRJ", "title", 55, 2);
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/suite/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withQueryParam("limit", equalTo("55"))
                .withQueryParam("offset", equalTo("2"))
                .withQueryParam("filters%5Bsearch%5D", equalTo("title")));
    }

    @Test
    void get() {
        try {
            suitesApi.getSuite("PRJ", 1);
        } catch (ApiException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/suite/PRJ/1"))
                .withHeader("Token", equalTo("secret-token")));
    }

    @Test
    void create() {
        try {
            suitesApi.createSuite("PRJ", new SuiteCreate().title("Test suite"));
        } catch (ApiException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/suite/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Test suite\"\n}")));
    }

    @Test
    void createWithDescription() {
        try {
            suitesApi.createSuite("PRJ",
                    new SuiteCreate().title("Test suite").description("Suite description"));
        } catch (ApiException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/suite/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Test suite\",\n  " +
                        "\"description\": \"Suite description\"\n}")));
    }

    @Test
    void createWithPreconditions() {
        try {
            suitesApi.createSuite("PRJ",
                    new SuiteCreate()
                            .title("Test suite")
                            .description("Suite description")
                            .preconditions("Suite preconditions"));
        } catch (ApiException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/suite/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Test suite\",\n  " +
                        "\"description\": \"Suite description\",\n  " +
                        "\"preconditions\": \"Suite preconditions\"\n}")));
    }

    @Test
    void createWithParent() {
        try {
            suitesApi.createSuite("PRJ",
                    new SuiteCreate()
                            .title("Test suite")
                            .description("Suite description")
                            .preconditions("Suite preconditions")
                            .parentId(12L));
        } catch (ApiException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/suite/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"parent_id\": 12,\n  " +
                        "\"title\": \"Test suite\",\n  " +
                        "\"description\": \"Suite description\",\n  " +
                        "\"preconditions\": \"Suite preconditions\"\n}")));
    }

    @Test
    void update() {
        try {
            suitesApi.updateSuite("PRJ", 1, new SuiteUpdate().title("Test suite"));
        } catch (ApiException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/suite/PRJ/1"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Test suite\"\n}")));
    }

    @Test
    void updateWithDescription() {
        try {
            suitesApi.updateSuite("PRJ", 1,
                    new SuiteUpdate()
                            .title("Test suite")
                            .description("Suite description"));
        } catch (ApiException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/suite/PRJ/1"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Test suite\",\n  " +
                        "\"description\": \"Suite description\"\n}")));
    }

    @Test
    void updateWithPreconditions() {
        try {
            suitesApi.updateSuite("PRJ", 1,
                    new SuiteUpdate()
                            .title("Test suite")
                            .description("Suite description")
                            .preconditions("Suite preconditions"));
        } catch (ApiException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/suite/PRJ/1"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"title\": \"Test suite\",\n  " +
                        "\"description\": \"Suite description\",\n  " +
                        "\"preconditions\": \"Suite preconditions\"\n}")));
    }

    @Test
    void updateWithParent() {
        try {
            suitesApi.updateSuite("PRJ", 1,
                    new SuiteUpdate()
                            .title("Test suite")
                            .description("Suite description")
                            .preconditions("Suite preconditions")
                            .parentId(2L));
        } catch (ApiException e) {
            //ignore
        }
        verify(patchRequestedFor(urlPathEqualTo("/v1/suite/PRJ/1"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(equalToJson("{\n  " +
                        "\"parent_id\": 2,\n  " +
                        "\"title\": \"Test suite\",\n  " +
                        "\"description\": \"Suite description\",\n  " +
                        "\"preconditions\": \"Suite preconditions\"\n}")));
    }

    @Test
    void delete() {
        try {
            suitesApi.deleteSuite("PRJ", 1, null);
        } catch (ApiException e) {
            //ignore
        }
        verify(deleteRequestedFor(urlPathEqualTo("/v1/suite/PRJ/1"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }
}
