package io.github.alefaux.foodlist.database

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class StorageUnitRepository {

    fun replaceAllForUser(userId: Long, units: List<Pair<Long, String>>): Map<Long, Long> = transaction {
        StorageUnits.deleteWhere { StorageUnits.userId eq userId }

        units.associate { (localId, name) ->
            val newId = StorageUnits.insert {
                it[StorageUnits.userId] = userId
                it[StorageUnits.name] = name
            } get StorageUnits.id

            localId to newId
        }
    }
}
