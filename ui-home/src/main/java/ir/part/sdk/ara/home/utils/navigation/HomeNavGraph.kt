package ir.part.sdk.ara.home.utils.navigation

import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import ir.part.sdk.ara.base.di.ComponentProviderActivity
import ir.part.sdk.ara.common.ui.view.di.daggerViewModel
import ir.part.sdk.ara.home.di.VersionScreenComponent
import ir.part.sdk.ara.home.ui.SplashScreen
import ir.part.sdk.ara.home.ui.UserHomeScreen
import ir.part.sdk.ara.home.version.VersionViewModel

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
            val versionViewModel: VersionViewModel = daggerViewModel {
                VersionScreenComponent.builder(LocalContext.current as ComponentProviderActivity)
                    .getVersion()
            }

            UserHomeScreen(versionViewModel = versionViewModel,
                navigateToLoginScreen = {
                    navController.navigateToUserLoginScreen()
                }, navigateToRegisterScreen = {
                    navController.navigateToUserRegisterScreen()
                }
            )
        }
    }
}