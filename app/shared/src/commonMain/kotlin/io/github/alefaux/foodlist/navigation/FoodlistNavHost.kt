package io.github.alefaux.foodlist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.alefaux.foodlist.feature.add.presentation.AddScreen
import io.github.alefaux.foodlist.feature.dashboard.presentation.DashboardScreen

@Composable
fun FoodlistNavHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = FoodlistDestinations.Dashboard
    ) {
        composable<FoodlistDestinations.Dashboard> {
            DashboardScreen(
                onAddClick = {
                    navController.navigate(FoodlistDestinations.Add)
                }
            )
        }
        composable<FoodlistDestinations.Add> {
            AddScreen(
                onBackPress = {
                    navController.navigateUp()
                }
            )
        }
    }
}