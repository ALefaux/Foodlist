package io.github.alefaux.foodlist.database

import org.jetbrains.exposed.sql.Table

object Users : Table("users") {
    val id = long("id").autoIncrement()
    val name = varchar("name", 100)
    val email = varchar("email", 255).uniqueIndex()
    val passwordHash = varchar("password_hash", 255).nullable()
    val createdAt = long("created_at")

    override val primaryKey = PrimaryKey(id)
}
