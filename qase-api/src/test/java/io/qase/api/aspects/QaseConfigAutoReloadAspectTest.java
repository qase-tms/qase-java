package io.qase.api.aspects;

import io.qase.api.config.QaseConfig;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QaseConfigAutoReloadAspectTest {

    @Test
    void systemPropertiesUpdated_usingSetProperty_configAutoreloaded() {
        System.setProperty(QaseConfig.ENABLE_KEY, Boolean.FALSE.toString());
        QaseConfig qaseConfig = ConfigFactory.create(QaseConfig.class);

        System.setProperty(QaseConfig.ENABLE_KEY, Boolean.TRUE.toString());

        assertTrue(qaseConfig.isEnabled());
    }

    @Test
    void systemPropertiesUpdated_usingSetProperties_configAutoreloaded() {
        final String newToken = "new-token";
        final String oldToken = "old-token";
        System.setProperty(QaseConfig.API_TOKEN_KEY, oldToken);
        QaseConfig qaseConfig = ConfigFactory.create(QaseConfig.class);
        Properties newProperties = new Properties() {{ setProperty(QaseConfig.API_TOKEN_KEY, newToken); }};

        System.setProperties(newProperties);

        assertEquals(newToken, qaseConfig.apiToken());
    }
}
