package io.qase.commons.models.domain;

import java.util.UUID;

public class Attachment {
    public String id = UUID.randomUUID().toString();
    public String fileName;
    public String mimeType;
    public String content;
    public byte[] contentBytes;
    public String filePath;
}
