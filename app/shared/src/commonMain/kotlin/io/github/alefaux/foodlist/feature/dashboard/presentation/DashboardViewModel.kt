package io.github.alefaux.foodlist.feature.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alefaux.foodlist.core.build.AppBuildInfo
import io.github.alefaux.foodlist.core.logging.AppLogging
import io.github.alefaux.foodlist.core.network.NetworkConfig
import io.github.alefaux.foodlist.feature.dashboard.domain.GetExpiredProductsUseCase
import io.github.alefaux.foodlist.feature.dashboard.modelui.ExpiredProductUi
import io.github.alefaux.foodlist.feature.dashboard.presentation.model.DashboardUiState
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getExpiredProductsUseCase: GetExpiredProductsUseCase,
    private val httpClient: HttpClient,
    private val appBuildInfo: AppBuildInfo,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState(isDebug = appBuildInfo.isDebug))
    val uiState: StateFlow<DashboardUiState> = _uiState

    fun loadData() {
        viewModelScope.launch(dispatcher) {
            // Todo fetch data for dashboard
            loadExpiredProducts()
            checkServerStatus()
        }
    }

    private fun checkServerStatus() {
        if (!appBuildInfo.isDebug) return

        viewModelScope.launch(dispatcher) {
            val isServerUp = runCatching {
                httpClient.get(NetworkConfig.baseUrl).status.isSuccess()
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't reach the server")
            }.getOrDefault(false)

            _uiState.update { state -> state.copy(isServerUp = isServerUp) }
        }
    }

    private fun loadExpiredProducts() {
        viewModelScope.launch(dispatcher) {
            runCatching {
                getExpiredProductsUseCase()
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't load expired products")
            }.onSuccess { products ->
                AppLogging.d("Successfully loaded expired products ${products.size}")

                _uiState.update { state ->
                    state.copy(
                        expiredProducts = products
                            .map { product ->
                                ExpiredProductUi(
                                    id = product.id,
                                    name = product.name,
                                    stockageName = "",
                                    expiredSince = ""
                                )
                            }.toImmutableList(),
                        expiredProductsCount = products.size
                    )
                }
            }
        }
    }
}