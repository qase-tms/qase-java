package io.qase.commons.models.domain;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public String action;
    public String expectedResult;
    public String inputData;
    public List<Attachment> attachments;

    public Data() {
        this.attachments = new ArrayList<>();
    }
}
