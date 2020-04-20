package io.qase.api.models.v1.testcases;

import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class Step {
    private String action;
    private List<Attachment> attachments;
    private String expectedResult;
    private String hash;
    private long position;
    private String sharedStepHash;
}
