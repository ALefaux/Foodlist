package io.github.alefaux.foodlist.feature.sync.data.remote

import kotlinx.serialization.Serializable

@Serializable
data class SyncRequestDto(
    val storageUnits: List<SyncStorageUnitDto>,
    val products: List<SyncProductDto>
)

@Serializable
data class SyncStorageUnitDto(
    val localId: Long,
    val name: String
)

@Serializable
data class SyncProductDto(
    val localId: Int,
    val name: String,
    val expirationDate: Long?,
    val ean: String,
    val discardedDate: Long?,
    val localStorageId: Long?,
    val quantity: String,
    val category: String
)

@Serializable
data class SyncResponseDto(
    val syncedStorageUnits: Int,
    val syncedProducts: Int
)
