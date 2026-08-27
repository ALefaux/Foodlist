package io.github.alefaux.foodlist.feature.scan.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.github.alefaux.foodlist.core.logging.AppLogging
import io.github.alefaux.foodlist.core.network.NetworkConnectivityChecker
import io.github.alefaux.foodlist.feature.scan.domain.AddScannedProductUseCase
import io.github.alefaux.foodlist.feature.scan.domain.LookupProductUseCase
import io.github.alefaux.foodlist.feature.scan.domain.ScannedProduct
import io.github.alefaux.foodlist.feature.scan.modelui.ScannedProductUi
import io.github.alefaux.foodlist.feature.scan.presentation.model.ScanUiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ScanViewModel(
    private val networkConnectivityChecker: NetworkConnectivityChecker,
    private val lookupProductUseCase: LookupProductUseCase,
    private val addScannedProductUseCase: AddScannedProductUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _uiState = MutableStateFlow<ScanUiState>(ScanUiState.CheckingConnectivity)
    val uiState: StateFlow<ScanUiState> = _uiState

    private var scannedProduct: ScannedProduct? = null

    fun checkConnectivity() {
        val state = _uiState.value
        if (state !is ScanUiState.CheckingConnectivity && state !is ScanUiState.NoNetwork) return

        _uiState.update { ScanUiState.CheckingConnectivity }

        viewModelScope.launch(dispatcher) {
            runCatching {
                networkConnectivityChecker.isConnected()
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't check network connectivity")
                _uiState.update { ScanUiState.NoNetwork }
            }.onSuccess { isConnected ->
                _uiState.update {
                    if (isConnected) ScanUiState.Scanning else ScanUiState.NoNetwork
                }
            }
        }
    }

    fun onBarcodeDetected(ean: String) {
        if (_uiState.value !is ScanUiState.Scanning) return

        _uiState.update { ScanUiState.LookingUp(ean) }

        viewModelScope.launch(dispatcher) {
            runCatching {
                lookupProductUseCase(ean)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't look up product $ean")
                _uiState.update { ScanUiState.ProductNotFound(ean) }
            }.onSuccess { product ->
                if (product == null) {
                    _uiState.update { ScanUiState.ProductNotFound(ean) }
                } else {
                    scannedProduct = product
                    _uiState.update {
                        ScanUiState.ProductFound(
                            ScannedProductUi(
                                name = product.name,
                                quantity = product.quantity,
                                category = product.brand.orEmpty()
                            )
                        )
                    }
                }
            }
        }
    }

    fun onPermissionDenied() {
        _uiState.update { ScanUiState.PermissionDenied }
    }

    fun resetScanning() {
        scannedProduct = null
        _uiState.update { ScanUiState.Scanning }
    }

    fun addToPantry() {
        val product = scannedProduct ?: return
        val current = _uiState.value as? ScanUiState.ProductFound ?: return
        if (current.isSaving) return

        _uiState.update { current.copy(isSaving = true, saveError = null) }

        viewModelScope.launch(dispatcher) {
            runCatching {
                addScannedProductUseCase(product)
            }.onFailure { error ->
                AppLogging.e(error, "Couldn't save scanned product ${product.ean}")
                _uiState.update {
                    current.copy(isSaving = false, saveError = "Couldn't save this product. Please try again.")
                }
            }.onSuccess { storageId ->
                _uiState.update { ScanUiState.Saved(storageId) }
            }
        }
    }
}
