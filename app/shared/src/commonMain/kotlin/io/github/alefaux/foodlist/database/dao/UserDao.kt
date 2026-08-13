package io.github.alefaux.foodlist.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.github.alefaux.foodlist.database.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEntity WHERE email = :email")
    suspend fun getByEmail(email: String): UserEntity?

    @Query("SELECT * FROM UserEntity WHERE id = :id")
    suspend fun getById(id: Long): UserEntity?

    @Insert
    suspend fun insert(user: UserEntity): Long
}
