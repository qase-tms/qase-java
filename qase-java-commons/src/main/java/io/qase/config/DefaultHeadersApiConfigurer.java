package io.qase.config;

import io.qase.api.QaseClient;
import io.qase.api.constant.Constants;
import io.qase.client.v1.ApiClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.qase.api.constant.HeaderNames.X_CLIENT_HEADER_NAME;
import static io.qase.api.constant.HeaderNames.X_PLATFORM_HEADER_NAME;
import static io.qase.api.constant.SystemPropertyNames.*;
import static io.qase.api.utils.StringUtils.isBlank;
import static io.qase.utils.CommonUtils.getFirstNonNullResult;

@Slf4j
public abstract class DefaultHeadersApiConfigurer implements ApiClientConfigurer {

    public static final String QASE_API = "qaseapi";

    public static final String OS = "os";

    public static final String ARCH = "arch";

    public static final String LANGUAGE = "java";

    protected static final String UNKNOWN = "unknown";

    public static ApiClientConfigurer createDefaultConfigurer() {
        return new DefaultHeadersApiConfigurer() {
            @Override
            protected String getFrameworkName() {
                return UNKNOWN;
            }

            @Override
            protected String getFrameworkVersion() {
                return UNKNOWN;
            }
        };
    }

    @Override
    public void configure(ApiClient apiClient) {
        apiClient.addDefaultHeader(Constants.X_CLIENT_REPORTER, QaseClient.getConfig().clientReporterName());
        String xClientHeaderValue = buildXClientHeaderValue();
        addDefaultHeaderIfNotBlank(apiClient, X_CLIENT_HEADER_NAME, xClientHeaderValue);
        addDefaultHeaderIfNotBlank(apiClient, X_PLATFORM_HEADER_NAME, buildXPlatformHeaderValue());
    }

    protected abstract String getFrameworkName();

    protected abstract String getFrameworkVersion();

    protected String getFrameworkVersionByClassOrUnknown(Class<?> frameworkClass) {
        return getFrameworkVersionByPackageOrUnknown(frameworkClass.getPackage());
    }

    private String getFrameworkVersionByPackageOrUnknown(Package frameworkPackage) {
        return findFirstNonNullPackageVersionOrUnknown(frameworkPackage);
    }

    private String getReporterVersion() {
        return findFirstNonNullPackageVersionOrUnknown(getClass().getPackage());
    }

    private String getReporterName() {
        return findFirstNonNullPackageTitleOrUnknown(getClass().getPackage());
    }

    private String findFirstNonNullPackageInfoOrUnknown(
        Package aPackage, List<Function<Package, String>> packageInfoGetters
    ) {
        return getFirstNonNullResult(aPackage, packageInfoGetters).orElse(UNKNOWN);
    }

    private String findFirstNonNullPackageVersionOrUnknown(Package aPackage) {
        return findFirstNonNullPackageInfoOrUnknown(
            aPackage,
            Arrays.asList(Package::getImplementationVersion, Package::getSpecificationVersion)
        );
    }

    private String findFirstNonNullPackageTitleOrUnknown(Package aPackage) {
        return findFirstNonNullPackageInfoOrUnknown(
            aPackage,
            Arrays.asList(Package::getImplementationTitle, Package::getImplementationTitle)
        );
    }

    private String buildXPlatformHeaderValue() {
        return new HeaderFormatter(
            new HeaderPartFormatter(OS, System.getProperty(OS_NAME)),
            new HeaderPartFormatter(ARCH, System.getProperty(OS_ARCH)),
            new HeaderPartFormatter(LANGUAGE, System.getProperty(JAVA_VERSION))
        ).format();
    }

    private String buildXClientHeaderValue() {
        return new HeaderFormatter(
            new HeaderPartFormatter(
                QASE_API,
                findFirstNonNullPackageVersionOrUnknown(DefaultHeadersApiConfigurer.class.getPackage())
            ),
            new HeaderPartFormatter(getReporterName(), getReporterVersion()),
            new HeaderPartFormatter(getFrameworkName(),getFrameworkVersion())
        ).format();
    }

    private void addDefaultHeaderIfNotBlank(ApiClient apiClient, String headerName, String headerValue) {
        if (!isBlank(headerName)) {
            apiClient.addDefaultHeader(headerName, headerValue);
        } else {
            log.warn("'{}' header value happens to be blank. Ignoring the header.", headerName);
        }
    }

    private static class HeaderFormatter {

        private static final String DELIMITER = ";";

        private final List<HeaderPartFormatter> headerPartFormatters;

        public HeaderFormatter(HeaderPartFormatter... headerPartFormatters) {
            this.headerPartFormatters = Arrays.asList(headerPartFormatters);
        }

        public String format() {
            return headerPartFormatters.stream()
                .map(HeaderPartFormatter::format)
                .collect(Collectors.joining(DELIMITER));
        }
    }

    @RequiredArgsConstructor
    private static class HeaderPartFormatter {

        private static final String HEADER_PART_FORMAT = "%s=%s";

        private final String partName;

        private final String partValue;

        private static String orUnknown(String string) {
            return Optional.ofNullable(string).orElse(UNKNOWN);
        }

        public String format() {
            return String.format(HEADER_PART_FORMAT, orUnknown(partName), orUnknown(partValue));
        }
    }
}
