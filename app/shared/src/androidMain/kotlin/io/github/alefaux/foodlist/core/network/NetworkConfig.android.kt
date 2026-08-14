package io.github.alefaux.foodlist.core.network

// 10.0.2.2 is the Android emulator's alias for the host machine's localhost.
// A physical device would need the host's real LAN IP instead.
actual object NetworkConfig {
    actual val baseUrl: String = "http://10.0.2.2:8080"
}
