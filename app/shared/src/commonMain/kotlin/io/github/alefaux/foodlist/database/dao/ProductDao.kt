package io.github.alefaux.foodlist.database.dao

import androidx.room.Dao
import androidx.room.Query
import io.github.alefaux.foodlist.database.entity.ProductEntity

@Dao
interface ProductDao {
    @Query("SELECT * FROM ProductEntity")
    fun getAll(): List<ProductEntity>
}