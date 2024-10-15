package io.qase.commons.config;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Optional;

public class ConfigFactory {
    private static final Logger logger = LoggerFactory.getLogger(ConfigFactory.class);

    private static final String CONFIG_FILE_NAME = "qase.config.json";

    public static QaseConfig loadConfig() {
        // load from file
        QaseConfig qaseConfig = loadFromFile(new QaseConfig());

        // load from env
        qaseConfig = loadFromEnv(qaseConfig);

        // load from system properties
        qaseConfig = loadFromSystemProperties(qaseConfig);

        // validate qaseConfig
        validateConfig(qaseConfig);

        return qaseConfig;
    }

    private static QaseConfig loadFromFile(QaseConfig qaseConfig) {
        if (!checkFile()) {
            return qaseConfig;
        }

        try (FileReader reader = new FileReader(CONFIG_FILE_NAME)) {
            JSONObject fileConfig = new JSONObject(new JSONTokener(reader));
            applyJsonConfig(qaseConfig, fileConfig);
        } catch (IOException e) {
            logger.error("Error reading qaseConfig file", e);
        }

        return qaseConfig;
    }

    private static boolean checkFile() {
        return new File(System.getProperty("user.dir"), CONFIG_FILE_NAME).exists();
    }

    private static QaseConfig loadFromEnv(QaseConfig qaseConfig) {
        qaseConfig.setMode(getEnv("QASE_MODE", qaseConfig.getMode()));
        qaseConfig.setFallback(getEnv("QASE_FALLBACK", qaseConfig.getFallback()));
        qaseConfig.environment = getEnv("QASE_ENVIRONMENT", qaseConfig.environment);
        qaseConfig.rootSuite = getEnv("QASE_ROOT_SUITE", qaseConfig.rootSuite);
        qaseConfig.debug = getBooleanEnv("QASE_DEBUG", qaseConfig.debug);

        qaseConfig.testops.project = getEnv("QASE_TESTOPS_PROJECT", qaseConfig.testops.project);
        qaseConfig.testops.defect = getBooleanEnv("QASE_TESTOPS_DEFECT", qaseConfig.testops.defect);
        qaseConfig.testops.api.token = getEnv("QASE_TESTOPS_API_TOKEN", qaseConfig.testops.api.token);
        qaseConfig.testops.api.host = getEnv("QASE_TESTOPS_API_HOST", qaseConfig.testops.api.host);
        qaseConfig.testops.run.title = getEnv("QASE_TESTOPS_RUN_TITLE", qaseConfig.testops.run.title);
        qaseConfig.testops.run.description = getEnv("QASE_TESTOPS_RUN_DESCRIPTION", qaseConfig.testops.run.description);
        qaseConfig.testops.run.id = getIntEnv("QASE_TESTOPS_RUN_ID", qaseConfig.testops.run.id);
        qaseConfig.testops.run.complete = getBooleanEnv("QASE_TESTOPS_RUN_COMPLETE", qaseConfig.testops.run.complete);
        qaseConfig.testops.plan.id = getIntEnv("QASE_TESTOPS_PLAN_ID", qaseConfig.testops.plan.id);
        qaseConfig.testops.batch.setSize(getIntEnv("QASE_TESTOPS_BATCH_SIZE", qaseConfig.testops.batch.getSize()));

        qaseConfig.report.setDriver(getEnv("QASE_REPORT_DRIVER", qaseConfig.report.getDriver()));
        qaseConfig.report.connection.local.setFormat(getEnv("QASE_REPORT_CONNECTION_FORMAT", qaseConfig.report.connection.local.getFormat()));
        qaseConfig.report.connection.local.path = getEnv("QASE_REPORT_CONNECTION_PATH", qaseConfig.report.connection.local.path);

        return qaseConfig;
    }

    private static QaseConfig loadFromSystemProperties(QaseConfig qaseConfig) {
        qaseConfig.setMode(getProperty("QASE_MODE", qaseConfig.getMode()));
        qaseConfig.setFallback(getProperty("QASE_FALLBACK", qaseConfig.getFallback()));
        qaseConfig.environment = getProperty("QASE_ENVIRONMENT", qaseConfig.environment);
        qaseConfig.rootSuite = getProperty("QASE_ROOT_SUITE", qaseConfig.rootSuite);
        qaseConfig.debug = getBooleanProperty("QASE_DEBUG", qaseConfig.debug);

        qaseConfig.testops.project = getProperty("QASE_TESTOPS_PROJECT", qaseConfig.testops.project);
        qaseConfig.testops.defect = getBooleanProperty("QASE_TESTOPS_DEFECT", qaseConfig.testops.defect);
        qaseConfig.testops.api.token = getProperty("QASE_TESTOPS_API_TOKEN", qaseConfig.testops.api.token);
        qaseConfig.testops.api.host = getProperty("QASE_TESTOPS_API_HOST", qaseConfig.testops.api.host);
        qaseConfig.testops.run.title = getProperty("QASE_TESTOPS_RUN_TITLE", qaseConfig.testops.run.title);
        qaseConfig.testops.run.description = getProperty("QASE_TESTOPS_RUN_DESCRIPTION", qaseConfig.testops.run.description);
        qaseConfig.testops.run.id = getIntProperty("QASE_TESTOPS_RUN_ID", qaseConfig.testops.run.id);
        qaseConfig.testops.run.complete = getBooleanProperty("QASE_TESTOPS_RUN_COMPLETE", qaseConfig.testops.run.complete);
        qaseConfig.testops.plan.id = getIntProperty("QASE_TESTOPS_PLAN_ID", qaseConfig.testops.plan.id);
        qaseConfig.testops.batch.setSize(getIntProperty("QASE_TESTOPS_BATCH_SIZE", qaseConfig.testops.batch.getSize()));

        qaseConfig.report.setDriver(getProperty("QASE_REPORT_DRIVER", qaseConfig.report.getDriver()));
        qaseConfig.report.connection.local.setFormat(getProperty("QASE_REPORT_CONNECTION_FORMAT", qaseConfig.report.connection.local.getFormat()));
        qaseConfig.report.connection.local.path = getProperty("QASE_REPORT_CONNECTION_PATH", qaseConfig.report.connection.local.path);

        return qaseConfig;
    }

    private static void validateConfig(QaseConfig qaseConfig) {
        if ((qaseConfig.mode == Mode.TESTOPS || qaseConfig.fallback == Mode.TESTOPS) && (qaseConfig.testops.project == null || qaseConfig.testops.api.token == null)) {
            logger.error("Project and API token are required for TestOps mode");
            qaseConfig.mode = Mode.OFF;
            qaseConfig.fallback = Mode.OFF;
        }
    }

    private static String getEnv(String key, String defaultValue) {
        return Optional.ofNullable(System.getenv(key)).orElse(defaultValue);
    }

    private static boolean getBooleanEnv(String key, boolean defaultValue) {
        return Optional.ofNullable(System.getenv(key)).map(Boolean::parseBoolean).orElse(defaultValue);
    }

    private static int getIntEnv(String key, int defaultValue) {
        return Optional.ofNullable(System.getenv(key)).map(Integer::parseInt).orElse(defaultValue);
    }

    private static String getProperty(String key, String defaultValue) {
        return Optional.ofNullable(System.getProperty(key)).orElse(defaultValue);
    }

    private static boolean getBooleanProperty(String key, boolean defaultValue) {
        return Optional.ofNullable(System.getProperty(key)).map(Boolean::parseBoolean).orElse(defaultValue);
    }

    private static int getIntProperty(String key, int defaultValue) {
        return Optional.ofNullable(System.getProperty(key)).map(Integer::parseInt).orElse(defaultValue);
    }

    private static void applyJsonConfig(QaseConfig qaseConfig, JSONObject fileConfig) {
        if (fileConfig.has("mode")) {
            qaseConfig.setMode(fileConfig.getString("mode"));
        }

        if (fileConfig.has("fallback")) {
            qaseConfig.setFallback(fileConfig.getString("fallback"));
        }

        if (fileConfig.has("environment")) {
            qaseConfig.environment = fileConfig.getString("environment");
        }

        if (fileConfig.has("rootSuite")) {
            qaseConfig.rootSuite = fileConfig.getString("rootSuite");
        }

        if (fileConfig.has("debug")) {
            qaseConfig.debug = fileConfig.getBoolean("debug");
        }

        if (fileConfig.has("testops")) {
            JSONObject testOps = fileConfig.getJSONObject("testops");

            if (testOps.has("project")) {
                qaseConfig.testops.project = testOps.getString("project");
            }

            if (testOps.has("defect")) {
                qaseConfig.testops.defect = testOps.getBoolean("defect");
            }

            if (testOps.has("api")) {
                JSONObject api = testOps.getJSONObject("api");

                if (api.has("token")) {
                    qaseConfig.testops.api.token = api.getString("token");
                }

                if (api.has("host")) {
                    qaseConfig.testops.api.host = api.getString("host");
                }
            }

            if (testOps.has("run")) {
                JSONObject run = testOps.getJSONObject("run");

                if (run.has("title")) {
                    qaseConfig.testops.run.title = run.getString("title");
                }

                if (run.has("description")) {
                    qaseConfig.testops.run.description = run.getString("description");
                }

                if (run.has("id")) {
                    qaseConfig.testops.run.id = run.getInt("id");
                }

                if (run.has("complete")) {
                    qaseConfig.testops.run.complete = run.getBoolean("complete");
                }
            }

            if (testOps.has("plan")) {
                JSONObject plan = testOps.getJSONObject("plan");

                if (plan.has("id")) {
                    qaseConfig.testops.plan.id = plan.getInt("id");
                }
            }

            if (testOps.has("batch")) {
                JSONObject batch = testOps.getJSONObject("batch");

                if (batch.has("size")) {
                    qaseConfig.testops.batch.setSize(batch.getInt("size"));
                }
            }
        }

        if (fileConfig.has("report")) {
            JSONObject report = fileConfig.getJSONObject("report");

            if (report.has("driver")) {
                qaseConfig.report.setDriver(report.getString("driver"));
            }

            if (report.has("connection")) {
                JSONObject connection = report.getJSONObject("connection");

                if (connection.has("local")) {
                    JSONObject local = connection.getJSONObject("local");

                    if (local.has("format")) {
                        qaseConfig.report.connection.local.setFormat(local.getString("format"));
                    }

                    if (local.has("path")) {
                        qaseConfig.report.connection.local.path = local.getString("path");
                    }
                }
            }
        }
    }
}
