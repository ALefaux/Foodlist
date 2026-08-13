package io.github.alefaux.foodlist.auth

import io.github.alefaux.foodlist.auth.dto.ErrorResponse
import io.github.alefaux.foodlist.auth.dto.LoginRequest
import io.github.alefaux.foodlist.auth.dto.RegisterRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.principal
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Route.authRoutes(authService: AuthService) {
    route("/auth") {
        post("/register") {
            val request = call.receive<RegisterRequest>()
            val response = authService.register(request.name, request.email, request.password)
            call.respond(HttpStatusCode.Created, response)
        }

        post("/login") {
            val request = call.receive<LoginRequest>()
            val response = authService.login(request.email, request.password)
            call.respond(HttpStatusCode.OK, response)
        }

        authenticate("auth-jwt") {
            get("/me") {
                val principal = call.principal<JWTPrincipal>()
                val userId = principal?.payload?.getClaim(JwtConfig.USER_ID_CLAIM)?.asLong()
                val user = userId?.let { authService.getUser(it) }

                if (user != null) {
                    call.respond(HttpStatusCode.OK, user)
                } else {
                    call.respond(HttpStatusCode.NotFound, ErrorResponse("User not found."))
                }
            }
        }
    }
}
