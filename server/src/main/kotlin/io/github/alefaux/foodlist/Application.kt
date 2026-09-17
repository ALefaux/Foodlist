package io.github.alefaux.foodlist

import io.github.alefaux.foodlist.auth.AuthService
import io.github.alefaux.foodlist.auth.authRoutes
import io.github.alefaux.foodlist.database.DatabaseFactory
import io.github.alefaux.foodlist.database.ProductRepository
import io.github.alefaux.foodlist.database.ProductSyncRepository
import io.github.alefaux.foodlist.database.StorageUnitRepository
import io.github.alefaux.foodlist.database.UserRepository
import io.github.alefaux.foodlist.plugins.configureSecurity
import io.github.alefaux.foodlist.plugins.configureSerialization
import io.github.alefaux.foodlist.plugins.configureStatusPages
import io.github.alefaux.foodlist.search.SearchService
import io.github.alefaux.foodlist.search.openfoodfacts.OpenFoodFactsClient
import io.github.alefaux.foodlist.search.searchRoutes
import io.github.alefaux.foodlist.sync.SyncService
import io.github.alefaux.foodlist.sync.syncRoutes
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.serialization.json.Json

fun main() {
    DatabaseFactory.init()

    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureSecurity()
    configureStatusPages()

    val httpClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    val authService = AuthService(UserRepository())
    val syncService = SyncService(StorageUnitRepository(), ProductSyncRepository())
    val searchService = SearchService(ProductRepository(), OpenFoodFactsClient(httpClient))

    routing {
        get("/") {
            call.respondText(sayHello("Ktor"))
        }
        authRoutes(authService)
        syncRoutes(syncService)
        searchRoutes(searchService)
    }
}
