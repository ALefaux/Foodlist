package io.github.alefaux.foodlist.core.auth

// The OAuth 2.0 "iOS" client ID from Google Cloud Console. Its reversed form
// (com.googleusercontent.apps.<id-prefix>) doubles as the redirect URL scheme for the
// browser-based sign-in flow in IosGoogleAuthProvider.
actual object GoogleAuthConfig {
    actual val clientId: String = "1046062730644-omm477dpnlbskn862jl409gsti7mu9f2.apps.googleusercontent.com"
}
