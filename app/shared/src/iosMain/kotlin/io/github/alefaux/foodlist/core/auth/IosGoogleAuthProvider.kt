package io.github.alefaux.foodlist.core.auth

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.suspendCancellableCoroutine
import platform.AuthenticationServices.ASPresentationAnchor
import platform.AuthenticationServices.ASWebAuthenticationPresentationContextProvidingProtocol
import platform.AuthenticationServices.ASWebAuthenticationSession
import platform.Foundation.NSURL
import platform.UIKit.UIApplication
import platform.UIKit.UIWindow
import platform.darwin.NSObject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.random.Random

/**
 * Signs in with Google via a system browser sheet (no third-party SDK / CocoaPods dependency
 * needed) using the OAuth 2.0 implicit ID token flow, mirroring what GoogleSignIn-iOS does
 * under the hood.
 */
@OptIn(ExperimentalForeignApi::class)
class IosGoogleAuthProvider : GoogleAuthProvider {

    override suspend fun signIn(context: GoogleSignInContext): Result<String> {
        val clientId = GoogleAuthConfig.clientId
        val redirectScheme = reversedClientIdScheme(clientId)
        val authUrl = buildAuthUrl(clientId, redirectScheme)
            ?: return Result.failure(IllegalStateException("Couldn't build the Google sign-in URL."))

        return try {
            val callbackUrl = startSession(authUrl, redirectScheme)
            val idToken = idTokenFrom(callbackUrl)
                ?: return Result.failure(IllegalStateException("Google didn't return a sign-in token."))
            Result.success(idToken)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun buildAuthUrl(clientId: String, redirectScheme: String): NSURL? {
        val query = listOf(
            "client_id" to clientId,
            "redirect_uri" to "$redirectScheme:/oauth2redirect",
            "response_type" to "id_token",
            "scope" to "openid email profile",
            "nonce" to randomToken(),
            "prompt" to "select_account"
        ).joinToString("&") { (key, value) -> "$key=${value.percentEncoded()}" }

        return NSURL(string = "https://accounts.google.com/o/oauth2/v2/auth?$query")
    }

    private suspend fun startSession(url: NSURL, callbackScheme: String): NSURL =
        suspendCancellableCoroutine { continuation ->
            val presentationContextProvider = PresentationContextProvider()

            val session = ASWebAuthenticationSession(uRL = url, callbackURLScheme = callbackScheme) { callbackUrl, error ->
                when {
                    callbackUrl != null -> continuation.resume(callbackUrl)
                    error != null -> continuation.resumeWithException(IllegalStateException(error.localizedDescription))
                    else -> continuation.resumeWithException(IllegalStateException("Google sign-in was cancelled."))
                }
            }
            session.presentationContextProvider = presentationContextProvider
            session.prefersEphemeralWebBrowserSession = true

            continuation.invokeOnCancellation { session.cancel() }

            if (!session.start()) {
                continuation.resumeWithException(IllegalStateException("Couldn't start the Google sign-in session."))
            }
        }

    private fun idTokenFrom(callbackUrl: NSURL): String? {
        val fragment = callbackUrl.fragment ?: return null
        return fragment.split("&")
            .map { it.split("=", limit = 2) }
            .firstOrNull { it.size == 2 && it[0] == "id_token" }
            ?.get(1)
    }

    private fun reversedClientIdScheme(clientId: String): String {
        val prefix = clientId.removeSuffix(".apps.googleusercontent.com")
        return "com.googleusercontent.apps.$prefix"
    }

    private fun randomToken(): String =
        Random.nextBytes(16).joinToString(separator = "") { byte -> ((byte.toInt() and 0xFF) or 0x100).toString(16).substring(1) }

    private fun String.percentEncoded(): String = buildString {
        for (byte in encodeToByteArray()) {
            val char = byte.toInt().toChar()
            if (char.isLetterOrDigit() || char in "-._~") {
                append(char)
            } else {
                append('%')
                append(((byte.toInt() and 0xFF) or 0x100).toString(16).uppercase().substring(1))
            }
        }
    }
}

@OptIn(ExperimentalForeignApi::class)
private class PresentationContextProvider : NSObject(), ASWebAuthenticationPresentationContextProvidingProtocol {
    override fun presentationAnchorForWebAuthenticationSession(session: ASWebAuthenticationSession): ASPresentationAnchor {
        val windows = UIApplication.sharedApplication.windows.filterIsInstance<UIWindow>()
        return windows.firstOrNull { it.isKeyWindow() } ?: windows.firstOrNull()
    }
}
