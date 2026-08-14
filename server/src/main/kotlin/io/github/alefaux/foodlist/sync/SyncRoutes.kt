package io.github.alefaux.foodlist.sync

import io.github.alefaux.foodlist.auth.JwtConfig
import io.github.alefaux.foodlist.auth.dto.ErrorResponse
import io.github.alefaux.foodlist.sync.dto.SyncRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.syncRoutes(syncService: SyncService) {
    authenticate("auth-jwt") {
        post("/sync") {
            val principal = call.principal<JWTPrincipal>()
            val userId = principal?.payload?.getClaim(JwtConfig.USER_ID_CLAIM)?.asLong()

            if (userId == null) {
                call.respond(HttpStatusCode.Unauthorized, ErrorResponse("Invalid token."))
                return@post
            }

            val request = call.receive<SyncRequest>()
            val response = syncService.sync(userId, request)
            call.respond(HttpStatusCode.OK, response)
        }
    }
}
