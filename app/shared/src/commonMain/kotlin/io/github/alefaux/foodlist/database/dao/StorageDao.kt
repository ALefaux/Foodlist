package io.github.alefaux.foodlist.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.alefaux.foodlist.database.entity.StorageEntity

@Dao
interface StorageDao {
    @Query("SELECT * FROM StorageEntity")
    suspend fun getAll(): List<StorageEntity>

    @Insert
    suspend fun insert(storage: StorageEntity)
}
