package io.qase.api.models.v1.testruns.get_all;

import com.google.gson.annotations.SerializedName;
import io.qase.api.models.v1.testruns.get.Environment;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class Entity {
    private Object description;
    @SerializedName("end_time")
    private Object endTime;
    private Environment environment;
    private long id;
    @SerializedName("public")
    private Boolean isPublic;
    @SerializedName("start_time")
    private String startTime;
    private Stats stats;
    private long status;
    @SerializedName("time_spent")
    private long timeSpent;
    private String title;
    @SerializedName("user_id")
    private long userId;
    private List<Integer> cases;
}
