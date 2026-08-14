package io.github.alefaux.foodlist.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

// Single-row table caching the server-issued session; id is always SINGLETON_ID,
// all fields are null when signed out.
@Entity
data class SessionEntity(
    @PrimaryKey val id: Int = SINGLETON_ID,
    val token: String?,
    val userId: Long?,
    val userName: String?,
    val userEmail: String?
) {
    companion object {
        const val SINGLETON_ID = 0
    }
}
