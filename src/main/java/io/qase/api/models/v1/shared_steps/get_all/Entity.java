package io.qase.api.models.v1.shared_steps.get_all;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class Entity {
    private String hash;
    private String title;
    private String action;
    @SerializedName("expected_result")
    private String expectedResult;
    private List<Long> cases;
    @SerializedName("cases_count")
    private long casesCount;
    private String created;
    private String updated;
}
