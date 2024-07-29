package io.qase.api.config.apiclient;

import io.qase.client.ApiClient;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.qase.api.config.apiclient.DefaultHeadersApiConfigurer.*;
import static io.qase.api.constant.HeaderNames.X_CLIENT_HEADER_NAME;
import static io.qase.api.constant.HeaderNames.X_PLATFORM_HEADER_NAME;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;

class DefaultHeadersApiConfigurerTest {

    private static final String FRAMEWORK_NAME = "SomeTestingFrameworkForExampleJunit";

    private static final String FRAMEWORK_VERSION = FRAMEWORK_NAME + "FrameworkVersion";

    // X-Client: qaseapi=[API_VERSION];qase-[REPORTER_NAME]=[REPORTER_VERSION];[FRAMEWORK_NAME]=[FRAMEWORK_VERSION]
    private static final Pattern X_CLIENT_HEADER_VALUE_PATTERN = Pattern.compile( // group names for readability
        "(?<qaseApiName>.*)=(?<qaseApiVersion>.*);(?<reporterName>.*)=(?<reporterVersion>.*);"
            + Pattern.quote(FRAMEWORK_NAME) + "=" + Pattern.quote(FRAMEWORK_VERSION)
    );

    // X-Platform: os=[OS_NAME];arch=[ARCH];[LANG]=[LANG_VER]
    private static final Pattern X_PLATFORM_HEADER_VALUE_PATTERN = Pattern.compile( // group names for readability
        Pattern.quote(OS) + "=(?<osName>.*);" + Pattern.quote(ARCH) + "=(?<archName>.*);"
            + Pattern.quote(LANGUAGE) + "=" + "(?<languageVersion>.*)"
    );

    @Test
    void configure_xClientAndXPlatformSetFromConfigurer_bothHeadersAddedToApiClient() {
        DefaultHeadersApiConfigurer defaultHeadersApiConfigurer = createConfigurer(FRAMEWORK_NAME, FRAMEWORK_VERSION);

        verifyConfigurerAddsAllHeadersToApiClient(
            defaultHeadersApiConfigurer,
            new HashMap<String, Pattern>() {{
                put(X_CLIENT_HEADER_NAME, X_CLIENT_HEADER_VALUE_PATTERN);
                put(X_PLATFORM_HEADER_NAME, X_PLATFORM_HEADER_VALUE_PATTERN);
            }}
        );
    }

    void verifyConfigurerAddsAllHeadersToApiClient(
        DefaultHeadersApiConfigurer configurer, Map<String, Pattern> expectedPresentHeaders
    ) {
        ApiClient apiClient = Mockito.spy(ApiClient.class);

        configurer.configure(apiClient);

        ArgumentCaptor<String> headerNameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> headerValueCaptor = ArgumentCaptor.forClass(String.class);
        verify(apiClient, atLeast(expectedPresentHeaders.size()))
            .addDefaultHeader(headerNameCaptor.capture(), headerValueCaptor.capture());
        Map<String, String> passedDefaultHeaders =
            zipOverwriting(headerNameCaptor.getAllValues(), headerValueCaptor.getAllValues());
        assertThat(
            "Not all expected headers are present in headers added by the configurer",
            isExpectedSubsetPresent(expectedPresentHeaders, passedDefaultHeaders)
        );
    }

    private boolean isExpectedSubsetPresent(
        Map<String, Pattern> expectedHeadersPresent, Map<String, String> headersSet
    ) {
        return expectedHeadersPresent.entrySet().stream().allMatch(
            expectedToBePresent -> Optional.ofNullable(headersSet.get(expectedToBePresent.getKey()))
                .map(expectedHeaderValue -> expectedToBePresent.getValue().matcher(expectedHeaderValue).matches())
                .orElse(false)
        );
    }

    private Map<String, String> zipOverwriting(List<String> keys, List<String> values) {
        if (keys.size() != values.size()) {
            throw new IllegalArgumentException("Keys and values sizes must be equal");
        }

        return IntStream.range(0, keys.size())
            .boxed()
            .collect(Collectors.toMap(keys::get, values::get, overwriting()));
    }

    private BinaryOperator<String> overwriting() {
        return (previousValue, newValue) -> newValue;
    }

    private DefaultHeadersApiConfigurer createConfigurer(String frameworkName, String frameworkVersion) {
        return new DefaultHeadersApiConfigurer() {
            @Override
            protected String getFrameworkName() {
                return frameworkName;
            }

            @Override
            protected String getFrameworkVersion() {
                return frameworkVersion;
            }
        };
    }
}
