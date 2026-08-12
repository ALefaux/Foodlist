package io.github.alefaux.foodlist.feature.storage.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.alefaux.foodlist.feature.storage.panes.StoragePane
import io.github.alefaux.foodlist.feature.storage.ui.AddStorageUnitDialog
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun StorageScreen(
    onStorageClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StorageViewModel = koinViewModel()
) {
    LifecycleResumeEffect(Unit) {
        viewModel.loadData()

        onPauseOrDispose {}
    }

    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = modifier
    ) { padding ->
        StoragePane(
            modifier = Modifier.padding(padding),
            storageUnits = state.storageUnits,
            onAddClick = viewModel::showAddDialog,
            onStorageClick = onStorageClick
        )
    }

    if (state.isAddDialogVisible) {
        AddStorageUnitDialog(
            onDismiss = viewModel::dismissAddDialog,
            onConfirm = viewModel::addStorageUnit
        )
    }
}
