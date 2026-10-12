package io.github.alefaux.foodlist.feature.shopping.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.shopping_priority_badge_optional
import foodlist.app.shared.generated.resources.shopping_priority_badge_urgent
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingPriority
import org.jetbrains.compose.resources.stringResource

/** Small label for urgent and optional items. Standard items show nothing. */
@Composable
fun ShoppingPriorityBadge(
    priority: ShoppingPriority,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    val (text, containerColor, contentColor) = when (priority) {
        ShoppingPriority.URGENT -> Triple(
            stringResource(Res.string.shopping_priority_badge_urgent),
            colorScheme.errorContainer,
            colorScheme.error
        )
        ShoppingPriority.NICE_TO_HAVE -> Triple(
            stringResource(Res.string.shopping_priority_badge_optional),
            colorScheme.surfaceContainerHigh,
            colorScheme.onSurfaceVariant
        )
        ShoppingPriority.STANDARD -> return
    }

    Text(
        modifier = modifier
            .background(color = containerColor, shape = RoundedCornerShape(100))
            .padding(horizontal = 8.dp, vertical = 2.dp),
        text = text,
        style = MaterialTheme.typography.labelSmall,
        fontWeight = FontWeight.Bold,
        color = contentColor
    )
}
