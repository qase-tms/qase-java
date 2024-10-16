package io.qase.commons.reporters;

import io.qase.commons.config.ConfigFactory;
import io.qase.commons.config.QaseConfig;

public class CoreReporterFactory {
    private static CoreReporter instance;

    private CoreReporterFactory() {
    }

    public static synchronized CoreReporter getInstance() {
        if (instance == null) {
            QaseConfig config = ConfigFactory.loadConfig();
            instance = new CoreReporter(config);
        }
        return instance;
    }
}
