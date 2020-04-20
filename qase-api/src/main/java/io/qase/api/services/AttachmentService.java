package io.qase.api.services;

import io.qase.api.models.v1.attachments.Attachment;
import io.qase.api.models.v1.attachments.Attachments;

import java.io.File;
import java.util.List;

public interface AttachmentService {

    Attachments getAll(int limit, int offset);

    Attachments getAll();

    Attachment get(String hash);

    List<Attachment> add(String projectCode, File file);

    boolean delete(String hash);
}
