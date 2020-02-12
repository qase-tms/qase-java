package io.qase.api;

import io.qase.api.models.v1.attachments.Attachment;

import java.io.File;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

public final class Attachments {
    private final QaseApiClient qaseApiClient;

    public Attachments(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    public io.qase.api.models.v1.attachments.Attachments getAll(int limit, int offset) {
        return qaseApiClient.get(io.qase.api.models.v1.attachments.Attachments.class, "/attachment", emptyMap(), null, limit, offset);
    }

    public io.qase.api.models.v1.attachments.Attachments getAll() {
        return this.getAll(100, 0);
    }

    public Attachment get(String hash) {
        return qaseApiClient.get(Attachment.class, "/attachment/{hash}", singletonMap("hash", hash), emptyMap());
    }

    public Attachment add(String projectCode, File file) {
        return qaseApiClient.post(Attachment.class, "/attachment/{code}", file);
    }

    public boolean delete(String hash) {
        return (boolean) qaseApiClient.delete("/attachment/{hash}", singletonMap("hash", hash)).get("status");
    }
}
