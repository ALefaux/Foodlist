package io.github.alefaux.foodlist.feature.search.pane

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.alefaux.foodlist.core.component.SearchTextField

@Composable
fun SearchPane(
    modifier: Modifier = Modifier
) {
    var queryState by remember { mutableStateOf("") }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        SearchTextField(
            onSearchChange = { query ->
                queryState = query
            },
            query = queryState
        )
    }
}