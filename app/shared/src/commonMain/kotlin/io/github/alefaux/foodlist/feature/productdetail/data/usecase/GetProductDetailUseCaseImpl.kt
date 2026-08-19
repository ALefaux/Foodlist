package io.github.alefaux.foodlist.feature.productdetail.data.usecase

import io.github.alefaux.foodlist.feature.productdetail.data.repository.ProductDetailRepository
import io.github.alefaux.foodlist.feature.productdetail.domain.GetProductDetailUseCase
import io.github.alefaux.foodlist.feature.productdetail.domain.ProductDetail

class GetProductDetailUseCaseImpl(
    private val repository: ProductDetailRepository
) : GetProductDetailUseCase {
    override suspend fun invoke(productId: Int): ProductDetail? = repository.getProductDetail(productId)
}
