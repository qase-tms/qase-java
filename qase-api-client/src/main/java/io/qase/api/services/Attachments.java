package io.qase.api.services;

import io.qase.api.CasesStorage;
import io.qase.api.StepStorage;
import io.qase.api.annotation.QaseId;
import io.qase.api.annotation.Step;
import io.qase.api.config.QaseConfig;
import io.qase.api.exceptions.QaseException;
import io.qase.api.exceptions.UncheckedQaseException;
import io.qase.client.api.AttachmentsApi;
import io.qase.client.model.AttachmentGet;
import io.qase.client.model.AttachmentUploadsResponse;
import io.qase.guice.Injectors;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class Attachments {

    private static final AttachmentsApi ATTACHMENTS_API = createAttachmentsApi();

    /**
     * Adds attachments to the current context.
     * The context could be either {@link io.qase.api.annotation.QaseId} or {@link io.qase.api.annotation.Step}
     *
     * @throws QaseException if the invocation context can not be found
     * */
    public static void addAttachmentsToCurrentContext(Collection<File> attachments) throws QaseException {
        AttachmentContext attachmentContext = lookupCurrentContext();
        AttachmentUploadsResponse attachmentUploadsResponse = ATTACHMENTS_API.uploadAttachment(
            System.getProperty(QaseConfig.PROJECT_CODE_KEY),
            new ArrayList<>(attachments)
        );
        Collection<String> attachmentIds = Optional.ofNullable(attachmentUploadsResponse.getResult())
            .orElseGet(ArrayList::new)
            .stream()
            .map(AttachmentGet::getHash)
            .collect(Collectors.toList());
        switch (attachmentContext) {
            case TEST_STEP:
                addAttachmentsToCurrentTestStep(attachmentIds);
                break;
            case TEST_CASE:
                addAttachmentsToCurrentTestCase(attachmentIds);
                break;
        }
    }

    public static void addAttachmentsToCurrentContext(File... attachments) throws QaseException {
        addAttachmentsToCurrentContext(Arrays.asList(attachments));
    }

    private static void addAttachmentsToCurrentTestCase(Collection<String> attachmentIds) {
        addAttachmentsToCollection(
            () -> CasesStorage.getCurrentCase().getAttachments(),
            absolutePaths -> CasesStorage.getCurrentCase().setAttachments(absolutePaths),
            attachmentIds
        );
    }

    private static void addAttachmentsToCurrentTestStep(Collection<String> attachmentsIds) {
        addAttachmentsToCollection(
            () -> StepStorage.getCurrentStep().getAttachments(),
            absolutePaths -> StepStorage.getCurrentStep().setAttachments(absolutePaths),
            attachmentsIds
        );
    }

    private static void addAttachmentsToCollection(
        Supplier<List<String>> collectionGetter,
        Consumer<List<String>> collectionSetter,
        Collection<String> attachmentsIds
    ) {
        List<String> currentStepAttachments = Optional.ofNullable(collectionGetter.get())
            .orElseGet(ArrayList::new);
        currentStepAttachments.addAll(attachmentsIds);
        collectionSetter.accept(currentStepAttachments);
    }

    private static AttachmentContext lookupCurrentContext() {
        if (StepStorage.isStepInProgress()) {
            return AttachmentContext.TEST_STEP;
        }
        if (CasesStorage.getCurrentCase() != null) {
            return AttachmentContext.TEST_CASE;
        }
        throw new UncheckedQaseException(new QaseException(String.format(
            "It is expected either %s or %s-annotated method be called.", Step.class.getName(), QaseId.class.getName()
        )));
    }

    private enum AttachmentContext {
        TEST_CASE, TEST_STEP
    }

    private static AttachmentsApi createAttachmentsApi() {
        // TODO: make this very particular default injector aware about the custom modules in subprojects
        return Injectors.createDefaultInjector().getInstance(AttachmentsApi.class);
    }
}
