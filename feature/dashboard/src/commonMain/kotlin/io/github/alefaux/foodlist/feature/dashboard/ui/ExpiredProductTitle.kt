package io.github.alefaux.foodlist.feature.dashboard.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ExpiredProductTitle(modifier: Modifier = Modifier) {
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.headlineSmall,
        text = "Expired products"
    )
}

@Composable
@Preview
fun ExpiredProductTitlePreview() {
    MaterialTheme {
        ExpiredProductTitle()
    }
}