package io.qase.api.models.v1.testrunresults;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class TestRunResults {
    private long count;
    @SerializedName("entities")
    private List<TestRunResult> testRunResultList;
    private long filtered;
    private long total;
}
