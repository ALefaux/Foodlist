package io.github.alefaux.foodlist.plugins

import io.github.alefaux.foodlist.auth.JwtConfig
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.jwt.JWTPrincipal
import io.ktor.server.auth.jwt.jwt

fun Application.configureSecurity() {
    install(Authentication) {
        jwt("auth-jwt") {
            realm = JwtConfig.REALM
            verifier(JwtConfig.verifier)
            validate { credential ->
                if (credential.payload.getClaim(JwtConfig.USER_ID_CLAIM).asLong() != null) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
        }
    }
}
