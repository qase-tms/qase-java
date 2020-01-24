package io.qase.api.models.v1.attachments.get;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Result {
    private String extension;
    private String file;
    @SerializedName("full_path")
    private String fullPath;
    private String hash;
    private String mime;
    private long size;
}
