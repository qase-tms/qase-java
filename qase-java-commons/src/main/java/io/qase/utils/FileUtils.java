package io.qase.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class FileUtils {

    private static final String FILE_EXTENSION_PATTERN_GROUP_NAME = "extension";

    private static final Pattern FILE_EXTENSION_PATTERN =
        Pattern.compile("^.*\\.(?<" + FILE_EXTENSION_PATTERN_GROUP_NAME + ">\\S+)$");

    private static final String EMPTY_STRING = "";

    public static String getFileExtension(File file) {
        Matcher matcher = FILE_EXTENSION_PATTERN.matcher(file.getName());
        return matcher.matches()
            ? matcher.group(FILE_EXTENSION_PATTERN_GROUP_NAME)
            : EMPTY_STRING;
    }
}
