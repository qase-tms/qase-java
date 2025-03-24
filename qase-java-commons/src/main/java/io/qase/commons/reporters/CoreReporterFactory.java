package io.qase.commons.reporters;

import io.qase.commons.config.ConfigFactory;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.utils.HostInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class CoreReporterFactory {
    private static final Logger logger = LoggerFactory.getLogger(CoreReporterFactory.class);
    private static CoreReporter instance;

    private CoreReporterFactory() {
    }

    public static synchronized CoreReporter getInstance() {
        if (instance == null) {
            QaseConfig config = ConfigFactory.loadConfig();
            HostInfo hostInfoCollector = new HostInfo();
            Map<String, String> hostInfo = hostInfoCollector.getHostInfo(CoreReporterFactory.class.getPackage().getImplementationVersion());
            logger.debug("Using host info: {}", hostInfo.toString());
            instance = new CoreReporter(config);
        }
        return instance;
    }
}
