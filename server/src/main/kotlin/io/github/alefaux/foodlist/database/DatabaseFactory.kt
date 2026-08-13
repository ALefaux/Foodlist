package io.github.alefaux.foodlist.database

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init(
        jdbcUrl: String = System.getenv("DATABASE_URL") ?: "jdbc:h2:./data/foodlist;AUTO_SERVER=TRUE"
    ) {
        Database.connect(
            url = jdbcUrl,
            driver = "org.h2.Driver"
        )

        transaction {
            SchemaUtils.create(Users)
        }
    }
}
