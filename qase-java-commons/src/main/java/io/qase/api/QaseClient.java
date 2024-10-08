package io.qase.api;

import io.qase.api.config.ConfigFactory;
import io.qase.api.config.Mode;
import io.qase.api.config.QaseConfig;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.api.RunsApi;
import io.qase.client.v1.models.RunCreate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class QaseClient {
    private static final Logger logger = LoggerFactory.getLogger(QaseClient.class);
    private static final QaseConfig qaseConfig = ConfigFactory.loadConfig();
    private static final ThreadLocal<ApiClient> apiClient = new InheritableThreadLocal<>();

    private static ApiClient initApiClient() {
        ApiClient client = new ApiClient();

        client.setBasePath(qaseConfig.testops.api.host);
        client.setApiKey(qaseConfig.testops.api.token);

        logger.info("Qase project code - {}", qaseConfig.testops.project);

        if (qaseConfig.testops.run.id == 0) {
            Long id;
            try {
                RunCreate model = new RunCreate().title(qaseConfig.testops.run.title).isAutotest(true);

                if (qaseConfig.testops.run.description != null) {
                    model.setDescription(qaseConfig.testops.run.description);
                }

                if (qaseConfig.environment != null) {
                    model.setEnvironmentSlug(qaseConfig.environment);
                }

                if (qaseConfig.testops.plan.id != 0) {
                    model.setPlanId((long) qaseConfig.testops.plan.id);
                }

                id = new RunsApi(client).createRun(qaseConfig.testops.project, model).getResult().getId();

                qaseConfig.testops.run.id = id.intValue();

                logger.info("Qase run id - {}", id);
            } catch (ApiException e) {
                qaseConfig.mode = Mode.OFF;
                logger.error("Failed to create a run in Qase: {}", e.getResponseBody());
            }
        }

        return client;
    }

    public static QaseConfig getConfig() {
        return qaseConfig;
    }

    public static ApiClient getApiClient() {
        if (apiClient.get() == null) {
            apiClient.set(initApiClient());
        }
        return apiClient.get();
    }

    public static void setApiClient(ApiClient client) {
        apiClient.set(client);
    }

    public static boolean isEnabled() {
        return qaseConfig.mode != Mode.OFF;
    }
}
