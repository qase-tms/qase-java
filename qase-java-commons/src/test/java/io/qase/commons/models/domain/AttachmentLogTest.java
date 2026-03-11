package io.qase.commons.models.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AttachmentLogTest {

    @Test
    void toLogStringContainsMetadataNotBinaryContent() {
        Attachment a = new Attachment();
        a.fileName = "screenshot.png";
        a.mimeType = "image/png";
        a.contentBytes = new byte[1024]; // 1KB of zeros
        a.sizeBytes = 1024;

        String log = a.toLogString();

        assertTrue(log.contains("screenshot.png"), "Should contain fileName");
        assertTrue(log.contains("image/png"), "Should contain mimeType");
        assertTrue(log.contains("1024"), "Should contain size in bytes");
        assertFalse(log.contains("contentBytes"), "Should not contain field name contentBytes");
        assertTrue(log.length() < 200, "Log string should be compact, was: " + log.length());
    }

    @Test
    void toLogStringWithNullContentBytesShowsZeroSize() {
        Attachment a = new Attachment();
        a.fileName = "notes.txt";
        a.mimeType = "text/plain";
        a.contentBytes = null;
        a.content = null;
        a.sizeBytes = 0;

        String log = a.toLogString();

        assertTrue(log.contains("notes.txt"));
        assertTrue(log.contains("0 bytes") || log.contains("size=0"), "Should show zero size");
    }

    @Test
    void toLogStringWithLargeAttachmentIsCompact() {
        Attachment a = new Attachment();
        a.fileName = "large-video.mp4";
        a.mimeType = "video/mp4";
        a.sizeBytes = 10 * 1024 * 1024; // 10 MB

        String log = a.toLogString();

        assertTrue(log.length() < 200, "10MB attachment log string must be under 200 chars, was: " + log.length());
        assertTrue(log.contains("large-video.mp4"));
        assertTrue(log.contains(String.valueOf(10 * 1024 * 1024)), "Should contain size 10485760");
    }

    @Test
    void toLogStringWithStringContentShowsLengthNotContent() {
        Attachment a = new Attachment();
        a.fileName = "log.txt";
        a.mimeType = "text/plain";
        a.content = "x".repeat(50000); // 50K chars of text content
        a.contentBytes = null;
        a.sizeBytes = 50000;

        String log = a.toLogString();

        assertTrue(log.length() < 200, "String content attachment should produce compact log");
        assertTrue(log.contains("log.txt"));
        assertFalse(log.contains("xxxxx"), "Should not contain raw content string");
    }
}
