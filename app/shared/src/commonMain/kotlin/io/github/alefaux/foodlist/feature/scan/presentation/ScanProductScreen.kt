package io.github.alefaux.foodlist.feature.scan.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.alefaux.foodlist.feature.scan.pane.ScanPane
import io.github.alefaux.foodlist.feature.scan.presentation.model.ScanUiState
import io.github.alefaux.foodlist.feature.scan.ui.ScanTopBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ScanProductScreen(
    onBackPress: () -> Unit,
    onManualEntryClick: () -> Unit,
    onProductAdded: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ScanViewModel = koinViewModel()
) {
    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    var torchEnabled by remember { mutableStateOf(false) }

    LifecycleResumeEffect(Unit) {
        viewModel.checkConnectivity()

        onPauseOrDispose {}
    }

    LaunchedEffect(state) {
        if (state is ScanUiState.Saved) onProductAdded()
    }

    Box(modifier = modifier.fillMaxSize()) {
        ScanPane(
            modifier = Modifier.fillMaxSize(),
            state = state,
            torchEnabled = torchEnabled,
            onBarcodeDetected = viewModel::onBarcodeDetected,
            onPermissionDenied = viewModel::onPermissionDenied,
            onManualEntryClick = onManualEntryClick,
            onCancel = viewModel::resetScanning,
            onAddToPantry = viewModel::addToPantry,
            onRetryConnectivity = viewModel::checkConnectivity
        )

        ScanTopBar(
            modifier = Modifier.align(Alignment.TopCenter),
            torchEnabled = torchEnabled,
            onBackPress = onBackPress,
            onToggleTorch = { torchEnabled = !torchEnabled }
        )
    }
}
