package io.github.alefaux.foodlist.feature.shopping.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.shopping_priority_nice
import foodlist.app.shared.generated.resources.shopping_priority_nice_description
import foodlist.app.shared.generated.resources.shopping_priority_standard
import foodlist.app.shared.generated.resources.shopping_priority_standard_description
import foodlist.app.shared.generated.resources.shopping_priority_urgent
import foodlist.app.shared.generated.resources.shopping_priority_urgent_description
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingPriority
import org.jetbrains.compose.resources.stringResource

@Composable
fun PriorityOption(
    priority: ShoppingPriority,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme
    val (title, description) = when (priority) {
        ShoppingPriority.URGENT ->
            Res.string.shopping_priority_urgent to Res.string.shopping_priority_urgent_description
        ShoppingPriority.STANDARD ->
            Res.string.shopping_priority_standard to Res.string.shopping_priority_standard_description
        ShoppingPriority.NICE_TO_HAVE ->
            Res.string.shopping_priority_nice to Res.string.shopping_priority_nice_description
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        selected = selected,
        shape = RoundedCornerShape(12.dp),
        color = if (selected) colorScheme.secondaryContainer.copy(alpha = 0.3f) else colorScheme.surfaceContainer,
        border = if (selected) BorderStroke(1.dp, colorScheme.secondary) else null
    ) {
        Row(
            modifier = Modifier.padding(end = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = selected, onClick = onClick)
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                Text(
                    text = stringResource(title),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(description),
                    style = MaterialTheme.typography.bodySmall,
                    color = colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
