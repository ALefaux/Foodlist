package io.github.alefaux.foodlist.feature.productdetail.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.product_detail_top_bar_title
import io.github.alefaux.foodlist.feature.productdetail.panes.ProductDetailPane
import io.github.alefaux.foodlist.feature.productdetail.ui.DeleteProductDialog
import io.github.alefaux.foodlist.feature.productdetail.ui.DiscardProductDialog
import io.github.alefaux.foodlist.feature.productdetail.ui.EditProductSheet
import io.github.alefaux.foodlist.feature.productdetail.ui.MoveToStorageSheet
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    onBackPress: () -> Unit,
    onProductDeleted: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProductDetailViewModel = koinViewModel(parameters = { parametersOf(productId) })
) {
    LifecycleResumeEffect(Unit) {
        viewModel.loadData()

        onPauseOrDispose {}
    }

    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    LaunchedEffect(state.isDeleted) {
        if (state.isDeleted) onProductDeleted()
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
                        text = stringResource(Res.string.product_detail_top_bar_title),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            )
        }
    ) { padding ->
        ProductDetailPane(
            modifier = Modifier.padding(padding),
            state = state,
            onIncreaseStock = viewModel::increaseStock,
            onDecreaseStock = viewModel::decreaseStock,
            onEditClick = viewModel::showEditSheet,
            onMoveClick = viewModel::showMoveSheet,
            onDiscardClick = viewModel::showDiscardDialog,
            onDeleteClick = viewModel::showDeleteDialog
        )
    }

    if (state.isEditSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = viewModel::dismissEditSheet,
            sheetState = rememberModalBottomSheetState()
        ) {
            EditProductSheet(
                name = state.name,
                quantity = state.quantity,
                category = state.category,
                expirationDate = state.expirationDate,
                onDismiss = viewModel::dismissEditSheet,
                onSave = viewModel::saveEdit
            )
        }
    }

    if (state.isMoveSheetVisible) {
        ModalBottomSheet(
            onDismissRequest = viewModel::dismissMoveSheet,
            sheetState = rememberModalBottomSheetState()
        ) {
            MoveToStorageSheet(
                storages = state.availableStorages,
                currentStorageId = state.storageId,
                onStorageSelected = viewModel::moveToStorage
            )
        }
    }

    if (state.isDeleteDialogVisible) {
        DeleteProductDialog(
            productName = state.name,
            onDismiss = viewModel::dismissDeleteDialog,
            onConfirm = viewModel::deleteProduct
        )
    }

    if (state.isDiscardDialogVisible) {
        DiscardProductDialog(
            productName = state.name,
            onDismiss = viewModel::dismissDiscardDialog,
            onConfirm = viewModel::discardProduct
        )
    }
}
