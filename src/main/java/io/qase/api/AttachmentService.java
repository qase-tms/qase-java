package io.qase.api;

import io.qase.api.models.v1.attachments.Attachment;
import io.qase.api.models.v1.attachments.Attachments;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

public final class AttachmentService {
    private final QaseApiClient qaseApiClient;

    public AttachmentService(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    public Attachments getAll(int limit, int offset) {
        return qaseApiClient.get(Attachments.class, "/attachment", emptyMap(), null, limit, offset);
    }

    public Attachments getAll() {
        return this.getAll(100, 0);
    }

    public Attachment get(String hash) {
        return qaseApiClient.get(Attachment.class, "/attachment/{hash}", singletonMap("hash", hash), emptyMap());
    }

    public List<Attachment> add(String projectCode, File file) {
        return Arrays.asList(qaseApiClient.post(Attachment[].class, "/attachment/{code}", singletonMap("code", projectCode), file));
    }

    public boolean delete(String hash) {
        return (boolean) qaseApiClient.delete("/attachment/{hash}", singletonMap("hash", hash)).get("status");
    }
}
