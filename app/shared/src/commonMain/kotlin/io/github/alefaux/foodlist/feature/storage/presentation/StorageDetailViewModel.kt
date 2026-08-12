package io.github.alefaux.foodlist.feature.storage.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alefaux.foodlist.core.logging.AppLogging
import io.github.alefaux.foodlist.core.model.ProductFreshness
import io.github.alefaux.foodlist.core.model.extension.toFreshness
import io.github.alefaux.foodlist.core.model.extension.toLocalDate
import io.github.alefaux.foodlist.feature.storage.domain.DeleteStorageUnitUseCase
import io.github.alefaux.foodlist.feature.storage.domain.GetStorageDetailUseCase
import io.github.alefaux.foodlist.feature.storage.domain.StorageProduct
import io.github.alefaux.foodlist.feature.storage.modelui.StorageProductUi
import io.github.alefaux.foodlist.feature.storage.presentation.model.StorageDetailUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import kotlin.time.Clock

class StorageDetailViewModel(
    private val storageId: Long,
    private val getStorageDetailUseCase: GetStorageDetailUseCase,
    private val deleteStorageUnitUseCase: DeleteStorageUnitUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow(StorageDetailUiState())
    val uiState: StateFlow<StorageDetailUiState> = _uiState

    private var allProducts: List<StorageProduct> = emptyList()

    fun loadData() {
        viewModelScope.launch(dispatcher) {
            runCatching {
                getStorageDetailUseCase(storageId)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't load storage detail for $storageId")
            }.onSuccess { detail ->
                if (detail == null) return@onSuccess

                allProducts = detail.products
                val categories = listOf("All") + detail.products.map { it.category }.distinct().sorted()

                _uiState.update { state ->
                    state.copy(
                        storageName = detail.name,
                        categories = categories.toImmutableList(),
                        selectedCategory = if (state.selectedCategory in categories) {
                            state.selectedCategory
                        } else {
                            "All"
                        },
                        isLoading = false
                    )
                }
                applyFilter()
            }
        }
    }

    fun selectCategory(category: String) {
        _uiState.update { it.copy(selectedCategory = category) }
        applyFilter()
    }

    fun showDeleteDialog() {
        _uiState.update { it.copy(isDeleteDialogVisible = true) }
    }

    fun dismissDeleteDialog() {
        _uiState.update { it.copy(isDeleteDialogVisible = false) }
    }

    fun deleteStorageUnit() {
        viewModelScope.launch(dispatcher) {
            runCatching {
                deleteStorageUnitUseCase(storageId)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't delete storage unit $storageId")
            }.onSuccess {
                _uiState.update { it.copy(isDeleteDialogVisible = false, isDeleted = true) }
            }
        }
    }

    private fun applyFilter() {
        val today = Clock.System.now().toLocalDate()
        val selected = _uiState.value.selectedCategory

        val filtered = allProducts
            .filter { selected == "All" || it.category == selected }
            .map { product ->
                val freshness = product.expirationDate.toFreshness(today)
                StorageProductUi(
                    id = product.id,
                    name = product.name,
                    quantity = product.quantity,
                    category = product.category,
                    freshness = freshness,
                    statusLabel = statusLabel(freshness, today, product.expirationDate)
                )
            }

        _uiState.update { state ->
            state.copy(
                products = filtered.toImmutableList(),
                totalCount = allProducts.size,
                expiringCount = allProducts.count {
                    it.expirationDate.toFreshness(today) != ProductFreshness.FRESH
                }
            )
        }
    }

    private fun statusLabel(
        freshness: ProductFreshness,
        today: LocalDate,
        expirationDate: LocalDate?
    ): String = when (freshness) {
        ProductFreshness.EXPIRED -> "Expired"
        ProductFreshness.EXPIRING_SOON -> {
            val days = expirationDate?.let { today.daysUntil(it) } ?: 0
            if (days <= 0) "Expiring today" else "Expiring in $days day${if (days == 1) "" else "s"}"
        }

        ProductFreshness.FRESH -> "Fresh"
    }
}
