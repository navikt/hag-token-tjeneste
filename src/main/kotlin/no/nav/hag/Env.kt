package no.nav.hag

import com.typesafe.config.ConfigFactory
import io.ktor.server.config.HoconApplicationConfig

private val appConfig = HoconApplicationConfig(ConfigFactory.load())
val maskinportenIntegrasjonsId: String = System.getenv("MASKINPORTEN_INTEGRATION_ID")
val maskinportenKid: String = System.getenv("MASKINPORTEN_KID")
val maskinportenTokenEndpoint: String = System.getenv("MASKINPORTEN_TOKEN_ENDPOINT")
val maskinportenClientIssuer: String = System.getenv("MASKINPORTEN_CLIENT_ISSUER")
val maskinportenPrivateKey: String = System.getProperty("MASKINPORTEN_PKEY") ?: System.getenv("MASKINPORTEN_PKEY")

object Env {
    object Nais {
        val tokenEndpoint = "NAIS_TOKEN_ENDPOINT".fromEnv()
    }

    object Altinn {
        val tokenAltinn3ExchangeEndpoint =
            "${"ALTINN_3_BASE_URL".fromEnv()}/authentication/api/v1/exchange/maskinporten"
        val dialogportenScope = "DIALOGPORTEN_SCOPE".fromEnv()
    }
}

fun String.fromEnv(): String =
    System.getenv(this)
        ?: appConfig.propertyOrNull(this)?.getString()
        ?: throw RuntimeException("Missing required environment variable \"$this\".")
