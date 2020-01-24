package io.qase.api;

import io.qase.api.models.v1.team.get.UserResponse;
import io.qase.api.models.v1.team.get_all.UsersResponse;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

public final class Team {
    private final QaseApiClient qaseApiClient;

    public Team(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    public UsersResponse getAll(int limit, int offset) {
        return qaseApiClient.get(UsersResponse.class, "/user", emptyMap(), null, limit, offset);
    }

    public UsersResponse getAll() {
        return this.getAll(100, 0);
    }

    public UserResponse get(long id) {
        return qaseApiClient.get(UserResponse.class, "/user/{id}", singletonMap("id", id), emptyMap());
    }
}
