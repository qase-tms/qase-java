package io.qase.api.models.v1.projects.get_all;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Defects {
    private long open;
    private long total;
}
