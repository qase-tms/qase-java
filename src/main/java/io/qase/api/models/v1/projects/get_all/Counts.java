package io.qase.api.models.v1.projects.get_all;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Counts {
    private long cases;
    private Defects defects;
    private long milestones;
    private Runs runs;
    private long suites;
}
