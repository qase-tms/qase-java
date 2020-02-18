package io.qase.api.models.v1.testcases;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class TestCases {
    private long count;
    @SerializedName("entities")
    private List<TestCase> testCaseList;
    private long filtered;
    private long total;
}
