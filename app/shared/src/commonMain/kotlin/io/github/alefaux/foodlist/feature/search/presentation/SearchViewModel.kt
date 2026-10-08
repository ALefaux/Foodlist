package io.github.alefaux.foodlist.feature.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alefaux.foodlist.core.logging.AppLogging
import io.github.alefaux.foodlist.feature.search.domain.SearchProductsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

class SearchViewModel(
    private val searchProductsUseCase: SearchProductsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ViewModel() {
    fun searchProduct(query: String) {
        if (query.length < 3) return

        viewModelScope.launch(dispatcher) {
            runCatching {
                searchProductsUseCase(query)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't search products")
            }.onSuccess { products ->
                AppLogging.d("Successfully searched products - ${products.size}")
            }
        }
    }
}