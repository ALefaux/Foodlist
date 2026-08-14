package io.github.alefaux.foodlist.database

import org.jetbrains.exposed.sql.Table

object StorageUnits : Table("storage_units") {
    val id = long("id").autoIncrement()
    val userId = long("user_id")
    val name = varchar("name", 200)

    override val primaryKey = PrimaryKey(id)
}
