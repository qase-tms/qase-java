package io.qase.client.services.impl;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.qase.api.QaseClient;
import io.qase.api.exceptions.QaseException;
import io.qase.api.utils.TestUtils;
import io.qase.client.api.AttachmentsApi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.qase.api.utils.TestUtils.useScreenshotsSending;

class AttachmentsApiScreenshotsUploaderTest {

    private static final String EMPTY_JSON_OBJECT = "{}";

    private WireMockServer wireMockServer;
    static int port;

    @BeforeEach
    public void setUp() {
        wireMockServer = new WireMockServer(options().dynamicPort());
        wireMockServer.start();
        int wiremockPort = wireMockServer.port();
        configureFor(wiremockPort);
        TestUtils.setupQaseTestEnvironmentVariables(wiremockPort);
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
        wireMockServer = null;
    }

    @Test
    public void sendScreenshotsIfPermitted_permitted_sendPerformed() throws QaseException {
        final String screenshotsUploadingEndpoint = "/v1/attachment/" + TestUtils.QASE_PROJECT_CODE;
        useScreenshotsSending(true);
        wireMockServer.addStubMapping(stubFor(post(screenshotsUploadingEndpoint).willReturn(ok(EMPTY_JSON_OBJECT))));
        AttachmentsApiScreenshotsUploader uploader =
            new AttachmentsApiScreenshotsUploader(new AttachmentsApi(QaseClient.getApiClient()));

        uploader.sendScreenshotsIfPermitted();

        verify(postRequestedFor(urlPathEqualTo(screenshotsUploadingEndpoint)));
    }
}
