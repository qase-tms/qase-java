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
        return getInstance(null, null, null, null);
    }

    public static synchronized CoreReporter getInstance(String reporterName, String reporterVersion,
                                                        String frameworkName, String frameworkVersion) {
        if (instance == null) {
            QaseConfig config = ConfigFactory.loadConfig();
            
            // Configure logger based on config
            if(config.debug){
                logger.setGlobalLogLevel(Logger.LogLevel.DEBUG);
            }
            
            // Apply logging configuration
            logger.setConsoleEnabled(config.logging.console);
            logger.setFileEnabled(config.logging.file || config.debug);
            
            logger.debug("Qase config: %s", config);
            HostInfo hostInfoCollector = new HostInfo();
            Map<String, String> hostInfo = hostInfoCollector.getHostInfo(
                reporterVersion != null ? reporterVersion : CoreReporterFactory.class.getPackage().getImplementationVersion(),
                frameworkName, frameworkVersion);
            logger.debug("Using host info: %s", hostInfo.toString());
            instance = new CoreReporter(config, reporterName, reporterVersion, frameworkName, frameworkVersion, hostInfo);
            registerShutdownHook(instance);
        }
        return instance;
    }

    // Some runners (e.g. JUnit4 under Gradle) never signal the end of the whole run,
    // so a started run is completed when the JVM exits. It is a no-op if the listener already completed it.
    private static void registerShutdownHook(CoreReporter reporter) {
        try {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    reporter.completeTestRun();
                } catch (Exception e) {
                    logger.error("Failed to complete test run on shutdown: %s", e.getMessage());
                }
            }, "qase-shutdown"));
        } catch (IllegalStateException | SecurityException e) {
            logger.warn("Unable to register shutdown hook: %s", e.getMessage());
        }
    }
}
