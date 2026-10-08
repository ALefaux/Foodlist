package io.github.alefaux.foodlist.feature.storage.modelui

data class DiscardedStorageProductUi(
    val id: Int,
    val name: String,
    val quantity: String,
    val category: String,
    val discardedOn: String
)
