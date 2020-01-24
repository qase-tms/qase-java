package io.qase.api.models.v1.testruns.get_all;

import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class Result {
    private long count;
    private List<Entity> entities;
    private long filtered;
    private long total;
}
