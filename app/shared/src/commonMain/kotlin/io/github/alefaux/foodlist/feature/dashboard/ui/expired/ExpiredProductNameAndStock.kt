package io.github.alefaux.foodlist.feature.dashboard.ui.expired

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ExpiredProductNameAndStock(
    name: String,
    stock: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight(),
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        ExpiredProductName(
            text = name
        )
        ExpiredProductStock(
            text = stock
        )
    }
}