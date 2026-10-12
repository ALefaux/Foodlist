package io.github.alefaux.foodlist.feature.profile.data.usecase

import io.github.alefaux.foodlist.database.dao.ProductDao
import io.github.alefaux.foodlist.database.dao.RecipeDao
import io.github.alefaux.foodlist.database.dao.ShoppingItemDao
import io.github.alefaux.foodlist.database.dao.StorageDao
import io.github.alefaux.foodlist.database.entity.ProductEntity
import io.github.alefaux.foodlist.database.entity.RecipeEntity
import io.github.alefaux.foodlist.database.entity.RecipeIngredientEntity
import io.github.alefaux.foodlist.database.entity.RecipeStepEntity
import io.github.alefaux.foodlist.database.entity.ShoppingItemEntity
import io.github.alefaux.foodlist.database.entity.StorageEntity
import io.github.alefaux.foodlist.feature.profile.domain.CreateTestDataUseCase
import kotlin.time.Clock
import kotlin.time.Duration.Companion.days

/**
 * Debug only: seeds storage units and products covering every freshness state, and recipes
 * built from those products covering every recipe availability, and a shopping list.
 * Everything is flagged with `isTestData` so [DeleteTestDataUseCaseImpl] can remove it.
 */
class CreateTestDataUseCaseImpl(
    private val storageDao: StorageDao,
    private val productDao: ProductDao,
    private val recipeDao: RecipeDao,
    private val shoppingItemDao: ShoppingItemDao
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

        val recipeIds = createTestRecipes()
        createTestShoppingList(fridgeId = fridgeId, lasagnaId = recipeIds.getValue("Classic Lasagna"))
    }

    private suspend fun createTestShoppingList(fridgeId: Long, lasagnaId: Long) {
        val now = Clock.System.now()

        shoppingItemDao.insertAll(
            listOf(
                // Goes to the fridge when ticked as bought
                ShoppingItemEntity(
                    name = "Whole Milk",
                    quantity = "1 L",
                    count = 2,
                    category = "Dairy",
                    storageId = fridgeId,
                    priority = "URGENT",
                    brands = "Lactel",
                    note = "Pick the latest expiration date.",
                    createdAt = now,
                    isTestData = true
                ),
                ShoppingItemEntity(
                    name = "Cherry Tomatoes",
                    quantity = "250 g",
                    category = "Vegetables",
                    priority = "NICE_TO_HAVE",
                    createdAt = now,
                    isTestData = true
                ),
                // Linked to the lasagna, and must not be suggested again
                ShoppingItemEntity(
                    name = "Ground Beef",
                    quantity = "500 g",
                    category = "Meat",
                    storageId = fridgeId,
                    recipeId = lasagnaId,
                    createdAt = now,
                    isTestData = true
                ),
                ShoppingItemEntity(
                    name = "Spinach",
                    quantity = "1 bag",
                    category = "Vegetables",
                    purchasedAt = now - 1.days,
                    createdAt = now - 2.days,
                    isTestData = true
                )
            )
        )
    }

    /** Returns the created recipe ids by name. */
    private suspend fun createTestRecipes(): Map<String, Long> =
        listOf(
            // Every ingredient is fresh: can make
            TestRecipe(
                name = "Spaghetti with Peas",
                description = "A quick weeknight pasta made entirely from pantry staples.",
                prepMinutes = 5,
                cookMinutes = 12,
                servings = 2,
                calories = 520,
                isVegetarian = true,
                tip = "Frozen peas keep their sweetness for months: add them straight from the freezer.",
                ingredients = listOf(
                    "Spaghetti" to "200 g",
                    "Frozen Peas" to "150 g",
                    "Olive Oil" to "2 tbsp"
                ),
                steps = listOf(
                    TestStep("Cook the spaghetti", "Bring a large pot of salted water to a boil and cook the spaghetti until al dente.", 10),
                    TestStep("Add the peas", "Add the frozen peas to the pot for the last 2 minutes of cooking.", 2),
                    TestStep("Finish with olive oil", "Drain, toss with the olive oil and season with salt and pepper. Serve immediately.", null)
                )
            ),
            TestRecipe(
                name = "Chicken Stir Fry",
                description = "Golden chicken and sweet peas tossed in a hot pan in under half an hour.",
                prepMinutes = 10,
                cookMinutes = 12,
                servings = 2,
                calories = 430,
                isVegetarian = false,
                tip = null,
                ingredients = listOf(
                    "Chicken Breast" to "400 g",
                    "Frozen Peas" to "200 g",
                    "Olive Oil" to "1 tbsp"
                ),
                steps = listOf(
                    TestStep("Slice the chicken", "Cut the chicken breast into thin strips and season with salt and pepper.", null),
                    TestStep("Sear the chicken", "Heat the olive oil in a wide pan over high heat and cook the chicken until golden.", 6),
                    TestStep("Add the peas", "Add the frozen peas and stir fry until hot. Serve right away.", 4)
                )
            ),
            // Greek Yogurt expires tomorrow: uses expiring items
            TestRecipe(
                name = "Creamy Chicken",
                description = "Tender chicken in a light yogurt sauce, a good way to use yogurt before it expires.",
                prepMinutes = 10,
                cookMinutes = 20,
                servings = 3,
                calories = 380,
                isVegetarian = false,
                tip = "Stir the yogurt in off the heat so the sauce stays smooth instead of splitting.",
                ingredients = listOf(
                    "Chicken Breast" to "400 g",
                    "Greek Yogurt" to "250 g",
                    "Olive Oil" to "1 tbsp"
                ),
                steps = listOf(
                    TestStep("Brown the chicken", "Cut the chicken into pieces and brown them in the olive oil over medium heat.", 8),
                    TestStep("Cook through", "Lower the heat, cover and cook until the chicken is cooked through.", 10),
                    TestStep("Make the sauce", "Take the pan off the heat and stir in the Greek yogurt. Season and serve.", null)
                )
            ),
            // Lasagna sheets, beef and tomato sauce are not in the pantry: missing ingredients
            TestRecipe(
                name = "Classic Lasagna",
                description = "Layers of pasta, rich meat sauce and melted cheese baked until bubbling.",
                prepMinutes = 25,
                cookMinutes = 45,
                servings = 6,
                calories = 610,
                isVegetarian = false,
                tip = "Leftover lasagna freezes well in portions for up to 3 months.",
                ingredients = listOf(
                    "Lasagna Sheets" to "12 sheets",
                    "Ground Beef" to "500 g",
                    "Tomato Sauce" to "700 g",
                    "Emmental" to "150 g",
                    "Olive Oil" to "1 tbsp"
                ),
                steps = listOf(
                    TestStep("Make the meat sauce", "Brown the ground beef in the olive oil, add the tomato sauce and simmer.", 15),
                    TestStep("Assemble", "Alternate layers of lasagna sheets and meat sauce in a baking dish, finishing with sauce.", null),
                    TestStep("Bake", "Cover with grated Emmental and bake at 180 °C until golden and bubbling.", 30)
                )
            ),
            // Sliced Bread was discarded: missing ingredients
            TestRecipe(
                name = "Cheese Toast",
                description = "Crispy bread topped with melted Emmental, ready in minutes.",
                prepMinutes = 3,
                cookMinutes = 7,
                servings = 1,
                calories = 340,
                isVegetarian = true,
                tip = null,
                ingredients = listOf(
                    "Sliced Bread" to "2 slices",
                    "Emmental" to "60 g"
                ),
                steps = listOf(
                    TestStep("Top the bread", "Cover the bread slices with grated Emmental.", null),
                    TestStep("Grill", "Grill until the cheese is melted and golden.", 5)
                )
            )
        ).associate { recipe ->
            val recipeId = recipeDao.insert(
                RecipeEntity(
                    name = recipe.name,
                    description = recipe.description,
                    prepMinutes = recipe.prepMinutes,
                    cookMinutes = recipe.cookMinutes,
                    servings = recipe.servings,
                    calories = recipe.calories,
                    isVegetarian = recipe.isVegetarian,
                    tip = recipe.tip,
                    isTestData = true
                )
            )
            recipeDao.insertIngredients(
                recipe.ingredients.map { (name, quantity) ->
                    RecipeIngredientEntity(recipeId = recipeId, name = name, quantity = quantity)
                }
            )
            recipeDao.insertSteps(
                recipe.steps.mapIndexed { index, step ->
                    RecipeStepEntity(
                        recipeId = recipeId,
                        position = index + 1,
                        title = step.title,
                        instructions = step.instructions,
                        timerMinutes = step.timerMinutes
                    )
                }
            )
            recipe.name to recipeId
        }

    private data class TestRecipe(
        val name: String,
        val description: String,
        val prepMinutes: Int,
        val cookMinutes: Int,
        val servings: Int,
        val calories: Int,
        val isVegetarian: Boolean,
        val tip: String?,
        val ingredients: List<Pair<String, String>>,
        val steps: List<TestStep>
    )

    private data class TestStep(
        val title: String,
        val instructions: String,
        val timerMinutes: Int?
    )
}
