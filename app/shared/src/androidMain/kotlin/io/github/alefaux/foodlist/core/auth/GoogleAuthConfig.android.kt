package io.github.alefaux.foodlist.core.auth

// The OAuth 2.0 "Web application" client ID from Google Cloud Console. Android's Credential
// Manager needs this (not an Android-type client ID) as the server audience, so the resulting
// ID token can be verified by our backend against GOOGLE_OAUTH_CLIENT_IDS.
actual object GoogleAuthConfig {
    actual val clientId: String = "REPLACE-WITH-WEB-CLIENT-ID.apps.googleusercontent.com"
}
