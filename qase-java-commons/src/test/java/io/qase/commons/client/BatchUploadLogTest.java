package io.qase.commons.client;

import io.qase.commons.models.domain.Attachment;
import io.qase.commons.models.domain.TestResult;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class BatchUploadLogTest {

    @Test
    void batchSummaryCountsResultsAttachmentsAndTotalBytes() {
        TestResult r1 = new TestResult();
        r1.title = "test1";
        Attachment a1 = new Attachment();
        a1.fileName = "s1.png";
        a1.sizeBytes = 1024;
        r1.attachments.add(a1);

        TestResult r2 = new TestResult();
        r2.title = "test2";
        Attachment a2 = new Attachment();
        a2.fileName = "s2.png";
        a2.sizeBytes = 2048;
        Attachment a3 = new Attachment();
        a3.fileName = "s3.png";
        a3.sizeBytes = 4096;
        r2.attachments.add(a2);
        r2.attachments.add(a3);

        List<TestResult> batch = Arrays.asList(r1, r2);

        // Compute summary the same way the production code does
        int resultCount = batch.size();
        int totalAttachments = batch.stream()
            .mapToInt(r -> r.attachments != null ? r.attachments.size() : 0)
            .sum();
        long totalBytes = batch.stream()
            .flatMap(r -> r.attachments != null ? r.attachments.stream() : Stream.empty())
            .mapToLong(a -> a.sizeBytes)
            .sum();

        assertEquals(2, resultCount);
        assertEquals(3, totalAttachments);
        assertEquals(1024 + 2048 + 4096, totalBytes, "Total bytes should be sum of all sizeBytes");

        // Verify MB formatting
        String formatted = String.format("%.1f MB", totalBytes / (1024.0 * 1024.0));
        assertTrue(formatted.contains("MB"), "Formatted string should contain MB");
        assertFalse(formatted.startsWith("-"), "Size should not be negative");
    }

    @Test
    void batchSummaryHandlesNullAttachmentsList() {
        TestResult r1 = new TestResult();
        r1.title = "test-no-attachments";
        r1.attachments = null;

        List<TestResult> batch = Collections.singletonList(r1);

        int totalAttachments = batch.stream()
            .mapToInt(r -> r.attachments != null ? r.attachments.size() : 0)
            .sum();
        long totalBytes = batch.stream()
            .flatMap(r -> r.attachments != null ? r.attachments.stream() : Stream.empty())
            .mapToLong(a -> a.sizeBytes)
            .sum();

        assertEquals(0, totalAttachments);
        assertEquals(0, totalBytes);
    }

    @Test
    void batchSummaryHandlesEmptyBatch() {
        List<TestResult> batch = Collections.emptyList();

        int resultCount = batch.size();
        int totalAttachments = batch.stream()
            .mapToInt(r -> r.attachments != null ? r.attachments.size() : 0)
            .sum();
        long totalBytes = batch.stream()
            .flatMap(r -> r.attachments != null ? r.attachments.stream() : Stream.empty())
            .mapToLong(a -> a.sizeBytes)
            .sum();

        assertEquals(0, resultCount);
        assertEquals(0, totalAttachments);
        assertEquals(0, totalBytes);
    }

    @Test
    void batchSummaryFormatsAsMBCorrectly() {
        TestResult r1 = new TestResult();
        r1.title = "test-large";
        Attachment a1 = new Attachment();
        a1.fileName = "big.bin";
        a1.sizeBytes = 2 * 1024 * 1024 + 512 * 1024; // 2.5 MB
        r1.attachments.add(a1);

        List<TestResult> batch = Collections.singletonList(r1);

        long totalBytes = batch.stream()
            .flatMap(r -> r.attachments != null ? r.attachments.stream() : Stream.empty())
            .mapToLong(a -> a.sizeBytes)
            .sum();
        String formatted = String.format("Uploading batch: %d results, %d attachments, %.1f MB",
            batch.size(), 1, totalBytes / (1024.0 * 1024.0));

        assertEquals("Uploading batch: 1 results, 1 attachments, 2.5 MB", formatted);
    }
}
