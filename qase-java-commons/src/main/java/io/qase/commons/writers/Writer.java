package io.qase.commons.writers;

import io.qase.commons.QaseException;
import io.qase.commons.models.domain.Attachment;
import io.qase.commons.models.report.ReportAttachment;
import io.qase.commons.models.report.ReportResult;
import io.qase.commons.models.report.Run;

public interface Writer {
    void prepare() throws QaseException;

    void writeRun(Run run) throws QaseException;

    void writeResult(ReportResult result) throws QaseException;

    /**
     * Writes attachment to storage and returns ReportAttachment with metadata
     * @param attachment Domain attachment to write
     * @return ReportAttachment with file path and metadata, or null if failed
     */
    ReportAttachment writeAttachment(Attachment attachment);
}
