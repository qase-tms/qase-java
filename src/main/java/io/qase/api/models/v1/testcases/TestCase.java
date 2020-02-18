package io.qase.api.models.v1.testcases;

import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class TestCase {
    private List<Attachment> attachments;
    private long automation;
    private long behavior;
    private String created;
    private List<CustomField> customFields;
    private String description;
    private long id;
    private List<Object> links;
    private Object milestoneId;
    private long position;
    private String postconditions;
    private String preconditions;
    private long priority;
    private long revision;
    private long severity;
    private long status;
    private List<Step> steps;
    private long suiteId;
    private List<Tag> tags;
    private String title;
    private long type;
    private String updated;
}
