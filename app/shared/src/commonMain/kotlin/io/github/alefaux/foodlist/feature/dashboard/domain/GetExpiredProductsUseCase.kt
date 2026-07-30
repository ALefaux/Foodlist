package io.github.alefaux.foodlist.feature.dashboard.domain

import io.github.alefaux.foodlist.core.model.Product

interface GetExpiredProductsUseCase {
    suspend operator fun invoke(): List<Product>
}