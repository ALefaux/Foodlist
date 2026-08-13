package io.github.alefaux.foodlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.alefaux.foodlist.feature.auth.presentation.SessionViewModel
import io.github.alefaux.foodlist.feature.auth.presentation.model.SessionState
import io.github.alefaux.foodlist.menu.BottomMenu
import io.github.alefaux.foodlist.navigation.FoodlistDestinations
import io.github.alefaux.foodlist.navigation.FoodlistNavHost
import io.github.alefaux.foodlist.theme.FoodlistTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    FoodlistTheme {
        val sessionViewModel: SessionViewModel = koinViewModel()
        val sessionState = sessionViewModel.sessionState.collectAsStateWithLifecycle().value

        when (sessionState) {
            SessionState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            else -> {
                AuthenticatedApp(sessionState = sessionState)
            }
        }
    }
}

@Composable
private fun AuthenticatedApp(sessionState: SessionState) {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination

    val isOnAuthRoute = currentDestination?.hierarchy?.any {
        it.hasRoute(FoodlistDestinations.Login::class) || it.hasRoute(FoodlistDestinations.CreateAccount::class)
    } == true

    LaunchedEffect(sessionState) {
        if (sessionState == SessionState.Unauthenticated && !isOnAuthRoute) {
            navController.navigate(FoodlistDestinations.Login) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

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
        }
    ) { padding ->
        FoodlistNavHost(
            navController = navController,
            modifier = Modifier.padding(padding),
            startDestination = if (sessionState is SessionState.Authenticated) {
                FoodlistDestinations.Dashboard
            } else {
                FoodlistDestinations.Login
            }
        )
    }
}

private fun BottomMenu.toDestination(): FoodlistDestinations? = when (this) {
    BottomMenu.Dashboard -> FoodlistDestinations.Dashboard
    BottomMenu.Storage -> FoodlistDestinations.Storage
    BottomMenu.Recipes -> null
    BottomMenu.Profile -> FoodlistDestinations.Profile
}
