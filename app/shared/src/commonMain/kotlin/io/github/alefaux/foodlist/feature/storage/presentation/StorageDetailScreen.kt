package io.github.alefaux.foodlist.feature.storage.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.storage_detail_add_item_fab
import foodlist.app.shared.generated.resources.storage_detail_delete_menu_item
import io.github.alefaux.foodlist.feature.productdetail.ui.DiscardProductDialog
import io.github.alefaux.foodlist.feature.storage.panes.StorageDetailPane
import io.github.alefaux.foodlist.feature.storage.ui.DeleteStorageDialog
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StorageDetailScreen(
    storageId: Long,
    onBackPress: () -> Unit,
    onAddItemClick: () -> Unit,
    onStorageDeleted: () -> Unit,
    onProductClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: StorageDetailViewModel = koinViewModel(parameters = { parametersOf(storageId) })
) {
    LifecycleResumeEffect(Unit) {
        viewModel.loadData()

        onPauseOrDispose {}
    }

    val state = viewModel.uiState.collectAsStateWithLifecycle().value
    var isMenuExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(state.isDeleted) {
        if (state.isDeleted) onStorageDeleted()
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                title = {
                    Text(
                        text = state.storageName,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                actions = {
                    IconButton(onClick = { isMenuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = null
                        )
                    }
                    DropdownMenu(
                        expanded = isMenuExpanded,
                        onDismissRequest = { isMenuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(stringResource(Res.string.storage_detail_delete_menu_item))
                            },
                            onClick = {
                                isMenuExpanded = false
                                viewModel.showDeleteDialog()
                            }
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddItemClick,
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null
                    )
                },
                text = {
                    Text(stringResource(Res.string.storage_detail_add_item_fab))
                }
            )
        }
    ) { padding ->
        StorageDetailPane(
            modifier = Modifier.padding(padding),
            state = state,
            onCategorySelected = viewModel::selectCategory,
            onProductClick = onProductClick,
            onDiscardClick = viewModel::requestDiscard,
            onRestoreDiscardedClick = viewModel::restoreDiscardedProduct
        )
    }

    if (state.isDeleteDialogVisible) {
        DeleteStorageDialog(
            storageName = state.storageName,
            onDismiss = viewModel::dismissDeleteDialog,
            onConfirm = viewModel::deleteStorageUnit
        )
    }

    state.productPendingDiscard?.let { product ->
        DiscardProductDialog(
            productName = product.name,
            onDismiss = viewModel::dismissDiscardDialog,
            onConfirm = viewModel::confirmDiscard
        )
    }
}
