package io.github.alefaux.foodlist.feature.productdetail.data.usecase

import io.github.alefaux.foodlist.feature.productdetail.data.repository.ProductDetailRepository
import io.github.alefaux.foodlist.feature.productdetail.domain.UpdateProductUseCase
import kotlinx.datetime.LocalDate

class UpdateProductUseCaseImpl(
    private val repository: ProductDetailRepository
) : UpdateProductUseCase {
    override suspend fun invoke(
        productId: Int,
        name: String,
        quantity: String,
        category: String,
        expirationDate: LocalDate?
    ) = repository.updateProduct(productId, name, quantity, category, expirationDate)
}
