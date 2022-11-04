package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.api.exceptions.QaseException;
import io.qase.client.ApiClient;
import io.qase.client.api.ProjectsApi;
import io.qase.client.model.ProjectCreate;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class ProjectServiceTest {
    static final WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
    static final ApiClient qaseApi = new ApiClient();
    static final ProjectsApi projectsApi = new ProjectsApi(qaseApi);
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
            projectsApi.getProjects(100, 0);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/project"))
                .withHeader("Token", equalTo("secret-token"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0")));
    }

    @Test
    void get() {
        try {
            projectsApi.getProject("PROJ");
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/project/PROJ"))
                .withHeader("Token", equalTo("secret-token")));
    }

    @Test
    void create() {
        try {
            projectsApi.createProject(
                    new ProjectCreate()
                            .code("PROJ")
                            .title("Project title")
                            .access(ProjectCreate.AccessEnum.NONE));
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/project"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(
                        equalToJson("{\n  \"code\": \"PROJ\",\n  " +
                                "\"title\": \"Project title\",\n  " +
                                "\"access\": \"none\"\n}")));
    }

    @Test
    void testCreateWithDescription() {
        try {
            projectsApi.createProject(
                    new ProjectCreate()
                            .code("PROJ")
                            .title("Project title")
                            .description("Awesome project")
                            .access(ProjectCreate.AccessEnum.NONE));
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/project"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(
                        equalToJson("{\n  \"code\": \"PROJ\",\n  " +
                                "\"title\": \"Project title\",\n  " +
                                "\"description\": \"Awesome project\",\n  " +
                                "\"access\": \"none\"\n}")));
    }

    @Test
    void createWithParams() {
        try {
            projectsApi.createProject(
                    new ProjectCreate()
                            .code("PROJ")
                            .title("Project title")
                            .description("Awesome project")
                            .access(ProjectCreate.AccessEnum.GROUP)
                            .group("groupHash"));
        } catch (QaseException e) {
            //ignore
        }
        verify(postRequestedFor(urlPathEqualTo("/v1/project"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json; charset=UTF-8"))
                .withRequestBody(
                        equalToJson("{\n  \"code\": \"PROJ\",\n  " +
                                "\"title\": \"Project title\",\n  " +
                                "\"description\": \"Awesome project\",\n  " +
                                "\"access\": \"group\",\n  " +
                                "\"group\": \"groupHash\"\n}")));
    }
}