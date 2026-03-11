package no.nav.helsearbeidsgiver.auth

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@Serializable
@OptIn(ExperimentalSerializationApi::class)
internal data class TokenResponse(
    @JsonNames("access_token")
    val accessToken: String,
    @JsonNames("expires_in")
    val expiresInSeconds: Int,
)

@Serializable
@OptIn(ExperimentalSerializationApi::class)
internal data class ErrorResponse(
    val error: String,
    @JsonNames("error_description")
    val errorDescription: String,
)
