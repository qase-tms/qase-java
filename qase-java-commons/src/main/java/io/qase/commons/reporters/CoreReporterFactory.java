package io.qase.commons.reporters;

import io.qase.commons.config.ConfigFactory;
import io.qase.commons.config.QaseConfig;
import io.qase.commons.logger.Logger;
import io.qase.commons.utils.HostInfo;


import java.util.Map;

public class CoreReporterFactory {
    private static final Logger logger = Logger.getInstance();
    private static CoreReporter instance;

    private CoreReporterFactory() {
    }

    public static synchronized CoreReporter getInstance() {
        if (instance == null) {
            QaseConfig config = ConfigFactory.loadConfig();
            if(config.debug){
                logger.setGlobalLogLevel(Logger.LogLevel.DEBUG);
            }
            logger.debug("Qase config: %s", config);
            HostInfo hostInfoCollector = new HostInfo();
            Map<String, String> hostInfo = hostInfoCollector.getHostInfo(CoreReporterFactory.class.getPackage().getImplementationVersion());
            logger.debug("Using host info: %s", hostInfo.toString());
            instance = new CoreReporter(config);
        }
        return instance;
    }
}
