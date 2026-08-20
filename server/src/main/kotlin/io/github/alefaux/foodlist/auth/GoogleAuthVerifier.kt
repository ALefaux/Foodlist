package io.github.alefaux.foodlist.auth

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import org.slf4j.LoggerFactory

class GoogleAuthVerifier(
    audiences: List<String> = System.getenv("GOOGLE_OAUTH_CLIENT_IDS")
        ?.split(",")
        ?.map { it.trim() }
        ?.filter { it.isNotBlank() }
        ?: emptyList()
) {
    private val logger = LoggerFactory.getLogger(GoogleAuthVerifier::class.java)

    private val verifier: GoogleIdTokenVerifier? = if (audiences.isEmpty()) {
        logger.warn(
            "GOOGLE_OAUTH_CLIENT_IDS environment variable not set - Google sign-in requests will be rejected. " +
                "Set it to the comma-separated list of allowed OAuth client IDs before enabling Google sign-in."
        )
        null
    } else {
        GoogleIdTokenVerifier.Builder(GoogleNetHttpTransport.newTrustedTransport(), GsonFactory.getDefaultInstance())
            .setAudience(audiences)
            .build()
    }

    fun verify(idToken: String): GoogleIdToken.Payload {
        val token = verifier?.verify(idToken) ?: throw InvalidGoogleTokenException()
        val payload = token.payload

        if (payload.emailVerified != true) {
            throw InvalidGoogleTokenException()
        }

        return payload
    }
}
