package io.github.alefaux.foodlist.feature.search.pane

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.alefaux.foodlist.core.component.SearchTextField
import io.github.alefaux.foodlist.feature.search.ui.SearchIconButton

@Composable
fun SearchPane(
    modifier: Modifier = Modifier,
    onSearchClick: (query: String) -> Unit
) {
    var query by remember { mutableStateOf("") }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier.height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SearchTextField(
                modifier = Modifier.weight(1f),
                onSearchChange = { newQuery ->
                    query = newQuery
                },
                query = query
            )
            SearchIconButton(
                modifier = Modifier.fillMaxHeight()
                    .padding(top = 8.dp)
                    .aspectRatio(1f)
            ) {
                onSearchClick(query)
            }
        }
    }
}