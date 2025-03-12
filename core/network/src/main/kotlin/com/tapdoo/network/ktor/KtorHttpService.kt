package com.tapdoo.network.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.isSuccess
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class KtorHttpService(val ioDispatcher: CoroutineDispatcher, val client: HttpClient) {

    /**
     * Executes a GET request to the specified route and returns the response body as the specified type.
     *
     * This function is a suspend function, meaning it can be paused and resumed, making it suitable for
     * asynchronous operations like network requests. It uses the provided [client] to make the HTTP request
     * and the [ioDispatcher] to perform the request on a background thread.
     *
     * The function handles HTTP errors by checking if the response status is successful. If the status is
     * not successful, it throws an [HttpException] with the status code and an error message.
     *
     * @param route The URL route to send the GET request to.
     * @param Response The expected type of the response body. This must be a reified type parameter,
     *                 allowing the function to access the type information at runtime.
     * @return A [Result] containing either:
     *          - The parsed response body of type [Response] if the request was successful.
     *          - An exception if the request failed or the response could not be parsed.
     *             The exception can be an [HttpException] for HTTP errors or any other exception that might
     *             occur during the request or parsing process.
     * @throws HttpException If the HTTP response status code indicates an error.
     * @throws Exception If any other error occurs during the request or response parsing.
     */
    suspend inline fun <reified Response : Any> get(route: String): Result<Response> {
        return withContext(ioDispatcher) {
            runCatching {
                val response: HttpResponse = client.get(route)

                // Handle HTTP errors gracefully.
                if (!response.status.isSuccess()) {
                    throw HttpException(
                        response.status.value,
                        "Request to $route failed with status code ${response.status.value}"
                    )
                }

                response.body<Response>()
            }
        }
    }
}

/**
 * Exception to represent HTTP-related errors.
 *
 * @property statusCode The HTTP status code associated with the error.
 * @property message The error message.
 */
class HttpException(private val statusCode: Int, override val message: String) : Exception(message)