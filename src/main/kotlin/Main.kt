data class Request(val endpoint: String)
data class Response(val body: String)

fun interface Processor : (Request) -> Response

val LoggingProcessor = { processor: Processor ->
    Processor { request: Request -> processor(request).also(::println) }
}

private val cache = mutableMapOf<Request, Response>()
val CachingProcessor = { processor: Processor ->
    Processor { request: Request ->
        cache[request] ?: processor(request).also { cache[request] = it }
    }
}

val RequestProcessor = Processor { request ->
    Response("Response: ${request.endpoint}")
}

fun main() {
    val cachingProcessor = CachingProcessor(LoggingProcessor(RequestProcessor))
    val request = Request("https://endpoint")
    cachingProcessor(request)
}