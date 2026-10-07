package io.github.alefaux.foodlist.feature.profile.data.usecase

import io.github.alefaux.foodlist.database.dao.ProductDao
import io.github.alefaux.foodlist.database.dao.StorageDao
import io.github.alefaux.foodlist.database.entity.ProductEntity
import io.github.alefaux.foodlist.database.entity.StorageEntity
import io.github.alefaux.foodlist.feature.profile.domain.CreateTestDataUseCase
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days

/**
 * Debug only: seeds storage units and products covering every freshness state.
 * Everything is flagged with `isTestData` so [DeleteTestDataUseCaseImpl] can remove it.
 */
class CreateTestDataUseCaseImpl(
    private val storageDao: StorageDao,
    private val productDao: ProductDao
) : CreateTestDataUseCase {

    override suspend fun invoke() {
        val now = Clock.System.now()
        val fridgeId = storageDao.insert(StorageEntity(name = "Fridge", isTestData = true))
        val freezerId = storageDao.insert(StorageEntity(name = "Freezer", isTestData = true))
        val pantryId = storageDao.insert(StorageEntity(name = "Pantry", isTestData = true))

        listOf(
            ProductEntity(
                name = "Whole Milk",
                ean = "3428273980046",
                expirationDate = now - 2.days,
                discardedDate = null,
                storageId = fridgeId,
                quantity = "1 L",
                category = "Dairy"
            ),
            ProductEntity(
                name = "Greek Yogurt",
                ean = "3033490004743",
                expirationDate = now + 1.days,
                discardedDate = null,
                storageId = fridgeId,
                quantity = "4 x 125 g",
                category = "Dairy",
                stock = 2
            ),
            ProductEntity(
                name = "Emmental",
                ean = "3228021170039",
                expirationDate = now + 3.days,
                discardedDate = null,
                storageId = fridgeId,
                quantity = "200 g",
                category = "Dairy"
            ),
            ProductEntity(
                name = "Chicken Breast",
                ean = "3266980123456",
                expirationDate = now + 6.days,
                discardedDate = null,
                storageId = fridgeId,
                quantity = "400 g",
                category = "Meat"
            ),
            ProductEntity(
                name = "Frozen Peas",
                ean = "3083681093223",
                expirationDate = now + 180.days,
                discardedDate = null,
                storageId = freezerId,
                quantity = "1 kg",
                category = "Vegetables"
            ),
            ProductEntity(
                name = "Vanilla Ice Cream",
                ean = "8711327399373",
                expirationDate = now - 10.days,
                discardedDate = null,
                storageId = freezerId,
                quantity = "900 ml",
                category = "Desserts"
            ),
            ProductEntity(
                name = "Spaghetti",
                ean = "8076800195057",
                expirationDate = now + 365.days,
                discardedDate = null,
                storageId = pantryId,
                quantity = "500 g",
                category = "Pasta",
                stock = 3
            ),
            ProductEntity(
                name = "Olive Oil",
                ean = "3270190207603",
                expirationDate = null,
                discardedDate = null,
                storageId = pantryId,
                quantity = "75 cl",
                category = "Oils"
            ),
            ProductEntity(
                name = "Sliced Bread",
                ean = "3029330003533",
                expirationDate = now - 5.days,
                discardedDate = now - 4.days,
                storageId = pantryId,
                quantity = "500 g",
                category = "Bakery"
            )
        ).forEach { product ->
            productDao.insert(product.copy(createdAt = now, isTestData = true))
        }
    }
}
