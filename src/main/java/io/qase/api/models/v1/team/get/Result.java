package io.qase.api.models.v1.team.get;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Result {
    private String email;
    private long id;
    private String name;
    private long status;
    private String title;
}
