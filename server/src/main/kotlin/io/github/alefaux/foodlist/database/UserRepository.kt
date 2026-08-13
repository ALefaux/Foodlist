package io.github.alefaux.foodlist.database

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository {

    fun findByEmail(email: String): UserRecord? = transaction {
        Users.selectAll()
            .where { Users.email eq email }
            .map { it.toUserRecord() }
            .singleOrNull()
    }

    fun findById(id: Long): UserRecord? = transaction {
        Users.selectAll()
            .where { Users.id eq id }
            .map { it.toUserRecord() }
            .singleOrNull()
    }

    fun insert(name: String, email: String, passwordHash: String, createdAt: Long): UserRecord = transaction {
        val id = Users.insert {
            it[Users.name] = name
            it[Users.email] = email
            it[Users.passwordHash] = passwordHash
            it[Users.createdAt] = createdAt
        } get Users.id

        UserRecord(id = id, name = name, email = email, passwordHash = passwordHash, createdAt = createdAt)
    }

    private fun ResultRow.toUserRecord() = UserRecord(
        id = this[Users.id],
        name = this[Users.name],
        email = this[Users.email],
        passwordHash = this[Users.passwordHash],
        createdAt = this[Users.createdAt]
    )
}
