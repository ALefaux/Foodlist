package io.github.alefaux.foodlist.feature.shopping.panes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import foodlist.app.shared.generated.resources.Res
import foodlist.app.shared.generated.resources.shopping_empty_purchased
import foodlist.app.shared.generated.resources.shopping_empty_to_buy
import foodlist.app.shared.generated.resources.shopping_recently_purchased
import foodlist.app.shared.generated.resources.shopping_tab_purchased
import foodlist.app.shared.generated.resources.shopping_tab_to_buy
import foodlist.app.shared.generated.resources.shopping_title
import io.github.alefaux.foodlist.core.component.HomeHeader
import io.github.alefaux.foodlist.feature.shopping.domain.ShoppingItem
import io.github.alefaux.foodlist.feature.shopping.presentation.model.ShoppingListTab
import io.github.alefaux.foodlist.feature.shopping.presentation.model.ShoppingListUiState
import io.github.alefaux.foodlist.feature.shopping.ui.AddItemField
import io.github.alefaux.foodlist.feature.shopping.ui.ShoppingItemRow
import io.github.alefaux.foodlist.feature.shopping.ui.ShoppingListSkeleton
import io.github.alefaux.foodlist.feature.shopping.ui.SmartSuggestionsCard
import org.jetbrains.compose.resources.stringResource

@Composable
fun ShoppingListPane(
    state: ShoppingListUiState,
    onNewItemNameChange: (String) -> Unit,
    onAddItem: () -> Unit,
    onAddAllSuggestions: () -> Unit,
    onTabSelected: (ShoppingListTab) -> Unit,
    onPurchasedChange: (Long, Boolean) -> Unit,
    onDeleteItem: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                HomeHeader(title = stringResource(Res.string.shopping_title))
                AddItemField(
                    value = state.newItemName,
                    onValueChange = onNewItemNameChange,
                    onAdd = onAddItem
                )
                if (state.suggestions.isNotEmpty()) {
                    SmartSuggestionsCard(
                        suggestionNames = state.suggestions.map { it.name },
                        onAddAll = onAddAllSuggestions
                    )
                }
                PrimaryTabRow(selectedTabIndex = state.selectedTab.ordinal) {
                    ShoppingListTab.entries.forEach { tab ->
                        Tab(
                            selected = tab == state.selectedTab,
                            onClick = { onTabSelected(tab) },
                            text = {
                                Text(
                                    text = stringResource(
                                        when (tab) {
                                            ShoppingListTab.TO_BUY -> Res.string.shopping_tab_to_buy
                                            ShoppingListTab.PURCHASED -> Res.string.shopping_tab_purchased
                                        }
                                    ),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        )
                    }
                }
            }
        }

        if (state.isLoading) {
            item {
                ShoppingListSkeleton()
            }
            return@LazyColumn
        }

        when (state.selectedTab) {
            ShoppingListTab.TO_BUY -> {
                if (state.toBuy.isEmpty()) {
                    emptyMessage(Res.string.shopping_empty_to_buy)
                } else {
                    itemRows(state.toBuy, onPurchasedChange, onDeleteItem)
                }

                if (state.purchased.isNotEmpty()) {
                    item {
                        RecentlyPurchasedHeader()
                    }
                    itemRows(state.purchased.take(RECENTLY_PURCHASED_COUNT), onPurchasedChange, onDeleteItem)
                }
            }

            ShoppingListTab.PURCHASED -> {
                if (state.purchased.isEmpty()) {
                    emptyMessage(Res.string.shopping_empty_purchased)
                } else {
                    itemRows(state.purchased, onPurchasedChange, onDeleteItem)
                }
            }
        }
    }
}

private fun LazyListScope.itemRows(
    items: List<ShoppingItem>,
    onPurchasedChange: (Long, Boolean) -> Unit,
    onDeleteItem: (Long) -> Unit
) {
    items(items, key = { it.id }) { item ->
        ShoppingItemRow(
            item = item,
            onPurchasedChange = { onPurchasedChange(item.id, it) },
            onDelete = { onDeleteItem(item.id) }
        )
    }
}

private fun LazyListScope.emptyMessage(text: org.jetbrains.compose.resources.StringResource) {
    item {
        Text(
            modifier = Modifier.padding(vertical = 16.dp),
            text = stringResource(text),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun RecentlyPurchasedHeader() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(Res.string.shopping_recently_purchased),
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(end = 12.dp)
        )
        HorizontalDivider(modifier = Modifier.weight(1f))
    }
}

private const val RECENTLY_PURCHASED_COUNT = 3
