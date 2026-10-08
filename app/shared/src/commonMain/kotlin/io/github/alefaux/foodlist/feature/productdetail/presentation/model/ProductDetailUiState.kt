package io.github.alefaux.foodlist.feature.productdetail.presentation.model

import io.github.alefaux.foodlist.core.model.ProductFreshness
import io.github.alefaux.foodlist.feature.storage.domain.StorageUnit
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.LocalDate

data class ProductDetailUiState(
    val id: Int = 0,
    val name: String = "",
    val quantity: String = "",
    val category: String = "",
    val stock: Int = 1,
    val expirationDate: LocalDate? = null,
    val createdAt: LocalDate? = null,
    val storageId: Long? = null,
    val storageName: String? = null,
    val freshness: ProductFreshness = ProductFreshness.FRESH,
    val statusLabel: String = "",
    val availableStorages: ImmutableList<StorageUnit> = persistentListOf(),
    val isLoading: Boolean = true,
    val isEditSheetVisible: Boolean = false,
    val isMoveSheetVisible: Boolean = false,
    val isDeleteDialogVisible: Boolean = false,
    val isDiscardDialogVisible: Boolean = false,
    val isDeleted: Boolean = false
)
