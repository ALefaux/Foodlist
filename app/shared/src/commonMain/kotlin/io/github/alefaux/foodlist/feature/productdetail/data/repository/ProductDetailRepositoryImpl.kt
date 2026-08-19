package io.github.alefaux.foodlist.feature.productdetail.data.repository

import io.github.alefaux.foodlist.core.model.extension.toFreshness
import io.github.alefaux.foodlist.core.model.extension.toLocalDate
import io.github.alefaux.foodlist.database.dao.ProductDao
import io.github.alefaux.foodlist.database.dao.StorageDao
import io.github.alefaux.foodlist.feature.productdetail.domain.ProductDetail
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.daysUntil
import io.github.alefaux.foodlist.core.model.ProductFreshness
import kotlin.time.Clock

class ProductDetailRepositoryImpl(
    private val productDao: ProductDao,
    private val storageDao: StorageDao
) : ProductDetailRepository {

    override suspend fun getProductDetail(productId: Int): ProductDetail? {
        val product = productDao.getById(productId) ?: return null
        val today = Clock.System.now().toLocalDate()
        val expirationDate = product.expirationDate?.toLocalDate()
        val freshness = expirationDate.toFreshness(today)
        val storageName = product.storageId?.let { storageDao.getById(it)?.name }

        return ProductDetail(
            id = product.id,
            name = product.name,
            quantity = product.quantity,
            category = product.category,
            stock = product.stock,
            expirationDate = expirationDate,
            createdAt = product.createdAt?.toLocalDate(),
            storageId = product.storageId,
            storageName = storageName,
            freshness = freshness,
            statusLabel = statusLabel(freshness, today, expirationDate)
        )
    }

    override suspend fun updateStock(productId: Int, stock: Int) {
        val product = productDao.getById(productId) ?: return
        productDao.update(product.copy(stock = stock))
    }

    override suspend fun updateProduct(
        productId: Int,
        name: String,
        quantity: String,
        category: String,
        expirationDate: LocalDate?
    ) {
        val product = productDao.getById(productId) ?: return
        productDao.update(
            product.copy(
                name = name,
                quantity = quantity,
                category = category,
                expirationDate = expirationDate?.atStartOfDayIn(TimeZone.currentSystemDefault())
            )
        )
    }

    override suspend fun moveProduct(productId: Int, storageId: Long) {
        val product = productDao.getById(productId) ?: return
        productDao.update(product.copy(storageId = storageId))
    }

    override suspend fun deleteProduct(productId: Int) {
        productDao.deleteById(productId)
    }

    private fun statusLabel(freshness: ProductFreshness, today: LocalDate, expirationDate: LocalDate?): String =
        when (freshness) {
            ProductFreshness.EXPIRED -> {
                val days = expirationDate?.let { it.daysUntil(today) } ?: 0
                if (days <= 0) "Expired today" else "Expired $days day${if (days == 1) "" else "s"} ago"
            }
            ProductFreshness.EXPIRING_SOON -> {
                val days = expirationDate?.let { today.daysUntil(it) } ?: 0
                if (days <= 0) "Expires today" else "Expires in $days day${if (days == 1) "" else "s"}"
            }
            ProductFreshness.FRESH -> "Fresh"
        }
}
