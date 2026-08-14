package io.github.alefaux.foodlist

import io.github.alefaux.foodlist.auth.AuthService
import io.github.alefaux.foodlist.auth.authRoutes
import io.github.alefaux.foodlist.database.DatabaseFactory
import io.github.alefaux.foodlist.database.ProductSyncRepository
import io.github.alefaux.foodlist.database.StorageUnitRepository
import io.github.alefaux.foodlist.database.UserRepository
import io.github.alefaux.foodlist.plugins.configureSecurity
import io.github.alefaux.foodlist.plugins.configureSerialization
import io.github.alefaux.foodlist.plugins.configureStatusPages
import io.github.alefaux.foodlist.sync.SyncService
import io.github.alefaux.foodlist.sync.syncRoutes
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun main() {
    DatabaseFactory.init()

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureSecurity()
    configureStatusPages()

    val authService = AuthService(UserRepository())
    val syncService = SyncService(StorageUnitRepository(), ProductSyncRepository())

    routing {
        get("/") {
            call.respondText(sayHello("Ktor"))
        }
        authRoutes(authService)
        syncRoutes(syncService)
    }
}
