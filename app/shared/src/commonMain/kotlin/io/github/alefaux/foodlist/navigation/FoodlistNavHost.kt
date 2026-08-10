package io.github.alefaux.foodlist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.alefaux.foodlist.feature.add.presentation.AddScreen
import io.github.alefaux.foodlist.feature.dashboard.presentation.DashboardScreen
import io.github.alefaux.foodlist.feature.scan.presentation.ScanProductScreen

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
                },
                onScanClick = {
                    navController.navigate(FoodlistDestinations.Scan)
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
        composable<FoodlistDestinations.Scan> {
            ScanProductScreen(
                onBackPress = {
                    navController.navigateUp()
                },
                onManualEntryClick = {
                    navController.navigate(FoodlistDestinations.Add)
                },
                onProductAdded = {
                    navController.navigateUp()
                }
            )
        }
    }
}