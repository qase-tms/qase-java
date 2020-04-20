package io.qase.api.models.v1.testcases;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Attachment {

    private String filename;
    private String mime;
    private long size;
    private String url;

}
