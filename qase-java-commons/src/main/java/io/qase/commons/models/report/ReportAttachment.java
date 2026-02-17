package io.qase.commons.models.report;

import com.google.gson.annotations.SerializedName;

/**
 * Attachment model for file report according to attachment.yaml specification
 */
public class ReportAttachment {
    /**
     * Unique identifier of the attachment
     */
    public String id;

    /**
     * Base64 encoded content of the attachment (nullable)
     */
    public String content;

    /**
     * Name of the file (nullable)
     */
    @SerializedName("file_name")
    public String fileName;

    /**
     * Path to the file (nullable)
     */
    @SerializedName("file_path")
    public String filePath;

    /**
     * MIME type of the attachment (nullable)
     */
    @SerializedName("mime_type")
    public String mimeType;

}

