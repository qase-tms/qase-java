package io.qase.api;

import io.qase.api.models.v1.attachments.add.UploadFileResponse;
import io.qase.api.models.v1.attachments.get.AttachmentResponse;
import io.qase.api.models.v1.attachments.get_all.AttachmentsResponse;

import java.io.File;

import static java.util.Collections.emptyMap;
import static java.util.Collections.singletonMap;

public final class Attachments {
    private final QaseApiClient qaseApiClient;

    public Attachments(QaseApiClient qaseApiClient) {
        this.qaseApiClient = qaseApiClient;
    }

    public AttachmentsResponse getAll(int limit, int offset) {
        return qaseApiClient.get(AttachmentsResponse.class, "/attachment", emptyMap(), null, limit, offset);
    }

    public AttachmentsResponse getAll() {
        return this.getAll(100, 0);
    }

    public AttachmentResponse get(String hash) {
        return qaseApiClient.get(AttachmentResponse.class, "/attachment/{hash}", singletonMap("hash", hash), emptyMap());
    }

    public UploadFileResponse add(String projectCode, File file) {
        return qaseApiClient.post(UploadFileResponse.class, "/attachment/{code}", file);
    }

    public boolean delete(String hash) {
        return (boolean) qaseApiClient.delete("/attachment/{hash}", singletonMap("hash", hash)).get("status");
    }
}
