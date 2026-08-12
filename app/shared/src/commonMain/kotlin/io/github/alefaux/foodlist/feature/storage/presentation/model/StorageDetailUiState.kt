package io.github.alefaux.foodlist.feature.storage.presentation.model

import io.github.alefaux.foodlist.feature.storage.modelui.StorageProductUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class StorageDetailUiState(
    val storageName: String = "",
    val products: ImmutableList<StorageProductUi> = persistentListOf(),
    val categories: ImmutableList<String> = persistentListOf("All"),
    val selectedCategory: String = "All",
    val totalCount: Int = 0,
    val expiringCount: Int = 0,
    val isLoading: Boolean = true,
    val isDeleteDialogVisible: Boolean = false,
    val isDeleted: Boolean = false
)
