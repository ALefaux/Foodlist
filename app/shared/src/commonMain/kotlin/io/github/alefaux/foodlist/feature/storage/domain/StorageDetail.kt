package io.github.alefaux.foodlist.feature.storage.domain

data class StorageDetail(
    val id: Long,
    val name: String,
    val products: List<StorageProduct>,
    val discardedProducts: List<StorageProduct>
)
