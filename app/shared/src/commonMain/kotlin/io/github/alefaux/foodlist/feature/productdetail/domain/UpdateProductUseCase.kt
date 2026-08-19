package io.github.alefaux.foodlist.feature.productdetail.domain

import kotlinx.datetime.LocalDate

interface UpdateProductUseCase {
    suspend operator fun invoke(
        productId: Int,
        name: String,
        quantity: String,
        category: String,
        expirationDate: LocalDate?
    )
}
