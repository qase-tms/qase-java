package io.qase.api.models.v1.customfields.get;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Result {
    private String created;
    @SerializedName("default_value")
    private String defaultValue;
    private String entity;
    private long id;
    @SerializedName("is_filterable")
    private Boolean isFilterable;
    @SerializedName("is_required")
    private Boolean isRequired;
    @SerializedName("is_visible")
    private Boolean isVisible;
    private String placeholder;
    private String title;
    private String type;
    private String updated;
    private String value;
}
