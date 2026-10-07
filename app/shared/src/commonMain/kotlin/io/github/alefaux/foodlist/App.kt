package io.github.alefaux.foodlist

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.alefaux.foodlist.core.theme.ThemeRepository
import io.github.alefaux.foodlist.menu.BottomMenu
import io.github.alefaux.foodlist.navigation.FoodlistDestinations
import io.github.alefaux.foodlist.navigation.FoodlistNavHost
import io.github.alefaux.foodlist.theme.FoodlistTheme
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    val themeRepository: ThemeRepository = koinInject()
    val isDarkThemeEnabled by themeRepository.isDarkThemeEnabled.collectAsState()

    FoodlistTheme(darkTheme = isDarkThemeEnabled ?: isSystemInDarkTheme()) {
        val navController = rememberNavController()
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination

        val isOnAuthRoute = currentDestination?.hierarchy?.any {
            it.hasRoute(FoodlistDestinations.Login::class) || it.hasRoute(FoodlistDestinations.CreateAccount::class)
        } == true

        Scaffold(
            bottomBar = {
                if (!isOnAuthRoute) {
                    NavigationBar {
                        listOf(
                            BottomMenu.Dashboard,
                            BottomMenu.Storage,
                            // BottomMenu.Recipes, // Available in V2
                            BottomMenu.Profile,
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
                                        text = stringResource(menu.label)
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
            }
        ) { padding ->
            FoodlistNavHost(
                navController = navController,
                modifier = Modifier
                    .padding(padding)
                    .consumeWindowInsets(padding)
            )
        }
    }
}

private fun BottomMenu.toDestination(): FoodlistDestinations? = when (this) {
    BottomMenu.Dashboard -> FoodlistDestinations.Dashboard
    BottomMenu.Storage -> FoodlistDestinations.Storage
    BottomMenu.Recipes -> null
    BottomMenu.Profile -> FoodlistDestinations.Profile
}
