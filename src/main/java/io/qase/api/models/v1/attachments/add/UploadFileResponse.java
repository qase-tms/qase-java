package io.qase.api.models.v1.attachments.add;

import lombok.Data;

import java.util.List;

@Data
@SuppressWarnings("unused")
public class UploadFileResponse {
    private List<Result> result;
    private Boolean status;
}
