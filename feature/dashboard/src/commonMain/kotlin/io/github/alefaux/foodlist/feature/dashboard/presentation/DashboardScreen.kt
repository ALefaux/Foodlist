package io.github.alefaux.foodlist.feature.dashboard.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.alefaux.foodlist.feature.dashboard.panes.DashboardPane
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = koinViewModel()
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            MediumTopAppBar(
                title = {
                    Text(
                        text = "Good morning, Kitchen manager"
                    )
                }
            )
        }
    ) { padding ->
        DashboardPane(
            modifier = Modifier.padding(padding)
        )
    }
}

@Preview
@Composable
private fun DashboardScreenPreview() {
    MaterialTheme {
        DashboardScreen()
    }
}