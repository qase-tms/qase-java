package io.qase.api.models.v1.testplans.add;

import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class CreateUpdateTestPlanRequest {
    private final String title;
    private String description;
    private final List<Integer> cases;
}
