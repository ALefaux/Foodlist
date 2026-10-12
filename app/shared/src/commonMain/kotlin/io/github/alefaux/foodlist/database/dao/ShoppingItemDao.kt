package io.github.alefaux.foodlist.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.github.alefaux.foodlist.database.entity.ShoppingItemEntity

@Dao
interface ShoppingItemDao {
    @Query("SELECT * FROM ShoppingItemEntity ORDER BY createdAt, id")
    suspend fun getAll(): List<ShoppingItemEntity>

    @Query("SELECT * FROM ShoppingItemEntity WHERE id = :id")
    suspend fun getById(id: Long): ShoppingItemEntity?

    @Insert
    suspend fun insertAll(items: List<ShoppingItemEntity>)

    @Update
    suspend fun update(item: ShoppingItemEntity)

    @Query("UPDATE ShoppingItemEntity SET storageId = NULL WHERE storageId = :storageId")
    suspend fun clearStorageReference(storageId: Long)

    @Query("DELETE FROM ShoppingItemEntity WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM ShoppingItemEntity WHERE isTestData = 1")
    suspend fun deleteTestData()
}
