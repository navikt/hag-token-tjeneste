package no.nav.hag

import io.github.cdimascio.dotenv.dotenv

private val dotenv =
    dotenv {
        ignoreIfMalformed = true
        ignoreIfMissing = true
    }

val maskinportenIntegrasjonsId: String = System.getenv("MASKINPORTEN_INTEGRATION_ID") ?: dotenv["MASKINPORTEN_INTEGRATION_ID"]
val maskinportenKid: String = System.getenv("MASKINPORTEN_KID") ?: dotenv["MASKINPORTEN_KID"]
val maskinportenPrivateKey: String = System.getenv("MASKINPORTEN_PKEY") ?: dotenv["MASKINPORTEN_PKEY"]
