package io.qase.commons.config;


import io.qase.commons.logger.Logger;

public class BatchConfig {
    private static final Logger logger = Logger.getInstance();

    public int size = 200;
    public int uploadTimeout = 300;

    public void setSize(int size) {
        if (size <= 0) {
            logger.warn("Batch size must be positive, got %d for parameter 'QASE_TESTOPS_BATCH_SIZE'. Using default: 200", size);
            return;
        }
        if (size > 2000) {
            logger.warn("Batch size must be <= 2000, got %d for parameter 'QASE_TESTOPS_BATCH_SIZE'. Using default: 200", size);
            return;
        }

        this.size = size;
    }

    public void setUploadTimeout(int uploadTimeout) {
        if (uploadTimeout <= 0) {
            logger.warn("Upload timeout must be positive, got %d for parameter 'QASE_TESTOPS_BATCH_UPLOAD_TIMEOUT'. Using default: 300", uploadTimeout);
            return;
        }

        this.uploadTimeout = uploadTimeout;
    }

    public int getUploadTimeout() {
        return uploadTimeout;
    }

    public int getSize() {
        return size;
    }
}
