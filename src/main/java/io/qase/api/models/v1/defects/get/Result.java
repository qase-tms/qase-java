package io.qase.api.models.v1.defects.get;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class Result {
    @SerializedName("actual_result")
    private String actualResult;
    private List<Object> attachments;
    private String created;
    @SerializedName("custom_fields")
    private List<Object> customFields;
    private long id;
    private String status;
    private String title;
    private String updated;
    @SerializedName("user_id")
    private long userId;
}
