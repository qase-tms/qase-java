package io.qase.api.models.v1.team;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class Users {
    private long count;
    @SerializedName("entities")
    private List<User> userList;
    private long filtered;
    private long total;
}
