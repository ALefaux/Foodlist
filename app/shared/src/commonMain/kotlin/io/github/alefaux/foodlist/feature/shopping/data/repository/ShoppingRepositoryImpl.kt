package io.github.alefaux.foodlist.feature.shopping.data.repository

import io.github.alefaux.foodlist.database.dao.ShoppingItemDao
import io.github.alefaux.foodlist.database.entity.ShoppingItemEntity
import io.github.alefaux.foodlist.feature.shopping.domain.NewShoppingItem
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingItem
import kotlin.time.Clock

class ShoppingRepositoryImpl(
    private val shoppingItemDao: ShoppingItemDao
) : ShoppingRepository {

    override suspend fun getItems(): List<ShoppingItem> =
        shoppingItemDao.getAll().map {
            ShoppingItem(
                id = it.id,
                name = it.name,
                quantity = it.quantity,
                purchasedAt = it.purchasedAt
            )
        }

    override suspend fun addItems(items: List<NewShoppingItem>) {
        val now = Clock.System.now()
        shoppingItemDao.insertAll(
            items.map { ShoppingItemEntity(name = it.name, quantity = it.quantity, createdAt = now) }
        )
    }

    override suspend fun setPurchased(itemId: Long, isPurchased: Boolean) {
        shoppingItemDao.setPurchasedAt(itemId, if (isPurchased) Clock.System.now() else null)
    }

    override suspend fun deleteItem(itemId: Long) {
        shoppingItemDao.deleteById(itemId)
    }
}
