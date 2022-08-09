package ir.part.sdk.ara.home.utils.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import ir.part.sdk.ara.home.ui.SplashScreen
import ir.part.sdk.ara.home.ui.UserHomeScreen

fun NavGraphBuilder.addHomeGraph(navController: NavHostController) {

    navigation(
        route = HomeRouter.HomeGraph.router,
        startDestination = HomeRouter.SplashScreen.router
    ) {

        splashScreen {
            SplashScreen(navigateToUserHomeScreen = {
                navController.navigateToUserHomeScreen()
            })
        }
        userHomeScreen {
            UserHomeScreen(
                {
                    navController.navigateToUserLoginScreen()
                }, {
                    navController.navigateToUserRegisterScreen()
                }
            )
        }
    }
}