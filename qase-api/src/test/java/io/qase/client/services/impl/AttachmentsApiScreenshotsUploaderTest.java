package io.qase.client.services.impl;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.api.QaseClient;
import io.qase.api.exceptions.QaseException;
import io.qase.client.api.AttachmentsApi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.qase.api.utils.TestUtils.useScreenshotsSending;
import static org.junit.jupiter.api.Assertions.*;

class AttachmentsApiScreenshotsUploaderTest {

    static final WireMockServer wireMockServer = new WireMockServer(options().dynamicPort());
    static int port;

    @BeforeAll
    static void setUp() {
        wireMockServer.start();
        port = wireMockServer.port();
        configureFor(port);
        System.setProperty("QASE_ENABLE", "true");
        System.setProperty("QASE_PROJECT_CODE", "PRJ");
        System.setProperty("QASE_RUN_ID", "777");
        System.setProperty("QASE_API_TOKEN", "secret-token");
        System.setProperty("QASE_URL", "http://localhost:" + port + "/v1");
    }

    @Test
    public void sendScreenshotsIfPermitted_permitted_sendPerformed() throws QaseException {
        useScreenshotsSending(true);
        wireMockServer.addStubMapping(stubFor(post("/v1/attachment/PRJ").willReturn(ok("{}"))));
        AttachmentsApiScreenshotsUploader uploader =
            new AttachmentsApiScreenshotsUploader(new AttachmentsApi(QaseClient.getApiClient()));

        uploader.sendScreenshotsIfPermitted();

        verify(postRequestedFor(urlPathEqualTo("/v1/attachment/PRJ"))
            .withHeader("Token", equalTo("secret-token"))
            .withHeader("Content-Type", matching("\\Qmultipart/form-data; boundary=\\E.+")));
    }
}
