package io.github.alefaux.foodlist.feature.recipes.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.recipes_duration
import foodlist.app.shared.generated.resources.recipes_ingredients_count
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeAvailability
import io.github.alefaux.foodlist.feature.recipes.modelui.RecipeUi
import org.jetbrains.compose.resources.stringResource

@Composable
fun RecipeCard(
    recipe: RecipeUi,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Box {
            // Placeholder until recipes have pictures
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(170.dp),
                color = MaterialTheme.colorScheme.surfaceContainerHigh
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Filled.Restaurant,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(56.dp)
                    )
                }
            }

            RecipeAvailabilityBadge(
                availability = recipe.availability,
                modifier = Modifier.padding(12.dp)
            )
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = recipe.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                RecipeInfo(
                    icon = Icons.Filled.Restaurant,
                    text = stringResource(Res.string.recipes_ingredients_count, recipe.ingredientsCount)
                )
                RecipeInfo(
                    icon = Icons.Filled.Schedule,
                    text = stringResource(Res.string.recipes_duration, recipe.durationMinutes)
                )
            }
        }
    }
}

@Composable
private fun RecipeInfo(
    icon: ImageVector,
    text: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(16.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
@Preview
private fun RecipeCardPreview() {
    MaterialTheme {
        RecipeCard(
            recipe = RecipeUi(
                id = 1L,
                name = "Classic Lasagna",
                ingredientsCount = 8,
                durationMinutes = 45,
                availability = RecipeAvailability.CAN_MAKE
            ),
            onClick = {}
        )
    }
}

@Composable
@Preview
private fun RecipeCardExpiringPreview() {
    MaterialTheme {
        RecipeCard(
            recipe = RecipeUi(
                id = 2L,
                name = "Vegetable Soup",
                ingredientsCount = 6,
                durationMinutes = 30,
                availability = RecipeAvailability.USES_EXPIRING_ITEMS
            ),
            onClick = {}
        )
    }
}
