package io.qase.client.services;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.google.gson.Gson;
import io.qase.api.CasesStorage;
import io.qase.api.StepStorage;
import io.qase.api.annotation.CaseId;
import io.qase.api.annotation.Step;
import io.qase.api.exceptions.QaseException;
import io.qase.api.services.Attachments;
import io.qase.api.services.QaseTestCaseListener;
import io.qase.api.utils.TestUtils;
import io.qase.client.model.AttachmentGet;
import io.qase.client.model.AttachmentUploadsResponse;
import io.qase.client.model.ResultCreate;
import io.qase.client.model.ResultCreateSteps;
import org.junit.jupiter.api.*;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static io.qase.api.utils.TestUtils.QASE_API_TOKEN;
import static io.qase.api.utils.TestUtils.QASE_PROJECT_CODE;
import static io.qase.configuration.QaseModule.INJECTOR;
import static org.apache.http.entity.mime.MIME.CONTENT_TYPE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

class AttachmentsTest {

    private static final String TEST_HASH = "Test Hash";

    private static final String STEP_VALUE = "Step value";

    private static final long CASE_WITHOUT_STEPS_ID = 1;

    private static final long CASE_WITH_STEPS_ID = 2;

    private static WireMockServer WIREMOCK_SERVER;

    private static QaseTestCaseListener QASE_LISTENER;

    @BeforeAll
    public static void beforeAll() {
        // Wiremock setup
        WIREMOCK_SERVER = new WireMockServer(options().dynamicPort());
        WIREMOCK_SERVER.start();
        int wiremockPort = WIREMOCK_SERVER.port();
        configureFor(wiremockPort);
        WIREMOCK_SERVER.addStubMapping(
            post("/v1/attachment/" + QASE_PROJECT_CODE)
                .willReturn(ok(createSuccessfulSingleAttachmentUploadResponseJson()))
                .build()
        );
        // Environment variables setup
        TestUtils.setupQaseTestEnvironmentVariables(wiremockPort);
        TestUtils.useScreenshotsSending(true);
        // Qase listener setup
        QASE_LISTENER = INJECTOR.getInstance(QaseTestCaseListener.class);
    }

    @AfterAll
    public static void afterAll() {
        WIREMOCK_SERVER.stop();
        WIREMOCK_SERVER = null;
    }

    @AfterEach
    public void afterEach() {
        WIREMOCK_SERVER.resetRequests();
        if (StepStorage.isStepInProgress()) {
            StepStorage.stopStep();
        }
        if (CasesStorage.isCaseInProgress()) {
            CasesStorage.stopCase();
        }
    }

    @CaseId(CASE_WITHOUT_STEPS_ID)
    public void caseWithAttachmentsWithoutSteps() throws QaseException {
        QASE_LISTENER.onTestCaseStarted();
        Attachments.addAttachmentsToCurrentContext(getTestAttachments());
        // No QASE_LISTENER.onTestCaseFinished() for being able to verify CaseStorage.getCurrentCase()
    }

    @CaseId(CASE_WITH_STEPS_ID)
    public void caseWithAttachmentsWithSteps() throws QaseException {
        QASE_LISTENER.onTestCaseStarted();
        stepWithAttachments();
        // No QASE_LISTENER.onTestCaseFinished() for being able to verify CaseStorage.getCurrentCase()
    }

    @Step(STEP_VALUE)
    public void stepWithAttachments() throws QaseException {
        Attachments.addAttachmentsToCurrentContext(getTestAttachments());
    }

    @Test
    public void addAttachmentsToCurrentContext_addInStep_attachmentAssociatedWithStep() throws QaseException {
        stepWithAttachments();

        verifyAttachmentHasBeenSent();
        assertStepsStorageContainsTestAttachment();
        assertFalse(CasesStorage.isCaseInProgress());
    }

    @Test
    public void addAttachmentsToCurrentContext_addInCase_attachmentAssociatedWithCase() throws QaseException {
        caseWithAttachmentsWithoutSteps();

        verifyAttachmentHasBeenSent();
        ResultCreate resultCreate = CasesStorage.getCurrentCase();
        Assertions.assertEquals(resultCreate.getAttachments().size(), 1);
        Assertions.assertEquals(resultCreate.getAttachments().get(0), TEST_HASH);
    }

    @Test
    public void addAttachmentsToCurrentContext_addInCaseStep_attachmentAssociatedWithStep() throws QaseException {
        caseWithAttachmentsWithSteps();

        verifyAttachmentHasBeenSent();
        assertStepsStorageContainsTestAttachment();
        assertThat(CasesStorage.getCurrentCase().getAttachments(), anyOf(nullValue(), is(empty())));
    }

    private void assertStepsStorageContainsTestAttachment() {
        LinkedList<ResultCreateSteps> resultCreateSteps = StepStorage.stopSteps();
        Assertions.assertEquals(resultCreateSteps.size(), 1);
        Assertions.assertEquals(resultCreateSteps.get(0).getAttachments().size(), 1);
        Assertions.assertEquals(resultCreateSteps.get(0).getAttachments().get(0), TEST_HASH);
    }

    private void verifyAttachmentHasBeenSent() {
        verify(postRequestedFor(urlPathEqualTo("/v1/attachment/" + QASE_PROJECT_CODE))
            .withHeader("Token", equalTo(QASE_API_TOKEN))
            .withHeader(CONTENT_TYPE, containing("multipart/form-data;"))
            .withAnyRequestBodyPart(aMultipart().withName("file")));
    }

    private static Collection<File> getTestAttachments() {
        return Collections.singletonList(Paths.get("src/test/resources/logo.jpg").toFile());
    }

    private static String createSuccessfulSingleAttachmentUploadResponseJson() {
        AttachmentUploadsResponse attachmentUploadsResponse = new AttachmentUploadsResponse();
        attachmentUploadsResponse.setStatus(true);
        attachmentUploadsResponse.setResult(Collections.singletonList(
            new AttachmentGet().hash(TEST_HASH)
        ));

        return new Gson().toJson(attachmentUploadsResponse);
    }
}
