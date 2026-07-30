package io.github.alefaux.foodlist

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import io.github.alefaux.foodlist.menu.BottomMenu
import io.github.alefaux.foodlist.feature.dashboard.presentation.DashboardScreen
import io.github.alefaux.foodlist.navigation.FoodlistNavHost
import io.github.alefaux.foodlist.theme.FoodlistTheme

@Composable
@Preview
fun App() {
    FoodlistTheme {
        var menuSelected: BottomMenu by remember { mutableStateOf(BottomMenu.Dashboard) }
        Scaffold(
            bottomBar = {
                NavigationBar {
                    listOf(
                        BottomMenu.Dashboard,
                        BottomMenu.Storage,
                        BottomMenu.Recipes,
                    ).forEach { menu ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    contentDescription = null,
                                    imageVector = menu.icon
                                )
                            },
                            label = {
                                Text(
                                    text = menu.label
                                )
                            },
                            onClick = {
                                menuSelected = menu
                            },
                            selected = menu == menuSelected
                        )
                    }
                }
            }
        ) {
            FoodlistNavHost()
        }
    }
}