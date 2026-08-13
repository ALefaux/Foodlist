package io.github.alefaux.foodlist.plugins

import io.github.alefaux.foodlist.auth.EmailAlreadyExistsException
import io.github.alefaux.foodlist.auth.InvalidCredentialsException
import io.github.alefaux.foodlist.auth.ValidationException
import io.github.alefaux.foodlist.auth.dto.ErrorResponse
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.statuspages.StatusPages
import io.ktor.server.response.respond

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<ValidationException> { call, cause ->
            call.respond(HttpStatusCode.BadRequest, ErrorResponse(cause.message ?: "Invalid request."))
        }
        exception<EmailAlreadyExistsException> { call, cause ->
            call.respond(HttpStatusCode.Conflict, ErrorResponse(cause.message ?: "Email already exists."))
        }
        exception<InvalidCredentialsException> { call, cause ->
            call.respond(HttpStatusCode.Unauthorized, ErrorResponse(cause.message ?: "Invalid credentials."))
        }
        exception<Throwable> { call, _ ->
            call.respond(HttpStatusCode.InternalServerError, ErrorResponse("Something went wrong."))
        }
    }
}
