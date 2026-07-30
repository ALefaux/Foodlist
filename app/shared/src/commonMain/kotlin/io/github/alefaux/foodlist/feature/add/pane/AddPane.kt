package io.github.alefaux.foodlist.feature.add.pane

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.alefaux.foodlist.core.component.SearchTextField

@Composable
fun AddPane(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        SearchTextField()
    }
}