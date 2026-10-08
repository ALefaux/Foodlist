package io.github.alefaux.foodlist.feature.recipes.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.recipe_detail_badge_vegetarian
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeAvailability
import io.github.alefaux.foodlist.feature.recipes.ui.RecipeAvailabilityBadge
import org.jetbrains.compose.resources.stringResource

/** Header of the recipe detail. Recipes have no pictures yet, so it uses a coloured background. */
@Composable
fun RecipeHero(
    name: String,
    description: String,
    availability: RecipeAvailability,
    isVegetarian: Boolean,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme

    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 220.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Brush.verticalGradient(listOf(colorScheme.primaryContainer, colorScheme.primary)))
    ) {
        Icon(
            imageVector = Icons.Filled.Restaurant,
            contentDescription = null,
            tint = colorScheme.onPrimary,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(20.dp)
                .size(72.dp)
                .alpha(0.2f)
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                RecipeAvailabilityBadge(availability = availability)
                if (isVegetarian) {
                    Text(
                        text = stringResource(Res.string.recipe_detail_badge_vegetarian),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = colorScheme.onSurface,
                        modifier = Modifier
                            .background(color = colorScheme.surfaceContainerLowest, shape = RoundedCornerShape(100))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = colorScheme.onPrimary
            )
            if (description.isNotBlank()) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorScheme.onPrimary.copy(alpha = 0.85f),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
@Preview
private fun RecipeHeroPreview() {
    MaterialTheme {
        RecipeHero(
            name = "Spaghetti with Peas",
            description = "A quick weeknight pasta made from pantry staples.",
            availability = RecipeAvailability.CAN_MAKE,
            isVegetarian = true
        )
    }
}
