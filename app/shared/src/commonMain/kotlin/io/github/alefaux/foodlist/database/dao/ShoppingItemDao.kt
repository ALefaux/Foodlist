package io.github.alefaux.foodlist.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.alefaux.foodlist.database.entity.ShoppingItemEntity
import kotlin.time.Instant

@Dao
interface ShoppingItemDao {
    @Query("SELECT * FROM ShoppingItemEntity ORDER BY createdAt, id")
    suspend fun getAll(): List<ShoppingItemEntity>

    @Insert
    suspend fun insertAll(items: List<ShoppingItemEntity>)

    @Query("UPDATE ShoppingItemEntity SET purchasedAt = :purchasedAt WHERE id = :id")
    suspend fun setPurchasedAt(id: Long, purchasedAt: Instant?)

    @Query("DELETE FROM ShoppingItemEntity WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM ShoppingItemEntity WHERE isTestData = 1")
    suspend fun deleteTestData()
}
