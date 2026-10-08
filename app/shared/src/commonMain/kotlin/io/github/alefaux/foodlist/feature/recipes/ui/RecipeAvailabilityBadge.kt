package io.github.alefaux.foodlist.feature.recipes.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PriorityHigh
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.recipes_badge_can_make
import foodlist.app.shared.generated.resources.recipes_badge_uses_expiring
import io.github.alefaux.foodlist.feature.recipes.domain.RecipeAvailability
import org.jetbrains.compose.resources.stringResource

/** Badge shown on the recipe picture. Nothing is shown when ingredients are missing. */
@Composable
fun RecipeAvailabilityBadge(
    availability: RecipeAvailability,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    val (icon, text, containerColor, contentColor) = when (availability) {
        RecipeAvailability.CAN_MAKE -> BadgeStyle(
            icon = Icons.Filled.CheckCircle,
            text = stringResource(Res.string.recipes_badge_can_make),
            containerColor = colorScheme.primary,
            contentColor = colorScheme.onPrimary
        )
        RecipeAvailability.USES_EXPIRING_ITEMS -> BadgeStyle(
            icon = Icons.Filled.PriorityHigh,
            text = stringResource(Res.string.recipes_badge_uses_expiring),
            containerColor = colorScheme.secondaryContainer,
            contentColor = colorScheme.onSecondaryContainer
        )
        RecipeAvailability.MISSING_INGREDIENTS -> return
    }

    Row(
        modifier = modifier
            .background(color = containerColor, shape = RoundedCornerShape(100))
            .padding(horizontal = 10.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(14.dp)
        )
        Text(
            text = text,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            color = contentColor
        )
    }
}

private data class BadgeStyle(
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val text: String,
    val containerColor: androidx.compose.ui.graphics.Color,
    val contentColor: androidx.compose.ui.graphics.Color
)
