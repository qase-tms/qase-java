package io.qase.api.utils;

import io.qase.api.QaseClient;

import static io.qase.api.config.QaseConfig.*;

/**
 * this class is needed only for tests
 */
public final class TestUtils {
    private TestUtils() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static void useBulk(boolean use) {
        System.setProperty(USE_BULK_KEY, String.valueOf(use));
        QaseClient.getConfig().reload();
    }

    public static void useRunAutocomplete(boolean use) {
        System.setProperty(RUN_AUTOCOMPLETE_KEY, String.valueOf(use));
        QaseClient.getConfig().reload();
    }

    public static void useScreenshotsSending(boolean use) {
        System.setProperty(QASE_SCREENSHOT_SENDING_KEY, String.valueOf(use));
        if (use) {
            System.setProperty(QASE_SCREENSHOT_FOLDER_KEY, "src/test/resources");
        }
        QaseClient.getConfig().reload();
    }
}
