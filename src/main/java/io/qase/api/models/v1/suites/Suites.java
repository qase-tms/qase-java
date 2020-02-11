package io.qase.api.models.v1.suites;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class Suites {
    private long count;
    @SerializedName("entities")
    private List<Suite> suiteList;
    private long filtered;
    private long total;

}
