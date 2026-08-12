package io.github.alefaux.foodlist.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.alefaux.foodlist.database.entity.StorageEntity

@Dao
interface StorageDao {
    @Query("SELECT * FROM StorageEntity")
    suspend fun getAll(): List<StorageEntity>

    @Query("SELECT * FROM StorageEntity WHERE id = :id")
    suspend fun getById(id: Long): StorageEntity?

    @Insert
    suspend fun insert(storage: StorageEntity)

    @Query("DELETE FROM StorageEntity WHERE id = :id")
    suspend fun deleteById(id: Long)
}
