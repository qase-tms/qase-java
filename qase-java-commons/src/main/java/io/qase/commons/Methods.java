package io.qase.commons;

import io.qase.commons.models.domain.Attachment;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Methods {
    public static void addComment(String message) {
        if (!CasesStorage.isCaseInProgress()) {
            return;
        }

        CasesStorage.getCurrentCase().message = message;
    }

    public static void addAttachments(String... attachments) {
        List<Attachment> attachmentList = Arrays.stream(attachments)
                .map(path -> {
                    Attachment attachment = new Attachment();
                    attachment.filePath = path;
                    return attachment;
                })
                .collect(Collectors.toList());

        if (StepStorage.isStepInProgress()) {
            StepStorage.getCurrentStep().attachments.addAll(attachmentList);
        } else {
            CasesStorage.getCurrentCase().attachments.addAll(attachmentList);
        }
    }

    public static void addAttachment(String fileName, String content, String contentType) {
        Attachment attachment = new Attachment();
        attachment.fileName = fileName;
        attachment.content = content;
        attachment.mimeType = contentType;

        if (StepStorage.isStepInProgress()) {
            StepStorage.getCurrentStep().attachments.add(attachment);
        } else {
            CasesStorage.getCurrentCase().attachments.add(attachment);
        }
    }

    public static void addAttachment(String fileName, byte[] content, String contentType) {
        Attachment attachment = new Attachment();
        attachment.fileName = fileName;
        attachment.contentBytes = content;
        attachment.mimeType = contentType;

        if (StepStorage.isStepInProgress()) {
            StepStorage.getCurrentStep().attachments.add(attachment);
        } else {
            CasesStorage.getCurrentCase().attachments.add(attachment);
        }
    }
}
