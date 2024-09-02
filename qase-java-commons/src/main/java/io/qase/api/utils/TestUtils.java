package io.qase.api.utils;

import io.qase.api.QaseClient;

import static io.qase.api.config.QaseConfig.*;

/**
 * this class is needed only for tests
 */
public final class TestUtils {

    public static final boolean QASE_ENABLE = true;

    public static final String QASE_PROJECT_CODE = "PRJ";

    public static final int QASE_RUN_ID = 777;

    public static final String QASE_API_TOKEN = "secret-token";

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
        QaseClient.getConfig().reload();
    }

    public static void setupQaseTestEnvironmentVariables(int testServerPort) {
        System.setProperty(ENABLE_KEY, String.valueOf(QASE_ENABLE));
        System.setProperty(PROJECT_CODE_KEY, QASE_PROJECT_CODE);
        System.setProperty(RUN_ID_KEY, String.valueOf(QASE_RUN_ID));
        System.setProperty(API_TOKEN_KEY, QASE_API_TOKEN);
        System.setProperty(BASE_URL_KEY, "http://localhost:" + testServerPort + "/v1");
    }
}
