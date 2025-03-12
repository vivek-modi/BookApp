package com.tapdoo.network.ktor

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 * Factory class for creating and configuring an instance of [HttpClient] using the Ktor library.
 *
 * This class provides a centralized way to build a pre-configured HTTP client with specific
 * settings for logging, content negotiation, default request headers, response observation,
 * and retry behavior. It leverages the OkHttp engine for network communication.
 */
class KtorClient {

    companion object {
        private const val BASE_URL = "tpbookserver.herokuapp.com"
        private const val HTTP_STATUS_LOG_TAG = "HttpStatus"
        private const val KTOR_CLIENT_LOG_TAG = "KtorClient"
    }

    /**
     * Builds and configures an [HttpClient] instance.
     *
     * @return A configured [HttpClient] ready for making HTTP requests.
     */
    fun build(): HttpClient {
        return HttpClient(OkHttp) {
            // Enable strict response validation.
            expectSuccess = true

            // Configure logging for network requests.
            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.d(KTOR_CLIENT_LOG_TAG, message)
                    }
                }
                level = LogLevel.ALL
            }

            // Set default request configurations.
            defaultRequest {
                // Use a full base url instead of just the host.
                url {
                    protocol = URLProtocol.HTTPS
                    host = BASE_URL
                }
                // Add the content type header.
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }

            // Configure content negotiation for JSON serialization/deserialization
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }

            // Observe and log HTTP response status codes.
            install(ResponseObserver) {
                onResponse { response ->
                    Log.d(HTTP_STATUS_LOG_TAG, "${response.status.value}")
                }
            }

            // Configure retry behavior for requests.
            install(HttpRequestRetry) {
                // disable automatic retries for now.
                retryOnExceptionOrServerErrors(maxRetries = 0)
            }
        }
    }
}