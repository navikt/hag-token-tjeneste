val kotlinVersion = "2.3.21"
val logbackVersion = "1.5.32"
val mockOauth2ServerVersion = "3.0.3"
val junitJupiterVersion = "6.0.3"
val logstashVersion = "9.0"
val maskinportenClientVersion = "0.3.0-SNAPSHOT"

plugins {
    kotlin("jvm") version "2.3.21"
    kotlin("plugin.serialization") version "2.2.21"
    id("io.ktor.plugin") version "3.5.0"
    id("org.jlleitschuh.gradle.ktlint") version "14.2.0"
}

group = "no.nav.syfo"
version = "0.0.1"

application {
    mainClass.set("no.nav.syfo.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    val githubPassword: String by project
    mavenCentral()
    maven {
        setUrl("https://maven.pkg.github.com/navikt/*")
        credentials {
            username = "x-access-token"
            password = githubPassword
        }
    }
}

dependencies {
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.ktor:ktor-client-apache5")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-html-builder")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.2")
    implementation("io.ktor:ktor-client-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.2")
    implementation("io.ktor:ktor-client-content-negotiation")
    implementation("io.ktor:ktor-serialization-kotlinx-json")

    implementation("no.nav.helsearbeidsgiver:maskinporten-client:$maskinportenClientVersion")

    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5:$kotlinVersion")
    testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")
    testImplementation("no.nav.security:mock-oauth2-server:$mockOauth2ServerVersion")
}

tasks.test {
    useJUnitPlatform()
    environment("MASKINPORTEN_TOKEN_ENDPOINT", "http://localhost:33445/default/token")
    environment("MASKINPORTEN_CLIENT_ISSUER", "default")
    environment("MASKINPORTEN_KID", "kid")
    environment("MASKINPORTEN_CLIENT_ID", "integrationId")
}

tasks.register("sjekkIngenPublicNais") {
    doLast {
        val naisDir = file("nais")
        val file = naisDir.resolve("nais-dev.yaml")
        check(naisDir.listFiles()?.size == 1 && file.exists()) { "nais må inneholde nøyaktig én fil kalt nais-dev.yaml" }
        check(!file.readText().contains("ekstern.dev.nav.no")) { "Ikke tillat 'ekstern.dev.nav.no' i dev.yaml" }
    }
}

tasks.named("check").configure {
    dependsOn("sjekkIngenPublicNais")
}
