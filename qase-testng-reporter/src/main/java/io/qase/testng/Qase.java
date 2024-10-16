package io.qase.testng;

import io.qase.commons.Methods;

public class Qase {
    public static void comment(String message) {
        Methods.addComment(message);
    }

    public static void attach(String... files) {
        Methods.addAttachments(files);
    }

    public static void attach(String fileName, String content, String contentType) {
        Methods.addAttachment(fileName, content, contentType);
    }
}
