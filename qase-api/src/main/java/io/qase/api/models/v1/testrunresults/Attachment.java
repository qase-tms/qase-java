package io.qase.api.models.v1.testrunresults;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Attachment {
    private String filename;
    private String url;
    private String mime;
    private long size;
}
