rootProject.name = "token-tjeneste"
pluginManagement {
    plugins {
        val kotlinterVersion: String by settings
        id("org.jmailen.kotlinter") version kotlinterVersion
    }
}
