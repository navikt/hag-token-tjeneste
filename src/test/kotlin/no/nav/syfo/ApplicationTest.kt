package no.nav.syfo

import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.server.testing.TestApplication
import kotlinx.coroutines.runBlocking
import no.nav.syfo.routes.tokenRouteMedClaim
import no.nav.security.mock.oauth2.MockOAuth2Server
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.security.KeyPairGenerator
import java.util.Base64

private const val PORT = 33445

class ApplicationTest {
    val testApplication =
        TestApplication {
            application {
                tokenRouteMedClaim("systembruker", "nav:syfo/arkivporten")
            }
        }

    val client = testApplication.createClient {}

    val mockOAuth2Server = MockOAuth2Server().apply { start(port = PORT) }

    @BeforeEach
    fun setup() {
        System.setProperty("MASKINPORTEN_PKEY", mockPrivateKey())
    }

    @Test
    fun `systembruker returnerer token`() {
        runBlocking {
            assert(client.get("/systembruker/311910663").bodyAsText().startsWith("eyJ"))
        }
    }
}

fun mockPrivateKey(): String {
    val kpg = KeyPairGenerator.getInstance("RSA").apply { initialize(2048) }
    val base64 = Base64.getEncoder().encodeToString(kpg.generateKeyPair().private.encoded)
    val chunked = base64.chunked(64).joinToString("\n")
    return "-----BEGIN PRIVATE KEY-----\n$chunked\n-----END PRIVATE KEY-----"
}
