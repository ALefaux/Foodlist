package io.github.alefaux.foodlist.feature.productdetail.data.repository

import io.github.alefaux.foodlist.feature.productdetail.domain.ProductDetail
import kotlinx.datetime.LocalDate

interface ProductDetailRepository {
    suspend fun getProductDetail(productId: Int): ProductDetail?
    suspend fun updateStock(productId: Int, stock: Int)
    suspend fun updateProduct(
        productId: Int,
        name: String,
        quantity: String,
        category: String,
        expirationDate: LocalDate?
    )
    suspend fun moveProduct(productId: Int, storageId: Long)
    suspend fun deleteProduct(productId: Int)
    suspend fun discardProduct(productId: Int)
}
