package io.github.alefaux.foodlist.feature.shopping.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.alefaux.foodlist.feature.shopping.panes.ShoppingListPane
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ShoppingListScreen(
    modifier: Modifier = Modifier,
    viewModel: ShoppingListViewModel = koinViewModel()
) {
    LifecycleResumeEffect(Unit) {
        viewModel.loadData()

        onPauseOrDispose {}
    }

    val state = viewModel.uiState.collectAsStateWithLifecycle().value

    Scaffold(
        modifier = modifier
    ) { padding ->
        ShoppingListPane(
            modifier = Modifier.padding(padding),
            state = state,
            onNewItemNameChange = viewModel::updateNewItemName,
            onAddItem = viewModel::addItem,
            onAddAllSuggestions = viewModel::addAllSuggestions,
            onTabSelected = viewModel::selectTab,
            onPurchasedChange = viewModel::setPurchased,
            onDeleteItem = viewModel::deleteItem
        )
    }
}
