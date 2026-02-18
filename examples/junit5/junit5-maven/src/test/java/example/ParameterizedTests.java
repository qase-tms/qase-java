package example;

import io.qase.commons.annotation.QaseId;
import io.qase.commons.annotation.QaseTitle;
import io.qase.commons.annotation.QaseFields;
import io.qase.commons.models.annotation.Field;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

public class ParameterizedTests {

    // --- @MethodSource ---

    static Stream<Arguments> loginCredentials() {
        return Stream.of(
                Arguments.of("admin", "secret"),
                Arguments.of("user", "password123"),
                Arguments.of("guest", "guest")
        );
    }

    @ParameterizedTest
    @MethodSource("loginCredentials")
    @QaseTitle("Login with credentials from method source")
    public void testWithMethodSource(String username, String password) {
        System.out.println("Login with " + username + " / " + password);
    }

    // --- @ValueSource ---

    @ParameterizedTest
    @ValueSource(strings = {"Chrome", "Firefox", "Safari", "Edge"})
    @QaseId(20)
    @QaseTitle("Browser compatibility test")
    public void testWithValueSource(String browser) {
        System.out.println("Testing with browser: " + browser);
    }

    // --- @CsvSource ---

    @ParameterizedTest
    @CsvSource({
            "GET, /api/users, 200",
            "POST, /api/users, 201",
            "DELETE, /api/users/1, 204",
            "GET, /api/unknown, 404"
    })
    @QaseTitle("API endpoint response codes")
    @QaseFields(value = {
            @Field(name = "layer", value = "api"),
    })
    public void testWithCsvSource(String method, String endpoint, int expectedStatus) {
        System.out.println(method + " " + endpoint + " -> " + expectedStatus);
    }
}
