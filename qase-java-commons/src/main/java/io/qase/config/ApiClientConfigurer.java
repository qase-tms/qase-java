package io.qase.config;

import io.qase.client.v1.ApiClient;

public interface ApiClientConfigurer {

    void configure(ApiClient apiClient);
}
