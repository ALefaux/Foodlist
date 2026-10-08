package io.github.alefaux.foodlist.feature.recipes.panes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.recipe_detail_ingredients_title
import foodlist.app.shared.generated.resources.recipe_detail_ingredients_total
import foodlist.app.shared.generated.resources.recipe_detail_steps_progress
import foodlist.app.shared.generated.resources.recipe_detail_steps_title
import io.github.alefaux.foodlist.feature.recipes.domain.IngredientStatus
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeDetail
import io.github.alefaux.foodlist.feature.recipes.ui.detail.PantryMatchCard
import io.github.alefaux.foodlist.feature.recipes.ui.detail.RecipeHero
import io.github.alefaux.foodlist.feature.recipes.ui.detail.RecipeIngredientRow
import io.github.alefaux.foodlist.feature.recipes.ui.detail.RecipeStatsCard
import io.github.alefaux.foodlist.feature.recipes.ui.detail.RecipeStepCard
import io.github.alefaux.foodlist.feature.recipes.ui.detail.RecipeTipCard
import kotlinx.collections.immutable.ImmutableSet
import org.jetbrains.compose.resources.stringResource

@Composable
fun RecipeDetailPane(
    recipe: RecipeDetail,
    completedSteps: ImmutableSet<Int>,
    onStepToggle: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val completedCount = recipe.steps.count { it.position in completedSteps }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            RecipeHero(
                name = recipe.name,
                description = recipe.description,
                availability = recipe.availability,
                isVegetarian = recipe.isVegetarian
            )
        }

        item {
            RecipeStatsCard(
                prepMinutes = recipe.prepMinutes,
                cookMinutes = recipe.cookMinutes,
                servings = recipe.servings,
                calories = recipe.calories
            )
        }

        if (recipe.ingredients.isNotEmpty()) {
            item {
                PantryMatchCard(
                    availableCount = recipe.ingredients.count { it.status != IngredientStatus.MISSING },
                    totalCount = recipe.ingredients.size
                )
            }

            item {
                SectionHeader(
                    icon = Icons.AutoMirrored.Filled.FormatListBulleted,
                    title = stringResource(Res.string.recipe_detail_ingredients_title),
                    trailing = stringResource(Res.string.recipe_detail_ingredients_total, recipe.ingredients.size)
                )
            }

            items(recipe.ingredients) { ingredient ->
                RecipeIngredientRow(ingredient = ingredient)
            }
        }

        if (recipe.steps.isNotEmpty()) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    SectionHeader(
                        icon = Icons.Filled.Restaurant,
                        title = stringResource(Res.string.recipe_detail_steps_title),
                        trailing = stringResource(
                            Res.string.recipe_detail_steps_progress,
                            completedCount,
                            recipe.steps.size
                        )
                    )
                    LinearProgressIndicator(
                        progress = { completedCount.toFloat() / recipe.steps.size },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(100))
                    )
                }
            }

            items(recipe.steps, key = { it.position }) { step ->
                RecipeStepCard(
                    step = step,
                    isCompleted = step.position in completedSteps,
                    onToggle = { onStepToggle(step.position) }
                )
            }
        }

        recipe.tip?.takeIf { it.isNotBlank() }?.let { tip ->
            item {
                RecipeTipCard(
                    tip = tip,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun SectionHeader(
    icon: ImageVector,
    title: String,
    trailing: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = trailing,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
