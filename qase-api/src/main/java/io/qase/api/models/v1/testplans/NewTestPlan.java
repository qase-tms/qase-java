package io.qase.api.models.v1.testplans;

import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class NewTestPlan {
    private final String title;
    private String description;
    private final List<Integer> cases;
}
