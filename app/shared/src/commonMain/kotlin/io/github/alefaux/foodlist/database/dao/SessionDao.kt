package io.github.alefaux.foodlist.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import io.github.alefaux.foodlist.database.entity.SessionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {
    @Query("SELECT * FROM SessionEntity WHERE id = ${SessionEntity.SINGLETON_ID}")
    fun observe(): Flow<SessionEntity?>

    @Query("SELECT * FROM SessionEntity WHERE id = ${SessionEntity.SINGLETON_ID}")
    suspend fun get(): SessionEntity?

    @Upsert
    suspend fun upsert(session: SessionEntity)
}
