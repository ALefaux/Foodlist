package io.github.alefaux.foodlist.feature.storage.presentation.model

import io.github.alefaux.foodlist.feature.storage.modelui.StorageUnitUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class StorageUiState(
    val storageUnits: ImmutableList<StorageUnitUi> = persistentListOf(),
    val isAddDialogVisible: Boolean = false
)
