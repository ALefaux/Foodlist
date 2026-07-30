package io.github.alefaux.foodlist.feature.add.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.alefaux.foodlist.feature.add.pane.AddPane

@Composable
fun AddScreen(
    modifier: Modifier = Modifier,
    onBackPress: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        content = {
                            Icon(
                                contentDescription = null,
                                imageVector = Icons.AutoMirrored.Default.ArrowBack
                            )
                        },
                        onClick = onBackPress
                    )
                },
                title = {
                    Text("Add")
                }
            )
        }
    ) { innerPadding ->
        AddPane(
            modifier = Modifier.padding(innerPadding)
        )
    }
}