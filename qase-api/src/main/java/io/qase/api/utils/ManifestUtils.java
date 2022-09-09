package io.qase.api.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ManifestUtils {

    private static final String MANIFEST_RESOURCE_NAME = "META-INF/MANIFEST.MF";

    public static Manifest getClassLoaderManifest(ClassLoader manifestLoader) throws IOException {
        return new Manifest(openManifestStream(manifestLoader));
    }

    public static String tryGetManifestMainAttributeOrNull(
        ClassLoader manifestLoader, Attributes.Name attributeName
    ) {
        try {
            Attributes mainAttributes = getClassLoaderManifest(manifestLoader).getMainAttributes();
            String attributeValue = mainAttributes.getValue(attributeName);
            if (attributeValue == null) {
                log.warn(
                    "Attribute '{}' was not found in the manifest, classloader: {}", attributeName, manifestLoader
                );
            }
            return attributeValue;
        } catch (IOException e) {
            log.error(
                String.format(
                    "An error occurred while trying to read th manifest, classloader: %s. Returning null.",
                    manifestLoader
                ),
                e
            );
            return null;
        }
    }

    public static Optional<String> tryGetManifestMainAttribute(
        ClassLoader manifestLoader, Attributes.Name attributeName
    ) {
        return Optional.ofNullable(tryGetManifestMainAttributeOrNull(manifestLoader, attributeName));
    }

    private static InputStream openManifestStream(ClassLoader classLoader) throws FileNotFoundException {
        InputStream manifestInputStream = classLoader.getResourceAsStream(MANIFEST_RESOURCE_NAME);
        if (manifestInputStream == null) {
            throw new FileNotFoundException(String.format("The manifest '%s' was not found", MANIFEST_RESOURCE_NAME));
        }

        return manifestInputStream;
    }
}
