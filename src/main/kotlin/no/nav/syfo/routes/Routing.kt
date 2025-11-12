package no.nav.syfo.routes

import io.ktor.http.HttpStatusCode
import io.ktor.http.HttpStatusCode.Companion.BadRequest
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import no.nav.syfo.maskinportenClientIssuer
import no.nav.syfo.maskinportenIntegrasjonsId
import no.nav.syfo.maskinportenKid
import no.nav.syfo.maskinportenPrivateKey
import no.nav.syfo.maskinportenTokenEndpoint
import no.nav.helsearbeidsgiver.maskinporten.MaskinportenClient
import no.nav.helsearbeidsgiver.maskinporten.MaskinportenClientConfigPkey
import no.nav.helsearbeidsgiver.maskinporten.getSystemBrukerClaim

fun Application.tokenRouteMedClaim(
    path: String,
    defaultScope: String,
) {
    routing {
        get("/$path/{orgNr}") {
            val orgNr = call.parameters["orgNr"] ?: return@get call.respondText("Mangler orgNr", status = BadRequest)
            val providedScope = call.request.queryParameters["scope"]

            try {
                val config =
                    MaskinportenClientConfigPkey(
                        kid = maskinportenKid,
                        privateKey = maskinportenPrivateKey,
                        issuer = maskinportenClientIssuer,
                        scope = providedScope ?: defaultScope,
                        clientId = maskinportenIntegrasjonsId,
                        endpoint = maskinportenTokenEndpoint,
                        additionalClaims = getSystemBrukerClaim(orgNr),
                    )

                val token = MaskinportenClient(config).fetchNewAccessToken()

                call.respondText(token.tokenResponse.accessToken, status = HttpStatusCode.OK)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Feil: ${e.message}")
            }
        }
    }
}

fun Application.tokenRouteUtenClaim(
    path: String,
    scope: String,
) {
    routing {
        get("/$path") {
            try {
                val config =
                    MaskinportenClientConfigPkey(
                        kid = maskinportenKid,
                        privateKey = maskinportenPrivateKey,
                        issuer = maskinportenClientIssuer,
                        scope = scope,
                        clientId = maskinportenIntegrasjonsId,
                        endpoint = maskinportenTokenEndpoint,
                    )

                val token = MaskinportenClient(config).fetchNewAccessToken()

                call.respondText(token.tokenResponse.accessToken, status = HttpStatusCode.OK)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.InternalServerError, "Feil: ${e.message}")
            }
        }
    }
}
