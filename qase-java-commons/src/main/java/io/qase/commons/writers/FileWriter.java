package io.qase.commons.writers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.qase.commons.QaseException;
import io.qase.commons.config.ConnectionConfig;
import io.qase.commons.config.Format;
import io.qase.commons.logger.Logger;
import io.qase.commons.models.domain.Attachment;
import io.qase.commons.models.domain.ThrowableAdapter;
import io.qase.commons.models.report.ReportAttachment;
import io.qase.commons.models.report.ReportResult;
import io.qase.commons.models.report.Run;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWriter implements Writer {
    private static final Logger logger = Logger.getInstance();

    private final String rootPath;
    final String resultsPath;
    final String attachmentPath;
    final Format format;

    public FileWriter(ConnectionConfig config) {
        this.format = config.local.format;
        this.rootPath = Paths.get(System.getProperty("user.dir"), config.local.path).toString();
        this.resultsPath = Paths.get(this.rootPath, "results").toString();
        this.attachmentPath = Paths.get(this.rootPath, "attachments").toString();
    }

    @Override
    public void prepare() throws QaseException {
        Path path = Paths.get(this.rootPath);
        this.createDirectory(path);

        Path resultsPath = Paths.get(this.resultsPath);
        this.createDirectory(resultsPath);

        Path attachmentPath = Paths.get(this.attachmentPath);
        this.createDirectory(attachmentPath);
    }

    private Gson createGson() {
        return new GsonBuilder()
                .registerTypeHierarchyAdapter(Throwable.class, new ThrowableAdapter())
                .create();
    }

    @Override
    public void writeRun(Run run) throws QaseException {
        Gson gson = createGson();
        String path = Paths.get(this.rootPath, "run.json").toString();
        File file = new File(path);

        try (java.io.FileWriter fileWriter = new java.io.FileWriter(file)) {
            String content = gson.toJson(run);
            if (this.format == Format.JSONP) {
                content = "qaseJsonp(" + content + ");";
            }
            fileWriter.write(content);
        } catch (IOException e) {
            throw new QaseException("Failed to write run result to file: " + path, e.getCause());
        }
    }

    @Override
    public void writeResult(ReportResult result) throws QaseException {
        Gson gson = createGson();
        String path = Paths.get(this.resultsPath, (result.id + ".json")).toString();
        File file = new File(path);

        try (java.io.FileWriter fileWriter = new java.io.FileWriter(file)) {
            String content = gson.toJson(result);
            if (this.format == Format.JSONP) {
                content = "qaseJsonp(" + content + ");";
            }
            fileWriter.write(content);
        } catch (IOException e) {
            throw new QaseException("Failed to write report result to file: " + path, e.getCause());
        }
    }

    @Override
    public ReportAttachment writeAttachment(Attachment attachment) {
        if (attachment == null) {
            return null;
        }

        ReportAttachment reportAttachment = new ReportAttachment();
        reportAttachment.id = attachment.id;
        reportAttachment.fileName = attachment.fileName;
        reportAttachment.mimeType = attachment.mimeType;

        if (attachment.filePath != null) {
            Path source = Paths.get(attachment.filePath);
            Path destination = Paths.get(this.attachmentPath, (attachment.id + "-" + source.getFileName().toString()));

            try {
                Files.copy(source, destination);
                reportAttachment.filePath = destination.toString();
                reportAttachment.size = Files.size(destination);
                
                // Set file name from source if not already set
                if (reportAttachment.fileName == null) {
                    reportAttachment.fileName = source.getFileName().toString();
                }
                
                return reportAttachment;
            } catch (IOException e) {
                logger.error("Failed to save attachment: {}", attachment.filePath, e);
                return null;
            }
        }

        // Handle content or contentBytes
        if (attachment.content != null || attachment.contentBytes != null) {
            // Generate a file name if not provided
            String fileName = attachment.fileName;
            if (fileName == null || fileName.isEmpty()) {
                fileName = "attachment";
            }
            
            String destinationPath = Paths.get(this.attachmentPath, (attachment.id + "-" + fileName)).toString();
            
            try {
                if (attachment.content != null) {
                    File file = new File(destinationPath);
                    try (java.io.FileWriter fileWriter = new java.io.FileWriter(file)) {
                        fileWriter.write(attachment.content);
                        reportAttachment.filePath = destinationPath;
                        reportAttachment.size = (long) attachment.content.length();
                        reportAttachment.fileName = fileName;
                    }
                } else if (attachment.contentBytes != null) {
                    Files.write(Paths.get(destinationPath), attachment.contentBytes);
                    reportAttachment.filePath = destinationPath;
                    reportAttachment.size = (long) attachment.contentBytes.length;
                    reportAttachment.fileName = fileName;
                }
                
                return reportAttachment;
            } catch (IOException e) {
                logger.error("Failed to write attachment content to file: {}", e.getMessage(), e);
                return null;
            }
        }
        
        // No valid content provided - return null instead of incomplete object
        logger.error("Attachment {} has no valid content (filePath, content, or contentBytes)", attachment.id);
        return null;
    }

    private void createDirectory(Path path) throws QaseException {
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                throw new QaseException("Failed to create directory for report: " + path, e.getCause());
            }
        }
    }
}
