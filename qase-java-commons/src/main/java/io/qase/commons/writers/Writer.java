package io.qase.commons.writers;

import io.qase.commons.QaseException;
import io.qase.commons.models.domain.Attachment;
import io.qase.commons.models.report.ReportResult;
import io.qase.commons.models.report.Run;

public interface Writer {
    void prepare() throws QaseException;

    void writeRun(Run run) throws QaseException;

    void writeResult(ReportResult result) throws QaseException;

    String writeAttachment(Attachment attachment);
}
