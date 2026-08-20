package io.github.alefaux.foodlist.core.auth

/**
 * Platform-specific handle used to present the Google sign-in UI (an Android [android.content.Context]
 * on Android, an opaque placeholder on iOS where the presenting view controller is resolved internally).
 */
expect class GoogleSignInContext

interface GoogleAuthProvider {
    /** Launches the platform's Google sign-in flow and returns a Google ID token on success. */
    suspend fun signIn(context: GoogleSignInContext): Result<String>
}
