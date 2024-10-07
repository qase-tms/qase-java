package io.qase.api;

import io.qase.api.config.QaseConfig;
import io.qase.client.v1.ApiException;
import io.qase.client.v1.ApiClient;
import io.qase.client.v1.api.RunsApi;
import io.qase.client.v1.models.RunCreate;
import org.aeonbits.owner.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.qase.api.Messages.REQUIRED_PARAMETERS_WARNING_MESSAGE;
import static io.qase.api.Messages.REQUIRED_PARAMETER_WARNING_MESSAGE;
import static io.qase.api.config.QaseConfig.*;

public final class QaseClient {
    private static final Logger logger = LoggerFactory.getLogger(QaseClient.class);
    private static final QaseConfig qaseConfig = ConfigFactory.create(QaseConfig.class);
    private static boolean isEnabled = qaseConfig.isEnabled();
    private static final ThreadLocal<ApiClient> apiClient = new InheritableThreadLocal<>();

    private static ApiClient initApiClient() {
        ApiClient client = new ApiClient();
        String apiToken = getConfig().apiToken();
        if (apiToken == null) {
            isEnabled = false;
            logger.info(REQUIRED_PARAMETER_WARNING_MESSAGE, API_TOKEN_KEY);
        }
        client.setBasePath(getConfig().baseUrl());
        client.setApiKey(apiToken);

        if (getConfig().projectCode() == null) {
            isEnabled = false;
            logger.info(REQUIRED_PARAMETER_WARNING_MESSAGE, PROJECT_CODE_KEY);
        }
        logger.info("Qase project code - {}", getConfig().projectCode());

        if (getConfig().runId() == null) {
            if (getConfig().runName() == null) {
                isEnabled = false;
                logger.info(REQUIRED_PARAMETERS_WARNING_MESSAGE, RUN_ID_KEY, RUN_NAME_KEY);
            } else {
                Long id;
                try {
                    id = new RunsApi(client).createRun(getConfig().projectCode(),
                                    new RunCreate()
                                            .title(getConfig().runName())
                                            .description(getConfig().runDescription())
                                            .isAutotest(true))
                            .getResult().getId();
                    getConfig().setProperty(RUN_ID_KEY, String.valueOf(id));
                    logger.info("Qase run id - {}", getConfig().runId());
                } catch (ApiException e) {
                    isEnabled = false;
                    logger.error(e.getMessage());
                }
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
        return isEnabled;
    }

    public static void setEnabled(boolean isEnabled) {
        QaseClient.isEnabled = isEnabled;
    }
}
