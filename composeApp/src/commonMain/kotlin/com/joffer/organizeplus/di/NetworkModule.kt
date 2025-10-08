package com.joffer.organizeplus.di

import com.joffer.organizeplus.BuildConfig
import io.github.aakira.napier.Napier
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module

private const val API_KEY_HEADER = "x-api-key"

val networkModule = module {
    // HTTP Client
    single<HttpClient> {
        HttpClient {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                        prettyPrint = true
                        isLenient = true
                    }
                )
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        Napier.d(message, tag = "HTTP")
                    }
                }
                level = LogLevel.ALL
            }

            // Add default headers to all requests
            defaultRequest {
                header(HttpHeaders.Accept, ContentType.Application.Json.toString())
                header(API_KEY_HEADER, BuildConfig.API_KEY)
            }
        }
    }
}
