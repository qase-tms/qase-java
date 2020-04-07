package io.qase.api;

import io.qase.api.models.v1.team.User;
import io.qase.api.models.v1.team.Users;
import io.qase.api.services.TeamService;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

public final class TeamServiceImpl implements TeamService {
    private final QaseApiClient qaseApiClient;

    public TeamServiceImpl(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    @Override
    public Users getAll(int limit, int offset) {
        return qaseApiClient.get(Users.class, "/user", emptyMap(), null, limit, offset);
    }

    @Override
    public Users getAll() {
        return this.getAll(100, 0);
    }

    @Override
    public User get(long id) {
        return qaseApiClient.get(User.class, "/user/{id}", singletonMap("id", id), emptyMap());
    }
}
