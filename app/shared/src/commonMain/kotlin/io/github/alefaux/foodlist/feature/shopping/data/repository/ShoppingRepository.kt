package io.github.alefaux.foodlist.feature.shopping.data.repository

import io.github.alefaux.foodlist.feature.shopping.domain.NewShoppingItem
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingItem

interface ShoppingRepository {
    suspend fun getItems(): List<ShoppingItem>
    suspend fun addItems(items: List<NewShoppingItem>)
    suspend fun setPurchased(itemId: Long, isPurchased: Boolean)
    suspend fun deleteItem(itemId: Long)
}
