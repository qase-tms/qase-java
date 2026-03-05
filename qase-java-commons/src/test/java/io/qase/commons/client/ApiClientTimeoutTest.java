package io.qase.commons.client;

import io.qase.commons.config.QaseConfig;
import io.qase.commons.config.TestopsConfig;
import io.qase.commons.config.ApiConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApiClientTimeoutTest {

    @AfterEach
    void cleanUp() {
        System.clearProperty("QASE_TESTOPS_API_TIMEOUT_SECONDS");
    }

    private QaseConfig createConfigWithTimeout(int timeoutSeconds) {
        QaseConfig config = new QaseConfig();
        config.testops = new TestopsConfig();
        config.testops.project = "TEST";
        config.testops.api.token = "test_token";
        config.testops.api.host = "qase.io";
        config.testops.api.timeoutSeconds = timeoutSeconds;
        return config;
    }

    @Test
    void timeoutAppliedToV1Client() {
        QaseConfig config = createConfigWithTimeout(30);
        ApiClientV1 client = new ApiClientV1(config);

        io.qase.client.v1.ApiClient apiClient = client.getApiClient();
        assertEquals(30000, apiClient.getConnectTimeout(), "connect timeout should be 30000ms");
        assertEquals(30000, apiClient.getReadTimeout(), "read timeout should be 30000ms");
        assertEquals(30000, apiClient.getWriteTimeout(), "write timeout should be 30000ms");
    }

    @Test
    void timeoutAppliedToV2Client() {
        QaseConfig config = createConfigWithTimeout(30);
        ApiClientV2 client = new ApiClientV2(config);

        io.qase.client.v2.ApiClient apiClient = client.getV2ApiClient();
        assertEquals(30000, apiClient.getConnectTimeout(), "connect timeout should be 30000ms");
        assertEquals(30000, apiClient.getReadTimeout(), "read timeout should be 30000ms");
        assertEquals(30000, apiClient.getWriteTimeout(), "write timeout should be 30000ms");
    }

    @Test
    void defaultTimeoutNotOverridden() {
        QaseConfig config = createConfigWithTimeout(0);
        ApiClientV1 client = new ApiClientV1(config);

        io.qase.client.v1.ApiClient apiClient = client.getApiClient();
        // OkHttp default is 10000ms
        assertEquals(10000, apiClient.getConnectTimeout(), "default connect timeout should be 10000ms");
        assertEquals(10000, apiClient.getReadTimeout(), "default read timeout should be 10000ms");
        assertEquals(10000, apiClient.getWriteTimeout(), "default write timeout should be 10000ms");
    }

    @Test
    void configParsedFromSystemProperty() {
        System.setProperty("QASE_TESTOPS_API_TIMEOUT_SECONDS", "60");

        io.qase.commons.config.QaseConfig loaded = io.qase.commons.config.ConfigFactory.loadConfig();

        assertEquals(60, loaded.testops.api.timeoutSeconds,
                "timeoutSeconds should be 60 when system property is set");
    }
}
