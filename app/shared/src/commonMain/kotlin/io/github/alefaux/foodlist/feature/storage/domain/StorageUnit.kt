package io.github.alefaux.foodlist.feature.storage.domain

data class StorageUnit(
    val id: Long,
    val name: String,
    val productCount: Int,
    val expiringCount: Int
)
