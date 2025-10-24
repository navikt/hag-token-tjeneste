val kotlin_version: String by project
val logback_version: String by project
val mockOauth2ServerVersion: String by project

plugins {
    kotlin("jvm") version "2.2.21"
    kotlin("plugin.serialization") version "2.2.21"
    id("io.ktor.plugin") version "3.3.1"
    id("org.jmailen.kotlinter") version "4.4.1"
}

group = "no.nav.hag"
version = "0.0.1"

application {
    mainClass.set("no.nav.hag.ApplicationKt")

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
    implementation("net.logstash.logback:logstash-logback-encoder:8.0")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    implementation("io.ktor:ktor-client-apache5")
    implementation("io.ktor:ktor-server-auth-jvm")
    implementation("io.ktor:ktor-server-auth-jwt-jvm")
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-html-builder")
    implementation("io.ktor:ktor-server-netty-jvm")

    implementation("no.nav.helsearbeidsgiver:maskinporten-client:0.3.0-SNAPSHOT")

    testImplementation("io.ktor:ktor-server-test-host-jvm")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("no.nav.security:mock-oauth2-server:$mockOauth2ServerVersion")
}

tasks.test {
    useJUnitPlatform()
    environment("MASKINPORTEN_TOKEN_ENDPOINT", "http://localhost:33445/default/token")
    environment("MASKINPORTEN_CLIENT_ISSUER", "default")
    environment("MASKINPORTEN_KID", "kid")
    environment("MASKINPORTEN_INTEGRATION_ID", "integrationId")
}

tasks.register("sjekkIngenPublicNais") {
    doLast {
        val naisDir = file(".nais")
        val file = naisDir.resolve("dev.yaml")
        check(naisDir.listFiles()?.size == 1 && file.exists()) { ".nais må inneholde nøyaktig én fil kalt dev.yaml" }
        check(!file.readText().contains("ekstern.dev.nav.no")) { "Ikke tillat 'ekstern.dev.nav.no' i dev.yaml" }
    }
}

tasks.named("check").configure {
    dependsOn("sjekkIngenPublicNais")
}
