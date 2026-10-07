package io.github.alefaux.foodlist.feature.storage.domain

import kotlinx.datetime.LocalDate

data class StorageProduct(
    val id: Int,
    val name: String,
    val quantity: String,
    val category: String,
    val expirationDate: LocalDate?,
    val discardedDate: LocalDate? = null
)
