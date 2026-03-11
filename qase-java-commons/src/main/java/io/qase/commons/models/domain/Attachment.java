package io.qase.commons.models.domain;

import java.util.UUID;

public class Attachment {
    public String id = UUID.randomUUID().toString();
    public String fileName;
    public String mimeType;
    public String content;
    public byte[] contentBytes;
    public String filePath;
    public long sizeBytes;
    public boolean isStagedTempFile;

    /**
     * Returns a compact log-safe string with metadata only.
     * Does NOT include contentBytes or content to avoid binary/large data in logs.
     */
    public String toLogString() {
        return String.format("Attachment{fileName='%s', mimeType='%s', size=%d bytes}",
            fileName, mimeType, sizeBytes);
    }
}
