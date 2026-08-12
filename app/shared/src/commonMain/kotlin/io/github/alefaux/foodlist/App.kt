package io.github.alefaux.foodlist

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.alefaux.foodlist.menu.BottomMenu
import io.github.alefaux.foodlist.navigation.FoodlistDestinations
import io.github.alefaux.foodlist.navigation.FoodlistNavHost
import io.github.alefaux.foodlist.theme.FoodlistTheme

@Composable
@Preview
fun App() {
    FoodlistTheme {
        val navController = rememberNavController()
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination

        Scaffold(
            bottomBar = {
                NavigationBar {
                    listOf(
                        BottomMenu.Dashboard,
                        BottomMenu.Storage,
                        BottomMenu.Recipes,
                    ).forEach { menu ->
                        val destination = menu.toDestination()
                        val selected = destination != null &&
                            currentDestination?.hierarchy?.any { it.hasRoute(destination::class) } == true

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
                                if (destination != null) {
                                    navController.navigate(destination) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            selected = selected
                        )
                    }
                }
            }
        ) { padding ->
            FoodlistNavHost(
                navController = navController,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

private fun BottomMenu.toDestination(): FoodlistDestinations? = when (this) {
    BottomMenu.Dashboard -> FoodlistDestinations.Dashboard
    BottomMenu.Storage -> FoodlistDestinations.Storage
    BottomMenu.Recipes -> null
}
