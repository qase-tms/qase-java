package io.qase.api.utils;

import java.net.URI;
import java.util.List;

import static io.qase.api.utils.IntegrationUtils.CASE_TAGS;

public final class CucumberUtils {

    private CucumberUtils() throws IllegalAccessException {
        throw new IllegalAccessException("Utils class");
    }

    public static Long getCaseId(List<String> tags) {
        for (String tag : tags) {
            String[] split = tag.split("=");
            if (CASE_TAGS.contains(split[0]) && split.length == 2 &&
                    (split[1].matches("\\d+") || split[1].matches("\\D+-\\d+"))) {
                return Long.valueOf(split[1].replaceAll("\\D+", ""));
            }
        }
        return null;
    }

    public static int getHash(URI uri, Long line) {
        return (uri.toString() + line).hashCode();
    }
}
