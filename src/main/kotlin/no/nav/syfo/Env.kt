package no.nav.syfo

val maskinportenIntegrasjonsId: String = System.getenv("MASKINPORTEN_CLIENT_ID")
val maskinportenKid: String = System.getenv("MASKINPORTEN_KID")
val maskinportenTokenEndpoint: String = System.getenv("MASKINPORTEN_TOKEN_ENDPOINT")
val maskinportenClientIssuer: String = System.getenv("MASKINPORTEN_CLIENT_ISSUER")
val maskinportenPrivateKey: String = System.getProperty("MASKINPORTEN_PKEY") ?: System.getenv("MASKINPORTEN_PKEY")
