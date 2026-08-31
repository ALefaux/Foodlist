package io.github.alefaux.foodlist.feature.productdetail.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.expiration_date_label
import foodlist.app.shared.generated.resources.expiration_date_none
import io.github.alefaux.foodlist.core.model.ProductFreshness
import io.github.alefaux.foodlist.core.model.extension.toDisplayString
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExpirationDateCard(
    expirationDate: LocalDate?,
    freshness: ProductFreshness,
    statusLabel: String,
    modifier: Modifier = Modifier
) {
    val containerColor = when (freshness) {
        ProductFreshness.EXPIRED -> MaterialTheme.colorScheme.errorContainer
        ProductFreshness.EXPIRING_SOON -> MaterialTheme.colorScheme.secondaryContainer
        ProductFreshness.FRESH -> MaterialTheme.colorScheme.surfaceContainerLowest
    }
    val contentColor = when (freshness) {
        ProductFreshness.EXPIRED -> MaterialTheme.colorScheme.onErrorContainer
        ProductFreshness.EXPIRING_SOON -> MaterialTheme.colorScheme.onSecondaryContainer
        ProductFreshness.FRESH -> MaterialTheme.colorScheme.onSurface
    }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = containerColor
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Event,
                    contentDescription = null,
                    tint = contentColor,
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = stringResource(Res.string.expiration_date_label),
                    style = MaterialTheme.typography.labelMedium,
                    color = contentColor
                )
            }

            Text(
                text = expirationDate?.toDisplayString() ?: stringResource(Res.string.expiration_date_none),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = contentColor,
                modifier = Modifier.padding(top = 8.dp)
            )

            if (expirationDate != null) {
                Text(
                    text = statusLabel,
                    style = MaterialTheme.typography.bodyMedium,
                    color = contentColor,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }
    }
}
