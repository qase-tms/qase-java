package io.qase.commons.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public final class CucumberUtils {
    private static final List<String> CASE_TAGS = Collections.unmodifiableList(Arrays.asList("@caseid", "@tmslink", "@qaseid", "@qaseids"));
    private static final String QASE_TITLE = "@QaseTitle";
    private static final String QASE_IGNORE = "@QaseIgnore";
    private static final String QASE_FIELDS = "@QaseFields";
    private static final String QASE_SUITE = "@QaseSuite";
    private static final String DELIMITER = "=";

    private CucumberUtils() throws IllegalAccessException {
        throw new IllegalAccessException("Utility class");
    }

    public static List<Long> getCaseIds(List<String> tags) {
        List<Long> ids = tags.stream()
                .map(tag -> tag.split(DELIMITER))
                .filter(split -> CASE_TAGS.contains(split[0].toLowerCase()) && split.length == 2)
                .flatMap(split -> {
                    if (split[0].equalsIgnoreCase("@qaseids")) {
                        return Arrays.stream(split[1].split(","))
                                .map(String::trim)
                                .filter(id -> id.matches("\\d+") || id.matches("\\D+-\\d+"))
                                .map(id -> Long.valueOf(id.replaceAll("\\D+", "")));
                    } else {
                        if (split[1].matches("\\d+") || split[1].matches("\\D+-\\d+")) {
                            return Stream.of(Long.valueOf(split[1].replaceAll("\\D+", "")));
                        }
                        return Stream.empty();
                    }
                })
                .collect(Collectors.toList());

        if (ids.isEmpty()) {
            return null;
        }

        return ids;
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

    public static  String formatTable(List<List<String>> tableData) {
        if (tableData == null || tableData.isEmpty()) {
            return "";
        }

        StringBuilder result = new StringBuilder();

        int[] maxWidths = new int[tableData.get(0).size()];

        for (List<String> row : tableData) {
            for (int i = 0; i < row.size(); i++) {
                int cellLength = row.get(i).length();
                if (cellLength > maxWidths[i]) {
                    maxWidths[i] = cellLength;
                }
            }
        }

        for (List<String> row : tableData) {
            result.append("| ");
            for (int i = 0; i < row.size(); i++) {
                String cellValue = row.get(i);
                result.append(cellValue);

                int padding = maxWidths[i] - cellValue.length() + 1;
                for (int j = 0; j < padding; j++) {
                    result.append(" ");
                }
                result.append("| ");
            }
            result.append("\n");
        }

        return result.toString().trim();
    }
}

