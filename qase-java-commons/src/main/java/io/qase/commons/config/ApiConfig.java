package io.qase.commons.config;

public class ApiConfig {
    public String host = "qase.io";
    public String token = "";
    public int timeoutSeconds = 0; // APIR-02: 0 = use OkHttp default (10s)
}
