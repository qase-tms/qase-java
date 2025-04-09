package io.qase.commons.config;


import io.qase.commons.logger.Logger;

public class BatchConfig {
    private static final Logger logger = Logger.getInstance();

    public int size = 200;

    public void setSize(int size) {
        if (size > 2000) {
            logger.warn("Batch size must be less than 2000. Using default value 200");
            return;
        }

        this.size = size;
    }

    public int getSize() {
        return size;
    }
}
