package io.qase.api.models.v1.testruns;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class TestRuns {
    private long count;
    @SerializedName("entities")
    private List<TestRun> TestRunList;
    private long filtered;
    private long total;
}
