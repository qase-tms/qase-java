package io.qase.api.models.v1.team;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class User {
    private String email;
    private long id;
    private String name;
    private long status;
    private String title;
}
