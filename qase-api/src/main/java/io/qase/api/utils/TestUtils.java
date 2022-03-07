package io.qase.api.utils;

import io.qase.api.QaseClient;

import static io.qase.api.config.QaseConfig.USE_BULK_KEY;

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
}
