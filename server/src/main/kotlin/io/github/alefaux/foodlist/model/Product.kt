package io.github.alefaux.foodlist.model


data class Product(
    val id: Long,
    val userId: Long,
    val name: String,
    val expirationDate: Long?,
    val ean: String,
    val discardedDate: Long?,
    val storageUnitId: Long?,
    val quantity: String,
    val category: String
)
