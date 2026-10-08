package io.github.alefaux.foodlist.feature.recipes.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.recipe_detail_ingredient_expiring
import foodlist.app.shared.generated.resources.recipe_detail_ingredient_in_pantry
import foodlist.app.shared.generated.resources.recipe_detail_ingredient_missing
import foodlist.app.shared.generated.resources.recipe_detail_ingredient_required
import io.github.alefaux.foodlist.feature.recipes.domain.IngredientStatus
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeIngredient
import org.jetbrains.compose.resources.stringResource

@Composable
fun RecipeIngredientRow(
    ingredient: RecipeIngredient,
    modifier: Modifier = Modifier
) {
    val style = ingredient.status.style()

    val details = when (ingredient.status) {
        IngredientStatus.MISSING -> ingredient.quantity.takeIf { it.isNotBlank() }
            ?.let { stringResource(Res.string.recipe_detail_ingredient_required, it) }
        else -> listOfNotNull(ingredient.quantity.takeIf { it.isNotBlank() }, ingredient.storageName)
            .joinToString(" • ")
            .takeIf { it.isNotBlank() }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(40.dp),
                shape = RoundedCornerShape(10.dp),
                color = style.containerColor
            ) {
                Icon(
                    imageVector = if (ingredient.status == IngredientStatus.MISSING) {
                        Icons.Filled.ShoppingBasket
                    } else {
                        Icons.Filled.Kitchen
                    },
                    contentDescription = null,
                    tint = style.contentColor,
                    modifier = Modifier.padding(9.dp)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = ingredient.name,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                if (details != null) {
                    Text(
                        text = details,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Row(
                modifier = Modifier
                    .background(color = style.containerColor, shape = RoundedCornerShape(100))
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = style.icon,
                    contentDescription = null,
                    tint = style.contentColor,
                    modifier = Modifier.size(14.dp)
                )
                Text(
                    text = style.label,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Bold,
                    color = style.contentColor
                )
            }
        }
    }
}

private data class IngredientStatusStyle(
    val label: String,
    val icon: ImageVector,
    val containerColor: Color,
    val contentColor: Color
)

@Composable
private fun IngredientStatus.style(): IngredientStatusStyle {
    val colorScheme = MaterialTheme.colorScheme
    return when (this) {
        IngredientStatus.IN_PANTRY -> IngredientStatusStyle(
            label = stringResource(Res.string.recipe_detail_ingredient_in_pantry),
            icon = Icons.Filled.CheckCircle,
            containerColor = colorScheme.primaryContainer.copy(alpha = 0.3f),
            contentColor = colorScheme.primary
        )
        IngredientStatus.EXPIRING_SOON -> IngredientStatusStyle(
            label = stringResource(Res.string.recipe_detail_ingredient_expiring),
            icon = Icons.Filled.Warning,
            containerColor = colorScheme.secondaryContainer.copy(alpha = 0.4f),
            contentColor = colorScheme.secondary
        )
        IngredientStatus.MISSING -> IngredientStatusStyle(
            label = stringResource(Res.string.recipe_detail_ingredient_missing),
            icon = Icons.Filled.RemoveCircle,
            containerColor = colorScheme.errorContainer,
            contentColor = colorScheme.error
        )
    }
}

@Composable
@Preview
private fun RecipeIngredientRowPreview() {
    MaterialTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            RecipeIngredientRow(
                ingredient = RecipeIngredient("Spaghetti", "200 g", IngredientStatus.IN_PANTRY, "Pantry")
            )
            RecipeIngredientRow(
                ingredient = RecipeIngredient("Greek Yogurt", "125 g", IngredientStatus.EXPIRING_SOON, "Fridge")
            )
            RecipeIngredientRow(
                ingredient = RecipeIngredient("Ground Beef", "400 g", IngredientStatus.MISSING, null)
            )
        }
    }
}
