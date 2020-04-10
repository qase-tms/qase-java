package io.qase.api.models.v1.testrunresults;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Step {
    private final long position;
    private final long status;
}
