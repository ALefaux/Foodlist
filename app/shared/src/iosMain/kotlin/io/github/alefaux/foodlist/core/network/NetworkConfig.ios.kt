package io.github.alefaux.foodlist.core.network

// The iOS Simulator shares the host machine's network, so localhost works directly.
// A physical device would need the host's real LAN IP instead.
actual object NetworkConfig {
    actual val baseUrl: String = "http://localhost:8080"
}
