package io.github.alefaux.foodlist.feature.scan.domain

data class ScannedProduct(
    val ean: String,
    val name: String,
    val quantity: String,
    val brand: String?,
    val category: String?
)
