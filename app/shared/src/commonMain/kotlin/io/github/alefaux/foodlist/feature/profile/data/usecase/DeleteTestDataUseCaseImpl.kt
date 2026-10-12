package io.github.alefaux.foodlist.feature.profile.data.usecase

import io.github.alefaux.foodlist.database.dao.ProductDao
import io.github.alefaux.foodlist.database.dao.RecipeDao
import io.github.alefaux.foodlist.database.dao.ShoppingItemDao
import io.github.alefaux.foodlist.database.dao.StorageDao
import io.github.alefaux.foodlist.feature.profile.domain.DeleteTestDataUseCase

/** Debug only: removes what [CreateTestDataUseCaseImpl] created, leaving real data untouched. */
class DeleteTestDataUseCaseImpl(
    private val storageDao: StorageDao,
    private val productDao: ProductDao,
    private val recipeDao: RecipeDao,
    private val shoppingItemDao: ShoppingItemDao
) : DeleteTestDataUseCase {

    override suspend fun invoke() {
        recipeDao.deleteTestIngredients()
        recipeDao.deleteTestSteps()
        recipeDao.deleteTestRecipes()
        shoppingItemDao.deleteTestData()

        productDao.deleteTestData()

        storageDao.getTestData().forEach { storage ->
            // Real products may have been added to a test storage: keep them, unassigned.
            productDao.clearStorageReference(storage.id)
            shoppingItemDao.clearStorageReference(storage.id)
            storageDao.deleteById(storage.id)
        }
    }
}
