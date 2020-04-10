package io.qase.api.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.api.QaseApi;
import io.qase.api.enums.*;
import io.qase.api.exceptions.QaseException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

class TestCaseServiceTest {
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
            qaseApi.testCases().getAll("PRJ");
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/case/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0")));
    }

    @Test
    void getAllWithParams() {
        try {
            qaseApi.testCases().getAll("PRJ", 50, 5, qaseApi.testCases().filter());
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/case/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("50"))
                .withQueryParam("offset", equalTo("5")));
    }

    @Test
    void getAllWithFilter() {
        try {
            TestCaseService.Filter filter = qaseApi.testCases().filter()
                    .automation(Automation.is_not_automated, Automation.to_be_automated)
                    .behavior(Behavior.positive)
                    .milestoneId(11)
                    .suiteId(2)
                    .severity(Severity.critical)
                    .priority(Priority.high, Priority.medium)
                    .status(Status.actual)
                    .type(Type.functional, Type.acceptance)
                    .search("title");
            qaseApi.testCases().getAll("PRJ", filter);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/case/PRJ"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json"))
                .withQueryParam("limit", equalTo("100"))
                .withQueryParam("offset", equalTo("0"))
                .withQueryParam("filters[milestone_id]", equalTo("11"))
                .withQueryParam("filters[severity]", equalTo("critical"))
                .withQueryParam("filters[behavior]", equalTo("positive"))
                .withQueryParam("filters[automation]", equalTo("is-not-automated,to-be-automated"))
                .withQueryParam("filters[search]", equalTo("title"))
                .withQueryParam("filters[priority]", equalTo("high,medium"))
                .withQueryParam("filters[type]", equalTo("functional,acceptance"))
                .withQueryParam("filters[status]", equalTo("actual"))
                .withQueryParam("filters[suite_id]", equalTo("2")));
    }

    @Test
    void get() {
        try {
            qaseApi.testCases().get("PRJ", 8);
        } catch (QaseException e) {
            //ignore
        }
        verify(getRequestedFor(urlPathEqualTo("/v1/case/PRJ/8"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }

    @Test
    void delete() {
        try {
            qaseApi.testCases().delete("PRJ", 8);
        } catch (QaseException e) {
            //ignore
        }
        verify(deleteRequestedFor(urlPathEqualTo("/v1/case/PRJ/8"))
                .withHeader("Token", equalTo("secret-token"))
                .withHeader("Content-Type", equalTo("application/json")));
    }
}