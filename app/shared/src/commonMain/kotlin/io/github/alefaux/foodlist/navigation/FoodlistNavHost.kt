package io.github.alefaux.foodlist.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.alefaux.foodlist.feature.add.presentation.AddScreen
import io.github.alefaux.foodlist.feature.auth.presentation.CreateAccountScreen
import io.github.alefaux.foodlist.feature.auth.presentation.LoginScreen
import io.github.alefaux.foodlist.feature.dashboard.presentation.DashboardScreen
import io.github.alefaux.foodlist.feature.productdetail.presentation.ProductDetailScreen
import io.github.alefaux.foodlist.feature.profile.presentation.ProfileScreen
import io.github.alefaux.foodlist.feature.scan.presentation.ScanProductScreen
import io.github.alefaux.foodlist.feature.splash.presentation.SplashScreen
import io.github.alefaux.foodlist.feature.storage.presentation.StorageDetailScreen
import io.github.alefaux.foodlist.feature.storage.presentation.StorageScreen

@Composable
fun FoodlistNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: FoodlistDestinations = FoodlistDestinations.Splash
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable<FoodlistDestinations.Splash> {
            SplashScreen(
                onReady = {
                    navController.navigate(FoodlistDestinations.Dashboard) {
                        popUpTo(FoodlistDestinations.Splash) { inclusive = true }
                    }
                }
            )
        }
        composable<FoodlistDestinations.Login> {
            LoginScreen(
                onBackPress = {
                    navController.navigateUp()
                },
                onSignInSuccess = {
                    navController.popBackStack(FoodlistDestinations.Profile, inclusive = false)
                },
                onForgotPasswordClick = {},
                onAppleClick = {},
                onSignUpClick = {
                    navController.navigate(FoodlistDestinations.CreateAccount)
                }
            )
        }
        composable<FoodlistDestinations.CreateAccount> {
            CreateAccountScreen(
                onBackPress = {
                    navController.navigateUp()
                },
                onAccountCreated = {
                    navController.popBackStack(FoodlistDestinations.Profile, inclusive = false)
                },
                onAppleClick = {},
                onSignInClick = {
                    navController.navigateUp()
                },
                onTermsClick = {},
                onPrivacyPolicyClick = {}
            )
        }
        composable<FoodlistDestinations.Dashboard> {
            DashboardScreen(
                onAddClick = {
                    navController.navigate(FoodlistDestinations.Add)
                },
                onScanClick = {
                    navController.navigate(FoodlistDestinations.Scan)
                },
                onExpiredProductClick = { productId ->
                    navController.navigate(FoodlistDestinations.ProductDetail(productId))
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
                onProductAdded = { storageId ->
                    navController.navigate(FoodlistDestinations.StorageDetail(storageId)) {
                        popUpTo(FoodlistDestinations.Scan) { inclusive = true }
                    }
                }
            )
        }
        composable<FoodlistDestinations.Storage> {
            StorageScreen(
                onStorageClick = { storageId ->
                    navController.navigate(FoodlistDestinations.StorageDetail(storageId))
                }
            )
        }
        composable<FoodlistDestinations.StorageDetail> { backStackEntry ->
            val destination = backStackEntry.toRoute<FoodlistDestinations.StorageDetail>()

            StorageDetailScreen(
                storageId = destination.storageId,
                onBackPress = {
                    navController.navigateUp()
                },
                onAddItemClick = {
                    navController.navigate(FoodlistDestinations.Add)
                },
                onStorageDeleted = {
                    navController.navigateUp()
                },
                onProductClick = { productId ->
                    navController.navigate(FoodlistDestinations.ProductDetail(productId))
                }
            )
        }
        composable<FoodlistDestinations.ProductDetail> { backStackEntry ->
            val destination = backStackEntry.toRoute<FoodlistDestinations.ProductDetail>()

            ProductDetailScreen(
                productId = destination.productId,
                onBackPress = {
                    navController.navigateUp()
                },
                onProductDeleted = {
                    navController.navigateUp()
                }
            )
        }
        composable<FoodlistDestinations.Profile> {
            ProfileScreen(
                onSignInClick = {
                    navController.navigate(FoodlistDestinations.Login)
                },
                onSignUpClick = {
                    navController.navigate(FoodlistDestinations.CreateAccount)
                }
            )
        }
    }
}
