rootProject.name = "token-tjeneste"
pluginManagement {
    plugins {
        val ktorVersion: String by settings
        val kotlinterVersion: String by settings
        id("org.jmailen.kotlinter") version kotlinterVersion
        id("io.ktor.plugin") version ktorVersion
    }
}
