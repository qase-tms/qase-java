package io.qase.commons.models.domain;

import com.google.gson.annotations.SerializedName;

public class SuiteData {
    public String title;
    @SerializedName("public_id")
    public Integer publicId;

    public SuiteData() {
        this.publicId = null;
    }
}
