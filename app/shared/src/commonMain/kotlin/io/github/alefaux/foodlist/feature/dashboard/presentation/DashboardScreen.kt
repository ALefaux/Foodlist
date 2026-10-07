package io.github.alefaux.foodlist.feature.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.dashboard_greeting
import io.github.alefaux.foodlist.feature.dashboard.panes.DashboardPane
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DashboardScreen(
    onAddClick: () -> Unit,
    onScanClick: () -> Unit,
    onExpiredProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DashboardViewModel = koinViewModel()
) {
    LifecycleResumeEffect(Unit) {
        viewModel.loadData()

        onPauseOrDispose {}
    }

    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = modifier
    ) { padding ->
        DashboardPane(
            modifier = Modifier.padding(padding),
            expiredProducts = state.expiredProducts,
            expiredProductsCount = state.expiredProductsCount,
            discardedProducts = state.discardedProducts,
            isDebug = state.isDebug,
            isServerUp = state.isServerUp,
            onAddClick = onAddClick,
            onScanClick = onScanClick,
            onExpiredProductClick = onExpiredProductClick
        )
    }
}