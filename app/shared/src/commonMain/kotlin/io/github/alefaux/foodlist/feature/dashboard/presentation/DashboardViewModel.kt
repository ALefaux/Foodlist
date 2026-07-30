package io.github.alefaux.foodlist.feature.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alefaux.foodlist.core.logging.AppLogging
import io.github.alefaux.foodlist.feature.dashboard.domain.GetExpiredProductsUseCase
import io.github.alefaux.foodlist.feature.dashboard.modelui.ExpiredProductUi
import io.github.alefaux.foodlist.feature.dashboard.presentation.model.DashboardUiState
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
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState

    fun loadData() {
        viewModelScope.launch(dispatcher) {
            // Todo fetch data for dashboard
            loadExpiredProducts()
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
                        expiredProducts = products.take(MAX_EXPIRED_PRODUCTS_DISPLAYED)
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

    companion object {
        private const val MAX_EXPIRED_PRODUCTS_DISPLAYED = 2
    }
}