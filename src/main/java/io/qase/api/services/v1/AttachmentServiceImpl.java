package io.qase.api.services.v1;

import io.qase.api.QaseApiClient;
import io.qase.api.models.v1.attachments.Attachment;
import io.qase.api.models.v1.attachments.Attachments;
import io.qase.api.services.AttachmentService;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

public final class AttachmentServiceImpl implements AttachmentService {
    private final QaseApiClient qaseApiClient;

    public AttachmentServiceImpl(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    @Override
    public Attachments getAll(int limit, int offset) {
        return qaseApiClient.get(Attachments.class, "/attachment", emptyMap(), null, limit, offset);
    }

    @Override
    public Attachments getAll() {
        return this.getAll(100, 0);
    }

    @Override
    public Attachment get(String hash) {
        return qaseApiClient.get(Attachment.class, "/attachment/{hash}", singletonMap("hash", hash), emptyMap());
    }

    @Override
    public List<Attachment> add(String projectCode, File file) {
        return Arrays.asList(qaseApiClient.post(Attachment[].class, "/attachment/{code}", singletonMap("code", projectCode), file));
    }

    @Override
    public boolean delete(String hash) {
        return (boolean) qaseApiClient.delete("/attachment/{hash}", singletonMap("hash", hash)).get("status");
    }
}
