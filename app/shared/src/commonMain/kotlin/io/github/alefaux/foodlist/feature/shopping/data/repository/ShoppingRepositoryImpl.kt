package io.github.alefaux.foodlist.feature.shopping.data.repository

import io.github.alefaux.foodlist.database.dao.ProductDao
import io.github.alefaux.foodlist.database.dao.RecipeDao
import io.github.alefaux.foodlist.database.dao.ShoppingItemDao
import io.github.alefaux.foodlist.database.entity.ProductEntity
import io.github.alefaux.foodlist.database.entity.ShoppingItemEntity
import io.github.alefaux.foodlist.feature.shopping.domain.NewShoppingItem
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingItem
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingItemDetail
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingItemDraft
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingPriority
import kotlin.time.Clock

class ShoppingRepositoryImpl(
    private val shoppingItemDao: ShoppingItemDao,
    private val productDao: ProductDao,
    private val recipeDao: RecipeDao
) : ShoppingRepository {

    override suspend fun getItems(): List<ShoppingItem> =
        shoppingItemDao.getAll().map {
            ShoppingItem(
                id = it.id,
                name = it.name,
                quantity = it.quantity,
                count = it.count,
                priority = it.priority.toPriority(),
                purchasedAt = it.purchasedAt
            )
        }

    override suspend fun getItemDetail(itemId: Long): ShoppingItemDetail? {
        val item = shoppingItemDao.getById(itemId) ?: return null

        return ShoppingItemDetail(
            id = item.id,
            isPurchased = item.purchasedAt != null,
            recipeId = item.recipeId,
            recipeName = item.recipeId?.let { recipeDao.getById(it)?.name },
            draft = ShoppingItemDraft(
                name = item.name,
                quantity = item.quantity,
                count = item.count,
                category = item.category,
                storageId = item.storageId,
                priority = item.priority.toPriority(),
                brands = item.brands,
                note = item.note
            )
        )
    }

    override suspend fun addItems(items: List<NewShoppingItem>) {
        val now = Clock.System.now()
        shoppingItemDao.insertAll(
            items.map {
                ShoppingItemEntity(name = it.name, quantity = it.quantity, recipeId = it.recipeId, createdAt = now)
            }
        )
    }

    override suspend fun updateItem(itemId: Long, draft: ShoppingItemDraft) {
        val item = shoppingItemDao.getById(itemId) ?: return
        shoppingItemDao.update(
            item.copy(
                name = draft.name,
                quantity = draft.quantity,
                count = draft.count,
                category = draft.category,
                storageId = draft.storageId,
                priority = draft.priority.name,
                brands = draft.brands,
                note = draft.note
            )
        )
    }

    /**
     * Purchasing an item with a storage adds it to that storage as a product.
     * Putting it back on the list removes that product, unless it was discarded meanwhile.
     */
    override suspend fun setPurchased(itemId: Long, isPurchased: Boolean) {
        val item = shoppingItemDao.getById(itemId) ?: return
        val now = Clock.System.now()

        if (isPurchased) {
            val productId = item.productId ?: item.storageId?.let { storageId ->
                productDao.insert(
                    ProductEntity(
                        name = item.name,
                        ean = "",
                        expirationDate = null,
                        discardedDate = null,
                        storageId = storageId,
                        quantity = item.quantity,
                        category = item.category,
                        stock = item.count,
                        createdAt = now
                    )
                ).toInt()
            }
            shoppingItemDao.update(item.copy(purchasedAt = now, productId = productId))
        } else {
            item.productId?.let { productId ->
                val product = productDao.getById(productId)
                if (product != null && product.discardedDate == null) productDao.deleteById(productId)
            }
            shoppingItemDao.update(item.copy(purchasedAt = null, productId = null))
        }
    }

    override suspend fun deleteItem(itemId: Long) {
        shoppingItemDao.deleteById(itemId)
    }

    private fun String.toPriority(): ShoppingPriority =
        ShoppingPriority.entries.firstOrNull { it.name == this } ?: ShoppingPriority.STANDARD
}
