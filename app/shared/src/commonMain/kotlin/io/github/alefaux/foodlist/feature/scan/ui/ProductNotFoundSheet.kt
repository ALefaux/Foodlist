package io.github.alefaux.foodlist.feature.scan.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.product_not_found_manual_entry_button
import foodlist.app.shared.generated.resources.product_not_found_message
import foodlist.app.shared.generated.resources.product_not_found_title
import foodlist.app.shared.generated.resources.product_not_found_try_again_button
import org.jetbrains.compose.resources.stringResource

@Composable
fun ProductNotFoundSheet(
    ean: String,
    onTryAgain: () -> Unit,
    onManualEntryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp)
    ) {
        Text(
            text = stringResource(Res.string.product_not_found_title),
            style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = stringResource(Res.string.product_not_found_message, ean),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = onTryAgain
        ) {
            Text(stringResource(Res.string.product_not_found_try_again_button))
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onManualEntryClick
        ) {
            Text(stringResource(Res.string.product_not_found_manual_entry_button))
        }
    }
}
