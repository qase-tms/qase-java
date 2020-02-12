package io.qase.api.models.v1.attachments;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class Attachments {
    private long count;
    @SerializedName("entities")
    private List<Attachment> attachmentList;
    private long filtered;
    private long total;
}
