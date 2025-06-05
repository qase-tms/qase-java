package io.qase.commons.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {

    public static boolean isBlank(String string) {
        return string == null || string.trim().isEmpty();
    }

    public static String getDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        return now.format(formatter);
    }

    public static String generateSignature(ArrayList<Long> ids, ArrayList<String> suites,
            Map<String, String> parameters) {
        ArrayList<String> parts = new ArrayList<>();

        if (ids != null && ids.size() > 0) {
            parts.add(ids.stream().map(String::valueOf).collect(Collectors.joining("-")));
        }

        if (suites != null && suites.size() > 0) {
            parts.addAll(suites.stream().map(suite -> suite.trim().replace(" ", "_").toLowerCase())
                    .collect(Collectors.toList()));
        }

        if (parameters != null && !parameters.isEmpty()) {
            parts.addAll(parameters.entrySet().stream()
                    .map(entry -> String.format("{\"%s\":\"%s\"}", entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList()));
        }

        return parts.stream().collect(Collectors.joining("::"));
    }
}
