package io.github.alefaux.foodlist.feature.shopping.data.repository

import io.github.alefaux.foodlist.feature.shopping.domain.NewShoppingItem
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingItem
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingItemDetail
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingItemDraft

interface ShoppingRepository {
    suspend fun getItems(): List<ShoppingItem>
    suspend fun getItemDetail(itemId: Long): ShoppingItemDetail?
    suspend fun addItems(items: List<NewShoppingItem>)
    suspend fun updateItem(itemId: Long, draft: ShoppingItemDraft)
    suspend fun setPurchased(itemId: Long, isPurchased: Boolean)
    suspend fun deleteItem(itemId: Long)
}
