package io.qase.api.models.v1.testrunresults;

import io.qase.api.enums.StepStatus;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
@SuppressWarnings("unused")
public class Step {
    private final long position;
    private final StepStatus status;
    private String comment;
    private List<Attachment> attachments;
}
