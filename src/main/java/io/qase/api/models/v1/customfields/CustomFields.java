package io.qase.api.models.v1.customfields;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class CustomFields {
    private long count;
    @SerializedName("entities")
    private List<CustomField> customFieldList;
    private long filtered;
    private long total;
}
