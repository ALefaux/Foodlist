package io.github.alefaux.foodlist.feature.dashboard.data.repository

import io.github.alefaux.foodlist.core.model.Product
import io.github.alefaux.foodlist.core.model.extension.toLocalDate
import io.github.alefaux.foodlist.database.dao.ProductDao
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DashboardRepositoryImpl(
    private val productDao: ProductDao
): DashboardRepository {
    override fun getProducts(): List<Product> =
        productDao.getAll().map {
            Product(
                id = it.id,
                expirationDate = it.expirationDate?.toLocalDate(),
                name = it.name
            )
        }
}