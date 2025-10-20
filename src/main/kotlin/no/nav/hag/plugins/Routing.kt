package no.nav.hag.plugins

import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.server.application.Application
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import no.nav.hag.maskinportenIntegrasjonsId
import no.nav.hag.maskinportenKid
import no.nav.hag.maskinportenPrivateKey
import no.nav.helsearbeidsgiver.maskinporten.MaskinportenClient
import no.nav.helsearbeidsgiver.maskinporten.MaskinportenClientConfigPkey
import no.nav.helsearbeidsgiver.maskinporten.getSystemBrukerClaim

private const val SCOPE = "nav:helseytelser:sykepenger"
private const val MASKINPORTEN_TOKEN_ENDPOINT = "https://test.maskinporten.no/token"
private const val MASKINPORTEN_CLIENT_ISSUER = "https://test.maskinporten.no/"

fun Application.configureRouting() {
    routing {
        get("/token/{orgNr}") {
            val orgNr = call.parameters["orgNr"] ?: return@get call.respondText("Mangler orgNr", status = BadRequest)

            val config =
                MaskinportenClientConfigPkey(
                    kid = maskinportenKid,
                    privateKey = maskinportenPrivateKey,
                    issuer = MASKINPORTEN_CLIENT_ISSUER,
                    scope = SCOPE,
                    clientId = maskinportenIntegrasjonsId,
                    endpoint = MASKINPORTEN_TOKEN_ENDPOINT,
                    additionalClaims = getSystemBrukerClaim(orgNr),
                )

            val token = MaskinportenClient(config).fetchNewAccessToken()

            call.respond(token)
        }
    }
}
