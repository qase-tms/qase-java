package io.qase.api.models.v1.projects.get;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Defects {
    private long open;
    private Object total;
}
