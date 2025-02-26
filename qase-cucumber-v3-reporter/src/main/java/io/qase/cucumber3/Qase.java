package io.qase.cucumber3;

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

    public static void attach(String fileName, byte[] content, String contentType) {
        Methods.addAttachment(fileName, content, contentType);
    }
}
