package io.github.alefaux.foodlist.feature.productdetail.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alefaux.foodlist.core.logging.AppLogging
import io.github.alefaux.foodlist.feature.productdetail.domain.DeleteProductUseCase
import io.github.alefaux.foodlist.feature.productdetail.domain.DiscardProductUseCase
import io.github.alefaux.foodlist.feature.productdetail.domain.GetProductDetailUseCase
import io.github.alefaux.foodlist.feature.productdetail.domain.MoveProductUseCase
import io.github.alefaux.foodlist.feature.productdetail.domain.UpdateProductStockUseCase
import io.github.alefaux.foodlist.feature.productdetail.domain.UpdateProductUseCase
import io.github.alefaux.foodlist.feature.productdetail.presentation.model.ProductDetailUiState
import io.github.alefaux.foodlist.feature.storage.domain.GetStorageUnitsUseCase
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate

class ProductDetailViewModel(
    private val productId: Int,
    private val getProductDetailUseCase: GetProductDetailUseCase,
    private val updateProductStockUseCase: UpdateProductStockUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val moveProductUseCase: MoveProductUseCase,
    private val deleteProductUseCase: DeleteProductUseCase,
    private val discardProductUseCase: DiscardProductUseCase,
    private val getStorageUnitsUseCase: GetStorageUnitsUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState

    fun loadData() {
        viewModelScope.launch(dispatcher) {
            runCatching {
                getProductDetailUseCase(productId)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't load product detail for $productId")
                _uiState.update { it.copy(isLoading = false) }
            }.onSuccess { product ->
                if (product == null) {
                    _uiState.update { it.copy(isLoading = false) }
                    return@onSuccess
                }

                _uiState.update { state ->
                    state.copy(
                        id = product.id,
                        name = product.name,
                        quantity = product.quantity,
                        category = product.category,
                        stock = product.stock,
                        expirationDate = product.expirationDate,
                        createdAt = product.createdAt,
                        storageId = product.storageId,
                        storageName = product.storageName,
                        freshness = product.freshness,
                        statusLabel = product.statusLabel,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun increaseStock() = updateStock(_uiState.value.stock + 1)

    fun decreaseStock() {
        val newStock = _uiState.value.stock - 1
        if (newStock >= 0) updateStock(newStock)
    }

    private fun updateStock(stock: Int) {
        _uiState.update { it.copy(stock = stock) }

        viewModelScope.launch(dispatcher) {
            runCatching {
                updateProductStockUseCase(productId, stock)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't update stock for $productId")
            }
        }
    }

    fun showEditSheet() = _uiState.update { it.copy(isEditSheetVisible = true) }
    fun dismissEditSheet() = _uiState.update { it.copy(isEditSheetVisible = false) }

    fun saveEdit(name: String, quantity: String, category: String, expirationDate: LocalDate?) {
        viewModelScope.launch(dispatcher) {
            runCatching {
                updateProductUseCase(productId, name, quantity, category, expirationDate)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't update product $productId")
            }.onSuccess {
                _uiState.update { it.copy(isEditSheetVisible = false) }
                loadData()
            }
        }
    }

    fun showMoveSheet() {
        _uiState.update { it.copy(isMoveSheetVisible = true) }

        viewModelScope.launch(dispatcher) {
            runCatching {
                getStorageUnitsUseCase()
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't load storage units")
            }.onSuccess { storages ->
                _uiState.update { it.copy(availableStorages = storages.toImmutableList()) }
            }
        }
    }

    fun dismissMoveSheet() = _uiState.update { it.copy(isMoveSheetVisible = false) }

    fun moveToStorage(storageId: Long) {
        viewModelScope.launch(dispatcher) {
            runCatching {
                moveProductUseCase(productId, storageId)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't move product $productId")
            }.onSuccess {
                _uiState.update { it.copy(isMoveSheetVisible = false) }
                loadData()
            }
        }
    }

    fun showDeleteDialog() = _uiState.update { it.copy(isDeleteDialogVisible = true) }
    fun dismissDeleteDialog() = _uiState.update { it.copy(isDeleteDialogVisible = false) }

    fun deleteProduct() {
        viewModelScope.launch(dispatcher) {
            runCatching {
                deleteProductUseCase(productId)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't delete product $productId")
            }.onSuccess {
                _uiState.update { it.copy(isDeleteDialogVisible = false, isDeleted = true) }
            }
        }
    }

    fun showDiscardDialog() = _uiState.update { it.copy(isDiscardDialogVisible = true) }
    fun dismissDiscardDialog() = _uiState.update { it.copy(isDiscardDialogVisible = false) }

    fun discardProduct() {
        viewModelScope.launch(dispatcher) {
            runCatching {
                discardProductUseCase(productId)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't discard product $productId")
            }.onSuccess {
                _uiState.update { it.copy(isDiscardDialogVisible = false, isDeleted = true) }
            }
        }
    }
}
