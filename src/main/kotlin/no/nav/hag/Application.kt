package no.nav.hag

import io.ktor.server.application.Application
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import no.nav.hag.routes.tokenRouteMedClaim
import no.nav.hag.routes.tokenRouteUtenClaim
import org.slf4j.LoggerFactory

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val logger = LoggerFactory.getLogger(Application::class.java)
    logger.info("Started App")

    routing {
        get("/intern/hemmelig-endepunkt"){
            call.respondText("Dette er et hemmelig endepunkt")
        }
        get("/open/ikke-hemmelig-endepunkt"){
            call.respondText("Bare en åpen endepunkt")
        }
    }

    tokenRouteMedClaim("systembruker", "nav:helseytelser/sykepenger")
    tokenRouteMedClaim("dialogporten", "digdir:dialogporten")
    tokenRouteMedClaim("subscribe", "altinn:events.subscribe")

    tokenRouteUtenClaim("systemregister", "altinn:authentication/systemregister.write")
    tokenRouteUtenClaim(
        "systembruker-forespoersel",
        "altinn:authentication/systemuser.request.read altinn:authentication/systemuser.request.write",
    )
}
