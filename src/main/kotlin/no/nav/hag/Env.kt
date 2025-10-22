package no.nav.hag

import io.github.cdimascio.dotenv.dotenv

private val dotenv =
    dotenv {
        ignoreIfMalformed = true
        ignoreIfMissing = true
    }

val maskinportenIntegrasjonsId: String = System.getenv("MASKINPORTEN_INTEGRATION_ID") ?: dotenv["MASKINPORTEN_INTEGRATION_ID"]
val maskinportenKid: String = System.getenv("MASKINPORTEN_KID") ?: dotenv["MASKINPORTEN_KID"]
val maskinportenTokenEndpoint: String = System.getenv("MASKINPORTEN_TOKEN_ENDPOINT") ?: dotenv["MASKINPORTEN_TOKEN_ENDPOINT"]
val maskinportenClientIssuer: String = System.getenv("MASKINPORTEN_CLIENT_ISSUER") ?: dotenv["MASKINPORTEN_CLIENT_ISSUER"]
val maskinportenPrivateKey: String =
    System.getProperty("MASKINPORTEN_PKEY") ?: System.getenv("MASKINPORTEN_PKEY") ?: dotenv["MASKINPORTEN_PKEY"]
