package io.qase.api.models.v1.testplans.get_all;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Entity {
    @SerializedName("cases_count")
    private long casesCount;
    private String created;
    private String description;
    private long id;
    private String title;
    private String updated;
}
