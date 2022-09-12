package io.qase.api.config.apiclient;

import io.qase.client.ApiClient;

public interface ApiClientConfigurer {

    void configure(ApiClient apiClient);
}
