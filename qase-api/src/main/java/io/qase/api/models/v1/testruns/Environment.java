package io.qase.api.models.v1.testruns;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Environment {
    private String description;
    private String host;
    private String slug;
    private String title;
}
