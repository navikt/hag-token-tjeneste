package no.nav.hag.auth

import io.ktor.client.HttpClient
import io.ktor.client.engine.apache5.Apache5
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(): HttpClient =
    HttpClient(Apache5) {
        expectSuccess = true
        install(ContentNegotiation) {
            json(jsonConfig)
        }
    }

val jsonConfig =
    Json {
        ignoreUnknownKeys = true
        decodeEnumsCaseInsensitive = true
    }
