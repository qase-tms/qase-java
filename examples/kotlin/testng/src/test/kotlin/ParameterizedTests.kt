import io.qase.commons.annotation.QaseId
import io.qase.commons.annotation.QaseTitle
import io.qase.commons.annotation.QaseFields
import io.qase.commons.models.annotation.Field
import org.testng.annotations.DataProvider
import org.testng.annotations.Test

class ParameterizedTests {

    @DataProvider(name = "login-credentials")
    fun loginCredentials(): Array<Array<Any>> = arrayOf(
        arrayOf("admin", "secret"),
        arrayOf("user", "password123"),
        arrayOf("guest", "guest")
    )

    @DataProvider(name = "browsers")
    fun browsers(): Array<Array<Any>> = arrayOf(
        arrayOf("Chrome"),
        arrayOf("Firefox"),
        arrayOf("Safari"),
        arrayOf("Edge")
    )

    @DataProvider(name = "api-endpoints")
    fun apiEndpoints(): Array<Array<Any>> = arrayOf(
        arrayOf("GET", "/api/users", 200),
        arrayOf("POST", "/api/users", 201),
        arrayOf("DELETE", "/api/users/1", 204),
        arrayOf("GET", "/api/unknown", 404)
    )

    @Test(dataProvider = "login-credentials")
    @QaseTitle("Login with credentials from data provider")
    fun testWithMultipleParams(username: String, password: String) {
        println("Login with $username / $password")
    }

    @Test(dataProvider = "browsers")
    @QaseId(20)
    @QaseTitle("Browser compatibility test")
    fun testWithSingleParam(browser: String) {
        println("Testing with browser: $browser")
    }

    @Test(dataProvider = "api-endpoints")
    @QaseTitle("API endpoint response codes")
    @QaseFields(value = [
        Field(name = "layer", value = "api"),
    ])
    fun testWithMixedParams(method: String, endpoint: String, expectedStatus: Int) {
        println("$method $endpoint -> $expectedStatus")
    }
}
