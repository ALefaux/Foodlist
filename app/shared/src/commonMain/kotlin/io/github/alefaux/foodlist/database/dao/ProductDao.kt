package io.github.alefaux.foodlist.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.alefaux.foodlist.database.entity.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM ProductEntity")
    suspend fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE storageId = :storageId")
    suspend fun getByStorageId(storageId: Long): List<ProductEntity>

    @Insert
    suspend fun insert(product: ProductEntity)

    @Query("UPDATE ProductEntity SET storageId = NULL WHERE storageId = :storageId")
    suspend fun clearStorageReference(storageId: Long)

    @Query("DELETE FROM ProductEntity")
    suspend fun deleteAll()
}