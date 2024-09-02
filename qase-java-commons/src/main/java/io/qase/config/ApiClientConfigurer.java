package io.qase.config;

import io.qase.client.ApiClient;

public interface ApiClientConfigurer {

    void configure(ApiClient apiClient);
}
