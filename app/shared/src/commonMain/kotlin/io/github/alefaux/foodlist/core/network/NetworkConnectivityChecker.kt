package io.github.alefaux.foodlist.core.network

interface NetworkConnectivityChecker {
    suspend fun isConnected(): Boolean
}
