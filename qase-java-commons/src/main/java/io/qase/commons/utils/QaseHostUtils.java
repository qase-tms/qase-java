package io.qase.commons.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class QaseHostUtils {

    private static final String DEFAULT_HOST = "qase.io";

    // Listed explicitly: enterprise hosts share the `api-<name>.qase.io` shape but serve the app
    // on `<name>.qase.io`, so a cluster cannot be inferred from the `api-` prefix.
    private static final Map<String, String> CLUSTER_APP_HOSTS;

    static {
        Map<String, String> hosts = new HashMap<>();
        hosts.put("api-eu.qase.io", "app-eu.qase.io");
        CLUSTER_APP_HOSTS = Collections.unmodifiableMap(hosts);
    }

    /**
     * Returns the web app base URL (with a trailing slash) for the configured {@code testops.api.host}.
     */
    public static String getAppUrl(String host) {
        if (DEFAULT_HOST.equals(host)) {
            return "https://app.qase.io/";
        }

        String appHost = CLUSTER_APP_HOSTS.get(("api-" + host).toLowerCase(Locale.ROOT));
        if (appHost != null) {
            return "https://" + appHost + "/";
        }

        return "https://" + host + "/";
    }
}
