package io.github.alefaux.foodlist.feature.shopping.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.shopping_suggestions_add_all
import foodlist.app.shared.generated.resources.shopping_suggestions_message
import foodlist.app.shared.generated.resources.shopping_suggestions_title
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource

/** Offers to add the recipe ingredients missing from the pantry, listed under the count. */
@Composable
fun SmartSuggestionsCard(
    suggestionNames: List<String>,
    onAddAll: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme

    Card(
        modifier = modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, colorScheme.secondaryContainer),
        colors = CardDefaults.cardColors(
            containerColor = colorScheme.secondaryContainer.copy(alpha = 0.25f)
        )
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(Res.string.shopping_suggestions_title),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.Bold,
                    color = colorScheme.secondary
                )
                Text(
                    text = pluralStringResource(
                        Res.plurals.shopping_suggestions_message,
                        suggestionNames.size,
                        suggestionNames.size
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = suggestionNames.joinToString(", "),
                    style = MaterialTheme.typography.bodySmall,
                    color = colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Button(
                onClick = onAddAll,
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorScheme.secondary,
                    contentColor = colorScheme.onSecondary
                )
            ) {
                Text(stringResource(Res.string.shopping_suggestions_add_all))
            }
        }
    }
}

@Composable
@Preview
private fun SmartSuggestionsCardPreview() {
    MaterialTheme {
        SmartSuggestionsCard(
            suggestionNames = listOf("Lasagna Sheets", "Ground Beef", "Tomato Sauce"),
            onAddAll = {}
        )
    }
}
