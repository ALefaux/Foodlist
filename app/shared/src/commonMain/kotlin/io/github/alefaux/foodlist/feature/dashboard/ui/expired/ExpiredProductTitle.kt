package io.github.alefaux.foodlist.feature.dashboard.ui.expired

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.dashboard_expired_products_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExpiredProductTitle(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.headlineSmall,
        text = stringResource(Res.string.dashboard_expired_products_title)
    )
}

@Composable
@Preview
fun ExpiredProductTitlePreview() {
    MaterialTheme {
        ExpiredProductTitle()
    }
}