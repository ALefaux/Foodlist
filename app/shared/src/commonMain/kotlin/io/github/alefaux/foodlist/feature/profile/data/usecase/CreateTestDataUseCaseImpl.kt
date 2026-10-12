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
        val fridgeId = storageDao.insert(StorageEntity(name = "Réfrigérateur", isTestData = true))
        val freezerId = storageDao.insert(StorageEntity(name = "Congélateur", isTestData = true))
        val pantryId = storageDao.insert(StorageEntity(name = "Placard", isTestData = true))

        listOf(
            ProductEntity(
                name = "Lait entier",
                ean = "3428273980046",
                expirationDate = now - 2.days,
                discardedDate = null,
                storageId = fridgeId,
                quantity = "1 L",
                category = "Produits laitiers"
            ),
            ProductEntity(
                name = "Yaourt grec",
                ean = "3033490004743",
                expirationDate = now + 1.days,
                discardedDate = null,
                storageId = fridgeId,
                quantity = "4 x 125 g",
                category = "Produits laitiers",
                stock = 2
            ),
            ProductEntity(
                name = "Emmental",
                ean = "3228021170039",
                expirationDate = now + 3.days,
                discardedDate = null,
                storageId = fridgeId,
                quantity = "200 g",
                category = "Produits laitiers"
            ),
            ProductEntity(
                name = "Blanc de poulet",
                ean = "3266980123456",
                expirationDate = now + 6.days,
                discardedDate = null,
                storageId = fridgeId,
                quantity = "400 g",
                category = "Viande"
            ),
            ProductEntity(
                name = "Petits pois surgelés",
                ean = "3083681093223",
                expirationDate = now + 180.days,
                discardedDate = null,
                storageId = freezerId,
                quantity = "1 kg",
                category = "Légumes"
            ),
            ProductEntity(
                name = "Glace à la vanille",
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
                category = "Pâtes",
                stock = 3
            ),
            ProductEntity(
                name = "Huile d'olive",
                ean = "3270190207603",
                expirationDate = null,
                discardedDate = null,
                storageId = pantryId,
                quantity = "75 cl",
                category = "Huiles"
            ),
            ProductEntity(
                name = "Pain de mie",
                ean = "3029330003533",
                expirationDate = now - 5.days,
                discardedDate = now - 4.days,
                storageId = pantryId,
                quantity = "500 g",
                category = "Boulangerie"
            )
        ).forEach { product ->
            productDao.insert(product.copy(createdAt = now, isTestData = true))
        }

        val recipeIds = createTestRecipes()
        createTestShoppingList(fridgeId = fridgeId, lasagnaId = recipeIds.getValue("Lasagnes à la bolognaise"))
    }

    private suspend fun createTestShoppingList(fridgeId: Long, lasagnaId: Long) {
        val now = Clock.System.now()

        shoppingItemDao.insertAll(
            listOf(
                // Goes to the fridge when ticked as bought
                ShoppingItemEntity(
                    name = "Lait entier",
                    quantity = "1 L",
                    count = 2,
                    category = "Produits laitiers",
                    storageId = fridgeId,
                    priority = "URGENT",
                    brands = "Lactel",
                    note = "Prendre la date de péremption la plus lointaine.",
                    createdAt = now,
                    isTestData = true
                ),
                ShoppingItemEntity(
                    name = "Tomates cerises",
                    quantity = "250 g",
                    category = "Légumes",
                    priority = "NICE_TO_HAVE",
                    createdAt = now,
                    isTestData = true
                ),
                // Linked to the lasagna, and must not be suggested again
                ShoppingItemEntity(
                    name = "Bœuf haché",
                    quantity = "500 g",
                    category = "Viande",
                    storageId = fridgeId,
                    recipeId = lasagnaId,
                    createdAt = now,
                    isTestData = true
                ),
                ShoppingItemEntity(
                    name = "Épinards",
                    quantity = "1 sachet",
                    category = "Légumes",
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
                name = "Spaghetti aux petits pois",
                description = "Des pâtes express du soir, faites uniquement avec les basiques du placard.",
                prepMinutes = 5,
                cookMinutes = 12,
                servings = 2,
                calories = 520,
                isVegetarian = true,
                tip = "Les petits pois surgelés gardent leur douceur des mois : ajoutez-les directement sortis du congélateur.",
                ingredients = listOf(
                    "Spaghetti" to "200 g",
                    "Petits pois surgelés" to "150 g",
                    "Huile d'olive" to "2 c. à s."
                ),
                steps = listOf(
                    TestStep("Cuire les spaghetti", "Portez une grande casserole d'eau salée à ébullition et faites cuire les spaghetti al dente.", 10),
                    TestStep("Ajouter les petits pois", "Ajoutez les petits pois surgelés dans la casserole pendant les 2 dernières minutes de cuisson.", 2),
                    TestStep("Finir à l'huile d'olive", "Égouttez, mélangez avec l'huile d'olive, salez et poivrez. Servez aussitôt.", null)
                )
            ),
            TestRecipe(
                name = "Poulet sauté aux petits pois",
                description = "Du poulet doré et des petits pois sautés à la poêle en moins d'une demi-heure.",
                prepMinutes = 10,
                cookMinutes = 12,
                servings = 2,
                calories = 430,
                isVegetarian = false,
                tip = null,
                ingredients = listOf(
                    "Blanc de poulet" to "400 g",
                    "Petits pois surgelés" to "200 g",
                    "Huile d'olive" to "1 c. à s."
                ),
                steps = listOf(
                    TestStep("Émincer le poulet", "Coupez le blanc de poulet en fines lanières, salez et poivrez.", null),
                    TestStep("Saisir le poulet", "Faites chauffer l'huile d'olive dans une grande poêle à feu vif et faites dorer le poulet.", 6),
                    TestStep("Ajouter les petits pois", "Ajoutez les petits pois surgelés et faites sauter jusqu'à ce qu'ils soient chauds. Servez tout de suite.", 4)
                )
            ),
            // Yaourt grec expires tomorrow: uses expiring items
            TestRecipe(
                name = "Poulet crémeux",
                description = "Du poulet tendre dans une sauce légère au yaourt, idéal pour finir un yaourt avant sa date.",
                prepMinutes = 10,
                cookMinutes = 20,
                servings = 3,
                calories = 380,
                isVegetarian = false,
                tip = "Incorporez le yaourt hors du feu pour que la sauce reste lisse et ne tranche pas.",
                ingredients = listOf(
                    "Blanc de poulet" to "400 g",
                    "Yaourt grec" to "250 g",
                    "Huile d'olive" to "1 c. à s."
                ),
                steps = listOf(
                    TestStep("Faire dorer le poulet", "Coupez le poulet en morceaux et faites-les dorer dans l'huile d'olive à feu moyen.", 8),
                    TestStep("Terminer la cuisson", "Baissez le feu, couvrez et laissez cuire jusqu'à ce que le poulet soit cuit à cœur.", 10),
                    TestStep("Préparer la sauce", "Retirez la poêle du feu et incorporez le yaourt grec. Assaisonnez et servez.", null)
                )
            ),
            // Feuilles de lasagne, bœuf haché and sauce tomate are not in the pantry: missing ingredients
            TestRecipe(
                name = "Lasagnes à la bolognaise",
                description = "Des couches de pâtes, une sauce à la viande généreuse et du fromage fondu, gratinées au four.",
                prepMinutes = 25,
                cookMinutes = 45,
                servings = 6,
                calories = 610,
                isVegetarian = false,
                tip = "Les restes de lasagnes se congèlent très bien en portions jusqu'à 3 mois.",
                ingredients = listOf(
                    "Feuilles de lasagne" to "12 feuilles",
                    "Bœuf haché" to "500 g",
                    "Sauce tomate" to "700 g",
                    "Emmental" to "150 g",
                    "Huile d'olive" to "1 c. à s."
                ),
                steps = listOf(
                    TestStep("Préparer la sauce bolognaise", "Faites revenir le bœuf haché dans l'huile d'olive, ajoutez la sauce tomate et laissez mijoter.", 15),
                    TestStep("Monter les lasagnes", "Alternez feuilles de lasagne et sauce dans un plat à gratin, en terminant par de la sauce.", null),
                    TestStep("Enfourner", "Parsemez d'emmental râpé et enfournez à 180 °C jusqu'à ce que ce soit doré et gratiné.", 30)
                )
            ),
            // Pain de mie was discarded: missing ingredients
            TestRecipe(
                name = "Tartines au fromage",
                description = "Du pain croustillant garni d'emmental fondu, prêt en quelques minutes.",
                prepMinutes = 3,
                cookMinutes = 7,
                servings = 1,
                calories = 340,
                isVegetarian = true,
                tip = null,
                ingredients = listOf(
                    "Pain de mie" to "2 tranches",
                    "Emmental" to "60 g"
                ),
                steps = listOf(
                    TestStep("Garnir le pain", "Recouvrez les tranches de pain d'emmental râpé.", null),
                    TestStep("Gratiner", "Passez sous le gril jusqu'à ce que le fromage soit fondu et doré.", 5)
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
