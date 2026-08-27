package io.github.alefaux.foodlist.feature.scan.presentation.model

import io.github.alefaux.foodlist.feature.scan.modelui.ScannedProductUi

sealed interface ScanUiState {
    data object CheckingConnectivity : ScanUiState
    data object NoNetwork : ScanUiState
    data object Scanning : ScanUiState
    data class LookingUp(val ean: String) : ScanUiState
    data class ProductFound(
        val product: ScannedProductUi,
        val isSaving: Boolean = false,
        val saveError: String? = null
    ) : ScanUiState
    data class ProductNotFound(val ean: String) : ScanUiState
    data object PermissionDenied : ScanUiState
    data class Saved(val storageId: Long) : ScanUiState
}
