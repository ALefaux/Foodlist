package io.github.alefaux.foodlist.core.auth

expect object GoogleAuthConfig {
    /**
     * The OAuth 2.0 client ID that the server verifies the Google ID token against.
     * Get these from https://console.cloud.google.com/apis/credentials.
     */
    val clientId: String
}
