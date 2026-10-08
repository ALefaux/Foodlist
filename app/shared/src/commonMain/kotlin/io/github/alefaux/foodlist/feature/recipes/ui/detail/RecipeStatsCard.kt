package io.github.alefaux.foodlist.feature.recipes.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SoupKitchen
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.recipe_detail_calories
import foodlist.app.shared.generated.resources.recipe_detail_cook
import foodlist.app.shared.generated.resources.recipe_detail_energy
import foodlist.app.shared.generated.resources.recipe_detail_minutes
import foodlist.app.shared.generated.resources.recipe_detail_prep
import foodlist.app.shared.generated.resources.recipe_detail_servings
import org.jetbrains.compose.resources.stringResource

@Composable
fun RecipeStatsCard(
    prepMinutes: Int,
    cookMinutes: Int,
    servings: Int?,
    calories: Int?,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            RecipeStat(
                icon = Icons.Filled.Schedule,
                label = stringResource(Res.string.recipe_detail_prep),
                value = stringResource(Res.string.recipe_detail_minutes, prepMinutes)
            )
            RecipeStat(
                icon = Icons.Filled.SoupKitchen,
                label = stringResource(Res.string.recipe_detail_cook),
                value = stringResource(Res.string.recipe_detail_minutes, cookMinutes)
            )
            RecipeStat(
                icon = Icons.Filled.Group,
                label = stringResource(Res.string.recipe_detail_servings),
                value = servings?.toString() ?: NOT_AVAILABLE
            )
            RecipeStat(
                icon = Icons.Filled.LocalFireDepartment,
                label = stringResource(Res.string.recipe_detail_energy),
                value = calories?.let { stringResource(Res.string.recipe_detail_calories, it) } ?: NOT_AVAILABLE
            )
        }
    }
}

@Composable
private fun RecipeStat(
    icon: ImageVector,
    label: String,
    value: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

private const val NOT_AVAILABLE = "—"

@Composable
@Preview
private fun RecipeStatsCardPreview() {
    MaterialTheme {
        RecipeStatsCard(
            prepMinutes = 10,
            cookMinutes = 15,
            servings = 4,
            calories = 520
        )
    }
}
