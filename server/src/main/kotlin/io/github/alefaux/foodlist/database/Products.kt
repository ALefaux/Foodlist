package io.github.alefaux.foodlist.database

import org.jetbrains.exposed.sql.Table

object Products : Table("products") {
    val id = long("id").autoIncrement()
    val userId = long("user_id")
    val name = varchar("name", 200)
    val expirationDate = long("expiration_date").nullable()
    val ean = varchar("ean", 64)
    val discardedDate = long("discarded_date").nullable()
    val storageUnitId = long("storage_unit_id").nullable()
    val quantity = varchar("quantity", 100)
    val category = varchar("category", 100)

    override val primaryKey = PrimaryKey(id)
}
