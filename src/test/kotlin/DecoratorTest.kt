import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class DecoratorTest {

    @Test
    fun `process returns Response`() {
        val request = Request("https://endpoint")

        val response = RequestProcessor(request)

        assertResponse(request, response)
    }

    @Test
    fun `can log a response`() {
        val request = Request("https://endpoint")

        val loggingProcessor = LoggingProcessor(RequestProcessor)
        val response = loggingProcessor(request)

        assertResponse(request, response)
    }

    @Test
    fun `can cache a response`() {
        val request = Request("https://endpoint")

        val cachingProcessor = CachingProcessor(RequestProcessor)
        val cachedResponse = cachingProcessor(request)

        assertNotNull(cachedResponse)
        assertResponse(request, cachedResponse)
    }

    private fun assertResponse(request: Request, response: Response) {
        val expectedResponse = "Response: ${request.endpoint}"
        assertEquals(expectedResponse, response.body)
    }
}