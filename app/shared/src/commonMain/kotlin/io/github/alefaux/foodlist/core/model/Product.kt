package io.github.alefaux.foodlist.core.model

import kotlinx.datetime.LocalDate

data class Product(
    val id: Int,
    val expirationDate: LocalDate?,
    val name: String,
    val discardedDate: LocalDate? = null
)
