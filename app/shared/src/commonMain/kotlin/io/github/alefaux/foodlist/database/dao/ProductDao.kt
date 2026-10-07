package io.github.alefaux.foodlist.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.github.alefaux.foodlist.database.entity.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM ProductEntity")
    suspend fun getAll(): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE storageId = :storageId AND discardedDate IS NULL")
    suspend fun getByStorageId(storageId: Long): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE storageId = :storageId AND discardedDate IS NOT NULL ORDER BY discardedDate DESC")
    suspend fun getDiscardedByStorageId(storageId: Long): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE id = :id")
    suspend fun getById(id: Int): ProductEntity?

    @Insert
    suspend fun insert(product: ProductEntity)

    @Update
    suspend fun update(product: ProductEntity)

    @Query("UPDATE ProductEntity SET discardedDate = NULL WHERE id = :id")
    suspend fun restoreDiscarded(id: Int)

    @Query("UPDATE ProductEntity SET storageId = NULL WHERE storageId = :storageId")
    suspend fun clearStorageReference(storageId: Long)

    @Query("DELETE FROM ProductEntity WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM ProductEntity WHERE isTestData = 1")
    suspend fun deleteTestData()

    @Query("DELETE FROM ProductEntity")
    suspend fun deleteAll()
}