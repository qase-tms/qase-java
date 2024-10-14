package io.qase.commons.reporters;

import io.qase.commons.config.QaseConfig;

public class CoreReporterFactory {
    private static CoreReporter instance;

    private CoreReporterFactory() {
    }

    public static synchronized CoreReporter getInstance(QaseConfig config) {
        if (instance == null) {
            instance = new CoreReporter(config);
        }
        return instance;
    }
}
