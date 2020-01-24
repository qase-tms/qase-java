package io.qase.api.models.v1.testruns.get;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Stats {
    private long blocked;
    private long deleted;
    private long failed;
    @SerializedName("in_progress")
    private long inProgress;
    private long passed;
    private long retest;
    private long skipped;
    private long total;
    private long untested;
}
