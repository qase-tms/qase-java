import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class ParametrisedTests {

    companion object {
        @JvmStatic
        fun arguments(): Stream<Arguments> = Stream.of(
            Arguments.of("string1"),
            Arguments.of("string2"),
            Arguments.of("string3")
        )
    }

    @ParameterizedTest
    @MethodSource("arguments")
    fun testMethod_success1(data: String) {
        println("Data is: $data")
    }

    @ParameterizedTest
    @MethodSource("arguments")
    fun testMethod_failed1(data: String) {
        println("Data is: $data")
        throw RuntimeException("Test failed")
    }
}
