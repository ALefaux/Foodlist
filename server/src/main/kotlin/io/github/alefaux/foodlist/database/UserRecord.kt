package io.github.alefaux.foodlist.database

data class UserRecord(
    val id: Long,
    val name: String,
    val email: String,
    val passwordHash: String?,
    val createdAt: Long
)
