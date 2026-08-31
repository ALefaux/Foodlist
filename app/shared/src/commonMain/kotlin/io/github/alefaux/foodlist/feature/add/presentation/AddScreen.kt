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
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.add_top_bar_title
import io.github.alefaux.foodlist.feature.add.pane.AddPane
import org.jetbrains.compose.resources.stringResource

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
                    Text(stringResource(Res.string.add_top_bar_title))
                }
            )
        }
    ) { innerPadding ->
        AddPane(
            modifier = Modifier.padding(innerPadding)
        )
    }
}