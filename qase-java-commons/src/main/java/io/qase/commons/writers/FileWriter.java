package io.qase.commons.writers;

import com.google.gson.Gson;
import io.qase.commons.QaseException;
import io.qase.commons.config.ConnectionConfig;
import io.qase.commons.config.Format;
import io.qase.commons.models.domain.Attachment;
import io.qase.commons.models.report.ReportResult;
import io.qase.commons.models.report.Run;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileWriter implements Writer {
    private static final Logger logger = LoggerFactory.getLogger(FileWriter.class);

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

    @Override
    public void writeRun(Run run) throws QaseException {
        Gson gson = new Gson();
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
        Gson gson = new Gson();
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
    public String writeAttachment(Attachment attachment) {
        if (attachment.filePath != null) {
            Path source = Paths.get(attachment.filePath);
            Path destination = Paths.get(this.attachmentPath, (attachment.id + "-" + source.getFileName().toString()));

            try {
                Files.copy(source, destination);
                return destination.toString();
            } catch (IOException e) {
                logger.error("Failed to save attachment: {}", attachment.filePath, e);
            }
            return "";
        }

        String destination = Paths.get(this.attachmentPath, (attachment.id + "-" + attachment.fileName)).toString();
        File file = new File(destination);

        try (java.io.FileWriter fileWriter = new java.io.FileWriter(file)) {
            fileWriter.write(attachment.content);
            return destination;
        } catch (IOException e) {
            logger.error("Failed to write attachment content to file: {}", e.getMessage(), e);
            return "";
        }
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
