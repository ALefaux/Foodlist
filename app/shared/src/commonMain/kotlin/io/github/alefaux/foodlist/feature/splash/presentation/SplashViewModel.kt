package io.github.alefaux.foodlist.feature.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alefaux.foodlist.core.logging.AppLogging
import io.github.alefaux.foodlist.core.network.NetworkConfig
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull

class SplashViewModel(
    private val httpClient: HttpClient,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady

    init {
        wakeUpServer()
    }

    private fun wakeUpServer() {
        viewModelScope.launch(dispatcher) {
            // Render's free tier spins a sleeping instance back up on the first request,
            // which can take up to ~50s. Ping the server here so it's warm by the time the
            // user reaches a screen that needs it, but never block startup indefinitely.
            withTimeoutOrNull(WAKE_UP_TIMEOUT_MILLIS) {
                runCatching { httpClient.get(NetworkConfig.baseUrl) }
                    .onFailure { error -> AppLogging.e(error, "Couldn't wake up the server") }
            }
            _isReady.update { true }
        }
    }

    private companion object {
        const val WAKE_UP_TIMEOUT_MILLIS = 45_000L
    }
}
