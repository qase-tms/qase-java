package io.qase.api.models.v1.team.get_all;

import lombok.Data;

@Data
@SuppressWarnings("unused")
public class Entity {
    private String email;
    private long id;
    private String name;
    private long status;
    private String title;
}
