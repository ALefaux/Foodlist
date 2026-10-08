package io.github.alefaux.foodlist.feature.dashboard.ui.server

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.dashboard_server_down
import foodlist.app.shared.generated.resources.dashboard_server_up
import org.jetbrains.compose.resources.stringResource

@Composable
fun ServerStatusChip(
    isServerUp: Boolean,
    modifier: Modifier = Modifier
) {
    val colorScheme = MaterialTheme.colorScheme

    Text(
        modifier = modifier
            .background(
                color = if (isServerUp) colorScheme.primary else colorScheme.error,
                shape = RoundedCornerShape(100)
            )
            .padding(horizontal = 8.dp),
        color = if (isServerUp) colorScheme.onPrimary else colorScheme.onError,
        text = stringResource(
            if (isServerUp) Res.string.dashboard_server_up else Res.string.dashboard_server_down
        )
    )
}

@Composable
@Preview
private fun ServerStatusChipUpPreview() {
    MaterialTheme {
        ServerStatusChip(
            isServerUp = true
        )
    }
}

@Composable
@Preview
private fun ServerStatusChipDownPreview() {
    MaterialTheme {
        ServerStatusChip(
            isServerUp = false
        )
    }
}
