import org.testng.annotations.DataProvider
import org.testng.annotations.Parameters
import org.testng.annotations.Test

class ParametrisedTests {

    @DataProvider(name = "data-provider")
    fun dataProviderMethod(): Array<Array<Any>> {
        return arrayOf(
            arrayOf("data one"),
            arrayOf("data two"),
            arrayOf("data three")
        )
    }

    @Test(dataProvider = "data-provider")
    @Parameters("data")
    fun testMethod_success1(data: String) {
        println("Data is: $data")
    }

    @Test(dataProvider = "data-provider")
    @Parameters("data")
    fun testMethod_failed1(data: String) {
        println("Data is: $data")
        throw RuntimeException("Test failed")
    }
}
