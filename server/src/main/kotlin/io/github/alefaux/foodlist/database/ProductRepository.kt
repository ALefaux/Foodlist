package io.github.alefaux.foodlist.database

import io.github.alefaux.foodlist.model.Product
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class ProductRepository {
    fun search(query: String): List<Product> = transaction {
        Products.selectAll()
            .where { Products.name.like(query) }
            .map { it.toProduct() }
    }

    private fun ResultRow.toProduct() = Product(
        id = this[Products.id],
        userId = this[Products.userId],
        name = this[Products.name],
        expirationDate = this[Products.expirationDate],
        ean = this[Products.ean],
        discardedDate = this[Products.discardedDate],
        storageUnitId = this[Products.storageUnitId],
        quantity = this[Products.quantity],
        category = this[Products.category]
    )
}