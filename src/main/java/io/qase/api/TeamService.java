package io.qase.api;

import io.qase.api.models.v1.team.User;
import io.qase.api.models.v1.team.Users;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

public final class TeamService {
    private final QaseApiClient qaseApiClient;

    public TeamService(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    public Users getAll(int limit, int offset) {
        return qaseApiClient.get(Users.class, "/user", emptyMap(), null, limit, offset);
    }

    public Users getAll() {
        return this.getAll(100, 0);
    }

    public User get(long id) {
        return qaseApiClient.get(User.class, "/user/{id}", singletonMap("id", id), emptyMap());
    }
}
