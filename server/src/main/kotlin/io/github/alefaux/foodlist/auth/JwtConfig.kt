package io.github.alefaux.foodlist.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.slf4j.LoggerFactory
import java.util.Date

object JwtConfig {
    const val ISSUER = "foodlist-server"
    const val AUDIENCE = "foodlist-app"
    const val REALM = "foodlist"
    const val USER_ID_CLAIM = "userId"
    private const val EXPIRY_MILLIS = 7L * 24 * 60 * 60 * 1000

    private val logger = LoggerFactory.getLogger(JwtConfig::class.java)

    private val secret: String = System.getenv("JWT_SECRET") ?: run {
        logger.warn(
            "JWT_SECRET environment variable not set - using an insecure default secret. " +
                "Set JWT_SECRET before deploying this server anywhere reachable."
        )
        "insecure-dev-secret-change-me-in-production"
    }

    private val algorithm = Algorithm.HMAC256(secret)

    val verifier = JWT.require(algorithm)
        .withIssuer(ISSUER)
        .withAudience(AUDIENCE)
        .build()

    fun generateToken(userId: Long, email: String): String =
        JWT.create()
            .withIssuer(ISSUER)
            .withAudience(AUDIENCE)
            .withSubject(email)
            .withClaim(USER_ID_CLAIM, userId)
            .withExpiresAt(Date(System.currentTimeMillis() + EXPIRY_MILLIS))
            .sign(algorithm)
}
