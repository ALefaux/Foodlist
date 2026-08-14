package io.github.alefaux.foodlist.database

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

data class ProductSyncRecord(
    val name: String,
    val expirationDate: Long?,
    val ean: String,
    val discardedDate: Long?,
    val storageUnitId: Long?,
    val quantity: String,
    val category: String
)

class ProductSyncRepository {

    fun replaceAllForUser(userId: Long, products: List<ProductSyncRecord>) = transaction {
        Products.deleteWhere { Products.userId eq userId }

        products.forEach { product ->
            Products.insert {
                it[Products.userId] = userId
                it[name] = product.name
                it[expirationDate] = product.expirationDate
                it[ean] = product.ean
                it[discardedDate] = product.discardedDate
                it[storageUnitId] = product.storageUnitId
                it[quantity] = product.quantity
                it[category] = product.category
            }
        }
    }
}
