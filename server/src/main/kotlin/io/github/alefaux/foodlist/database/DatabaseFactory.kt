package io.github.alefaux.foodlist.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init(
        jdbcUrl: String = System.getenv("DATABASE_URL") ?: "jdbc:h2:./data/foodlist;AUTO_SERVER=TRUE",
        user: String = System.getenv("DATABASE_USER") ?: "",
        password: String = System.getenv("DATABASE_PASSWORD") ?: ""
    ) {
        val driver = if (jdbcUrl.startsWith("jdbc:postgresql")) "org.postgresql.Driver" else "org.h2.Driver"

        Database.connect(
            url = jdbcUrl,
            driver = driver,
            user = user,
            password = password
        )

        transaction {
            SchemaUtils.create(Users, StorageUnits, Products)
        }
    }
}
