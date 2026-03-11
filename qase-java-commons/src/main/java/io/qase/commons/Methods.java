package io.qase.commons;

import io.qase.commons.logger.Logger;
import io.qase.commons.models.domain.Attachment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Methods {
    private static final Logger logger = Logger.getInstance();

    private static String sanitizeForFileName(String fileName) {
        if (fileName == null) return "unknown";
        return fileName.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

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
                    attachment.isStagedTempFile = false;
                    attachment.sizeBytes = new File(path).length();
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
        attachment.mimeType = contentType;
        attachment.sizeBytes = content.length();

        try {
            File tempFile = File.createTempFile("qase-attachment-", "-" + sanitizeForFileName(fileName));
            tempFile.deleteOnExit();
            try (java.io.FileWriter fw = new java.io.FileWriter(tempFile)) {
                fw.write(content);
            }
            attachment.filePath = tempFile.getAbsolutePath();
            attachment.isStagedTempFile = true;
        } catch (IOException e) {
            logger.warn("Failed to stage attachment to temp file, keeping in memory: %s", e.getMessage());
            attachment.content = content;
        }

        if (StepStorage.isStepInProgress()) {
            StepStorage.getCurrentStep().attachments.add(attachment);
        } else {
            CasesStorage.getCurrentCase().attachments.add(attachment);
        }
    }

    public static void addAttachment(String fileName, byte[] content, String contentType) {
        Attachment attachment = new Attachment();
        attachment.fileName = fileName;
        attachment.mimeType = contentType;
        attachment.sizeBytes = content.length;

        try {
            File tempFile = File.createTempFile("qase-attachment-", "-" + sanitizeForFileName(fileName));
            tempFile.deleteOnExit();
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(content);
            }
            attachment.filePath = tempFile.getAbsolutePath();
            attachment.isStagedTempFile = true;
        } catch (IOException e) {
            logger.warn("Failed to stage attachment to temp file, keeping in memory: %s", e.getMessage());
            attachment.contentBytes = content;
        }

        if (StepStorage.isStepInProgress()) {
            StepStorage.getCurrentStep().attachments.add(attachment);
        } else {
            CasesStorage.getCurrentCase().attachments.add(attachment);
        }
    }
}
