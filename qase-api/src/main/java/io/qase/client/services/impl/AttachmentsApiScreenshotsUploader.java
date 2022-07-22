package io.qase.client.services.impl;

import io.qase.api.QaseClient;
import io.qase.api.exceptions.QaseException;
import io.qase.client.api.AttachmentsApi;
import io.qase.client.model.AttachmentGet;
import io.qase.client.services.ScreenshotsSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

import static io.qase.api.config.QaseConfig.*;
import static io.qase.utils.FileUtils.getFileExtension;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Slf4j
public class AttachmentsApiScreenshotsUploader implements ScreenshotsSender {

    private static final String QASE_SCREENSHOT_EXTENSIONS_DELIMITER = ",";

    private final AttachmentsApi attachmentsApi;

    {
        validateQaseScreenshotsExtensions();
    }

    @Override
    public void sendScreenshotsIfPermitted() throws QaseException {
        if (!QaseClient.getConfig().screenshotSendingPermitted()) {
            log.warn(
                "Screenshots sending is not permitted. Consider enabling it by setting {} to true.",
                QASE_SCREENSHOT_SENDING_KEY
            );
            return;
        }

        List<File> screenshots = getScreenshots();
        if (!screenshots.isEmpty()) {
            List<AttachmentGet> uploadResult = attachmentsApi.uploadAttachment(
                QaseClient.getConfig().projectCode(),
                screenshots
            ).getResult();
            log.info("Screenshots uploading result: {}", uploadResult);
        } else {
            log.warn("No screenshots found in screenshots folder {}", QaseClient.getConfig().screenshotFolder());
        }
    }

    private List<File> getScreenshots() {
        File screenshotsDirectory = new File(QaseClient.getConfig().screenshotFolder());
        File[] screenshots = ofNullable(screenshotsDirectory.listFiles(AttachmentsApiScreenshotsUploader::isScreenshot))
            .orElseThrow(() -> new IllegalArgumentException(
                QASE_SCREENSHOT_FOLDER_KEY + " does not denote a directory." +
                    " Consider checking if the directory exists and it is accessible by the application."
            ));
        return Arrays.asList(screenshots);
    }

    private static boolean isScreenshot(File file) {
        return file.isFile()
            && Arrays.stream(QaseClient.getConfig().screenshotsExtensions().split(QASE_SCREENSHOT_EXTENSIONS_DELIMITER))
            .anyMatch(extension -> getFileExtension(file).equalsIgnoreCase(extension));
    }

    private static void validateQaseScreenshotsExtensions() {
        Pattern qaseScreenshotsExtensionsPattern = Pattern.compile("(\\S+)(,\\S+)*", Pattern.CASE_INSENSITIVE);
        String qaseScreenshotsExtensionsValue = QaseClient.getConfig().screenshotsExtensions();
        boolean isVariableOfValidFormat =
            qaseScreenshotsExtensionsPattern.matcher(qaseScreenshotsExtensionsValue).matches();
        if (!isVariableOfValidFormat) {
            String errorMessage = String.format(
                "%s is expected to be a coma-separated list of extensions, but found: %s",
                QASE_SCREENSHOT_EXTENSIONS_KEY,
                qaseScreenshotsExtensionsValue
            );
            throw new IllegalStateException(errorMessage);
        }
    }
}
