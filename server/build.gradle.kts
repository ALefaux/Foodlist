plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.serialization)
}

group = "io.github.alefaux.foodlist"
version = "1.0.0"
application {
    mainClass = "io.github.alefaux.foodlist.ApplicationKt"
}

dependencies {
    api(projects.core)
    implementation(libs.logback)
    implementation(libs.ktor.serverCore)
    implementation(libs.ktor.serverNetty)
    implementation(libs.ktor.serverAuth)
    implementation(libs.ktor.serverAuthJwt)
    implementation(libs.ktor.serverContentNegotiation)
    implementation(libs.ktor.serverStatusPages)
    implementation(libs.ktor.serialization.json)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.auth0.java.jwt)
    implementation(libs.jbcrypt)
    implementation(libs.google.api.client)

    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation(libs.exposed.jdbc)
    implementation(libs.h2database)
    implementation(libs.postgresql)

    testImplementation(libs.ktor.serverTestHost)
    testImplementation(libs.kotlinx.coroutines.core)
    testImplementation(kotlin("test"))
}