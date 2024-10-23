package io.qase.commons.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.net.URI;
import java.util.*;


public final class CucumberUtils {
    private static final List<String> CASE_TAGS = Collections.unmodifiableList(Arrays.asList("@caseid", "@tmslink", "@qaseid"));
    private static final String QASE_TITLE = "@QaseTitle";
    private static final String QASE_IGNORE = "@QaseIgnore";
    private static final String QASE_FIELDS = "@QaseFields";
    private static final String QASE_SUITE = "@QaseSuite";
    private static final String DELIMITER = "=";

    private CucumberUtils() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }

    public static Long getCaseId(List<String> tags) {
        return tags.stream()
                .map(tag -> tag.split(DELIMITER))
                .filter(split -> CASE_TAGS.contains(split[0].toLowerCase()) && split.length == 2 &&
                        (split[1].matches("\\d+") || split[1].matches("\\D+-\\d+")))
                .map(split -> Long.valueOf(split[1].replaceAll("\\D+", "")))
                .findFirst()
                .orElse(null);
    }

    public static String getCaseTitle(List<String> tags) {
        return tags.stream()
                .map(tag -> tag.split(DELIMITER))
                .filter(split -> QASE_TITLE.equalsIgnoreCase(split[0]) && split.length == 2)
                .map(split -> split[1])
                .findFirst()
                .orElse(null);
    }

    public static String getCaseSuite(List<String> tags) {
        return tags.stream()
                .map(tag -> tag.split(DELIMITER))
                .filter(split -> QASE_SUITE.equalsIgnoreCase(split[0]) && split.length == 2)
                .map(split -> split[1])
                .findFirst()
                .orElse(null);
    }

    public static boolean getCaseIgnore(List<String> tags) {
        return tags.stream()
                .anyMatch(tag -> tag.split(DELIMITER)[0].equalsIgnoreCase(QASE_IGNORE));
    }

    public static Map<String, String> getCaseFields(List<String> tags) {
        return tags.stream()
                .map(tag -> tag.split(DELIMITER))
                .filter(split -> QASE_FIELDS.equalsIgnoreCase(split[0]) && split.length == 2)
                .map(split -> {
                    try {
                        return new Gson().fromJson(split[1].replaceAll("'", "\""), Map.class);
                    } catch (JsonSyntaxException e) {
                        return new HashMap<String, String>();
                    }
                })
                .findFirst()
                .orElse(new HashMap<>());
    }

    public static int getHash(URI uri, Long line) {
        return (uri.toString() + line).hashCode();
    }
}

